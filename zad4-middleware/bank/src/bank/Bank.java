package bank;

import sr.rpc.thrift.*;

import java.math.BigDecimal;
import java.util.List;

public class Bank
{
    private AccountDatabase accountDatabase;
    private List<BankCurrency> supportedCurrencies;
    private BankCurrency homeCurrency;
    private Money requiredIncomeForPremium;
    private BigDecimal creditPercentage;

    public Bank(List<BankCurrency> supportedCurrencies, BankCurrency homeCurrency)
    {
        accountDatabase = new AccountDatabase();
        this.supportedCurrencies = supportedCurrencies;
        this.homeCurrency = homeCurrency;
        requiredIncomeForPremium = new Money(homeCurrency, new BigDecimal("1000.00"));
        creditPercentage = new BigDecimal("0.10");
    }

    public Account createAccount(String name, String surname, String pesel, Money income)
    {
        AccountType type = checkAccountType(income);
        Account account = new Account(name, surname, pesel, type, new Money(homeCurrency, new BigDecimal("0.00")));
        accountDatabase.addAccount(account);
        return account;
    }

    public Account getAccount(String pesel, String encryptedKey) throws NoSuchAccount, WrongKey
    {
        Account account = accountDatabase.getAccount(pesel);
        if (account == null) throw new NoSuchAccount(pesel);
        if (!account.verify(encryptedKey)) throw new WrongKey(pesel, encryptedKey);
        return account;
    }

    public CreditInfo requestCredit(Account account, Money amount, int timeInMonths) throws NonPremiumOperationRequest
    {
        if (account.getType() != AccountType.PREMIUM) throw new NonPremiumOperationRequest();
        Money costRequestedCurrency = new Money(amount.getCurrency(), amount.getValue().multiply(creditPercentage).multiply(new BigDecimal(timeInMonths)));
        BigDecimal exchangeRate = new ExchangeRateProvider().check(homeCurrency, amount.getCurrency());
        Money costHomeCurrency = new Money(homeCurrency, amount.getValue().multiply(exchangeRate).multiply(creditPercentage).multiply(new BigDecimal(timeInMonths)));
        return new CreditInfo(costRequestedCurrency.toMoneyStruct(), costHomeCurrency.toMoneyStruct());
    }

    public void printBank()
    {
        System.out.println("Home currency: " + homeCurrency);
        System.out.print("Supported currencies:  ");
        for (BankCurrency currency: supportedCurrencies)
        {
            System.out.print(currency + "  ");
        }
        System.out.println("Accounts: ");
        accountDatabase.printDatabase();
    }

    private AccountType checkAccountType(Money income)
    {
        BigDecimal exchangeRate = new ExchangeRateProvider().check(homeCurrency, income.getCurrency());
        BigDecimal homeCurrencyIncome = income.getValue().multiply(exchangeRate);
        if (homeCurrencyIncome.compareTo(requiredIncomeForPremium.getValue()) >= 0) return AccountType.PREMIUM;
        else return AccountType.STANDARD;
    }
}
