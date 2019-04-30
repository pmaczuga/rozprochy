package bank;

import org.apache.thrift.TException;
import sr.rpc.thrift.AccountCreationInfo;
import sr.rpc.thrift.AccountInfo;
import sr.rpc.thrift.AccountType;
import sr.rpc.thrift.BankCurrency;

import java.util.List;

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
    public AccountCreationInfo create(String name, String surname, String pesel, sr.rpc.thrift.MoneyStruct income) throws TException
    {
        System.out.println("create(" + name + ", " + surname + ", " + pesel + ", " + income.value + " " + income.currency + ")");
        Account account = bank.createAccount(name, surname, pesel, new Money(income));
        return new AccountCreationInfo(account.getPesel(), account.getKey(), account.getType());
    }

    @Override
    public List<BankCurrency> supportedCurrencies() throws TException
    {
        System.out.println("supportedCurrencies()");
        return bank.getSupportedCurrencies();
    }
}
