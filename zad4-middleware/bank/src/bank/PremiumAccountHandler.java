package bank;

import org.apache.thrift.TException;
import sr.rpc.thrift.CreditInfo;
import sr.rpc.thrift.MoneyStruct;
import sr.rpc.thrift.PremiumAccount;

public class PremiumAccountHandler extends StandardAccountHandler implements PremiumAccount.Iface
{
    public PremiumAccountHandler(int id, Bank bank)
    {
        super(id, bank);
    }

    @Override
    public CreditInfo requestCredit(String pesel, String encryptedkey, MoneyStruct amount, int timeInMonths) throws TException
    {
        System.out.println("requestCredit(" + pesel + ", " + encryptedkey + ", " + amount.value + " " + amount.currency + ", " + timeInMonths + ")");
        Account account = bank.getAccount(pesel, encryptedkey);
        return bank.requestCredit(account, new Money(amount), timeInMonths);
    }
}
