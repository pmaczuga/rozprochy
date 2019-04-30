import bank.Account;
import bank.Bank;
import bank.Money;
import com.sun.deploy.net.CrossDomainXML;
import org.apache.commons.codec.digest.DigestUtils;
import sr.rpc.thrift.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws NoSuchAccount, WrongKey, NonPremiumOperationRequest, WrongMoney, UnsupportedCurrency
    {
        Bank bank = new Bank(Arrays.asList(BankCurrency.PLN, BankCurrency.EUR), BankCurrency.PLN);
        Account account = bank.createAccount("Jan", "Kowalski", "97121222222", new Money(BankCurrency.PLN, ("100.00")));
        System.out.println(account);
        Account account1 = bank.getAccount("97121222222", DigestUtils.md5Hex("Ala ma kota").toUpperCase());
        System.out.println(DigestUtils.md5Hex("Ala ma kota").toUpperCase());
        System.out.println(account);
        CreditInfo info = bank.requestCredit(account1, new Money(BankCurrency.EUR, "10.00"), 12);
        System.out.println(info.costHomeCurrency);
        System.out.println(info.costRequestedCurrency);
    }
}
