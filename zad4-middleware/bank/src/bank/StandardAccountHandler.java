package bank;

import org.apache.thrift.TException;
import sr.rpc.thrift.AccountInfo;
import sr.rpc.thrift.BankCurrency;
import sr.rpc.thrift.MoneyStruct;
import sr.rpc.thrift.StandardAccount;

import java.util.List;

public class StandardAccountHandler implements StandardAccount.Iface
{
    protected int id;
    protected Bank bank;

    public StandardAccountHandler(int id, Bank bank)
    {
        this.id = id;
        this.bank = bank;
    }

    @Override
    public AccountInfo validate(String pesel, String encryptedkey) throws TException
    {
        System.out.println("validate(" + pesel + ", " + encryptedkey + ")");
        Account account = bank.getAccount(pesel, encryptedkey);
        return account.accountInfo();
    }

    @Override
    public MoneyStruct balance(String pesel, String encryptedkey) throws TException
    {
        System.out.println("balance(" + pesel + ", " + encryptedkey + ")");
        Account account = bank.getAccount(pesel, encryptedkey);
        return account.getBalance().toMoneyStruct();
    }
}
