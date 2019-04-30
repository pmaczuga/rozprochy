import sys
import hashlib
sys.path.append('gen-py')

from bank import AccountCreator, StandardAccount, PremiumAccount
from bank.ttypes import *

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TMultiplexedProtocol

def print_start_commands():
    print("Commands: ")
    print("exit")
    print("create <name> <suraname> <pesel> <inocome> <income currency>")
    print("login <pesel> <key>")

def print_login_commands(account_type=AccountType.STANDARD):
    print("logout")
    print("balance")
    if account_type == AccountType.PREMIUM:
        print("credit <amount> <currency> <time in months>")

def print_creation_info(info):
    print("pesel: {}".format(info.pesel))
    print("key: {}".format(info.key))
    print("account type: {}".format(AccountType._VALUES_TO_NAMES[info.type]))

def print_money_struct(money_struct):
    print("{} {}".format(money_struct.value, BankCurrency._VALUES_TO_NAMES[money_struct.currency]))

def print_credit_info(info):
    print("Cost in bank home currency: ")
    print_money_struct(info.costHomeCurrency)
    print("Cost in requested currency: ")
    print_money_struct(info.costRequestedCurrency)

def encrypt_key(key):
    return hashlib.md5(bytes(key, 'utf-8')).hexdigest().upper()

def start_logged(pesel, key, account_service):
    print("\nLogged as: {}\n".format(pesel))

    if isinstance(account_service, PremiumAccount.Client):
        print_login_commands(AccountType.PREMIUM)
    else:
        print_login_commands()

    while(True):
        print("\n>> ", end='', flush=True)
        str = input()
        args = str.split()

        if len(args) <= 0:
            continue
        
        if args[0] == 'logout':
            print("Logging out...")
            return

        elif args[0] == 'balance':
            try:
                # --------------------------------------------------------------
                # ------------------------- Balance ----------------------------
                money = account_service.balance(pesel, encrypt_key(key))
                # --------------------------------------------------------------
            except Thrift.TException as e:
                print(e)
                continue
            print_money_struct(money)

        elif args[0] == 'credit' and isinstance(account_service, PremiumAccount.Client):
            if len(args) != 4:
                print("Wrong number of arguments")
                continue
            try:
                # --------------------------------------------------------------
                # ----------------------- Credit request -----------------------
                info = account_service.requestCredit(pesel, encrypt_key(key), MoneyStruct(args[1], BankCurrency._NAMES_TO_VALUES[args[2]]), int(args[3]))
                # --------------------------------------------------------------
            except ValueError:
                "Wrong time (in months) format"
            except (KeyError, UnsupportedCurrency):
                "Unsupported currency"
            except NonPremiumOperationRequest:
                "Credit request form non premium acoount"
            except WrongMoney:
                "Wrong money format"
            print_credit_info(info)

def main():
    # Connect to service AccountCreator
    transport_create = TSocket.TSocket('localhost', 9090)
    transport_create = TTransport.TBufferedTransport(transport_create)
    protocol_create = TBinaryProtocol.TBinaryProtocol(transport_create)
    creator = AccountCreator.Client(protocol_create)
    transport_create.open()

    # Connect to services StandardAccount and PremiumAccount
    transport_accounts = TSocket.TSocket('localhost', 9091)
    transport_accounts = TTransport.TBufferedTransport(transport_accounts)
    protocol_accounts = TBinaryProtocol.TBinaryProtocol(transport_accounts)
    standard = StandardAccount.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol_accounts, "Standard"))
    premium = PremiumAccount.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol_accounts, "Premium"))
    transport_accounts.open()

    print_start_commands()
    while(True):
        print("\n>> ", end='', flush=True)
        str = input()
        args = str.split()

        if len(args) <= 0:
            continue

        if args[0] == 'exit':
            break

        elif args[0] == 'create':
            if len(args) != 6:
                print("Wrong number of arguments")
                continue
            try:
                income = MoneyStruct(args[4], BankCurrency._NAMES_TO_VALUES[args[5]])
            except KeyError:
                print("Unsupported currency")
                continue
            try:
                # ----------------------------------------------------------
                # ---------------- Create new account ----------------------
                info = creator.create(args[1], args[2], args[3], income)
                # ----------------------------------------------------------
            except WrongMoney:
                print("Wrong money format")
                continue
            except UnsupportedCurrency:
                print("Unsupported currency")
                continue
            except Thrift.TException as e:
                print(e)
                continue
            print_creation_info(info)


        elif args[0] == 'login':
            if len(args) != 3:
                print("Wrong number of arguments")
                continue
            try:
                # ----------------------------------------------------------
                # ----------------- Validate - get account info ------------
                info = standard.validate(args[1], encrypt_key(args[2]))
                # ----------------------------------------------------------
            except NoSuchAccount:
                print("No such account")
                continue
            except WrongKey:
                print("Wrong key")
                continue
            except Thrift.TException as e:
                print(e)
                continue
            if info.type == AccountType.STANDARD:
                start_logged(args[1], args[2], standard)
            else:
                start_logged(args[1], args[2], premium)

    transport_create.close()
    transport_accounts.close()


if __name__ == '__main__':
    main()
