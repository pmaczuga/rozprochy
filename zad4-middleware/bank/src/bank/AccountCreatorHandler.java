package bank;

import org.apache.thrift.TException;
import sr.rpc.thrift.AccountCreationInfo;
import sr.rpc.thrift.AccountType;

public class AccountCreatorHandler implements sr.rpc.thrift.AccountCreator.Iface
{
    private int id;
    private Bank bank;

    public AccountCreatorHandler(int id, Bank bank)
    {
        this.id = id;
        this.bank = bank;
    }

    @Override
    public AccountCreationInfo create(String name, String surname, String pesel, sr.rpc.thrift.MoneyStruct income) throws TException {
        Account account = bank.createAccount(name, surname, pesel, new Money(income));
        return new AccountCreationInfo(account.getPesel(), account.getKey(), account.getType());
    }
}
