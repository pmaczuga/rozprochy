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
    private ExchangeRateProvider exchangeRateProvider;

    public Bank(List<BankCurrency> supportedCurrencies, BankCurrency homeCurrency) throws WrongMoney
    {
        accountDatabase = new AccountDatabase();
        this.supportedCurrencies = supportedCurrencies;
        this.homeCurrency = homeCurrency;
        requiredIncomeForPremium = new Money(homeCurrency, new BigDecimal("1000.00"));
        creditPercentage = new BigDecimal("0.001");
        exchangeRateProvider = new ExchangeRateProvider(supportedCurrencies);
    }

    public Account createAccount(String name, String surname, String pesel, Money income) throws WrongMoney, UnsupportedCurrency
    {
        if (!getSupportedCurrencies().contains(income.getCurrency())) throw new UnsupportedCurrency(income.getCurrency().toString());
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

    public CreditInfo requestCredit(Account account, Money amount, int timeInMonths) throws NonPremiumOperationRequest, UnsupportedCurrency, WrongMoney
    {
        if (account.getType() != AccountType.PREMIUM) throw new NonPremiumOperationRequest();
        if (!supportedCurrencies.contains(amount.getCurrency())) throw new UnsupportedCurrency(amount.getCurrency().toString());
        Money costRequestedCurrency = new Money(amount.getCurrency(), amount.getValue().multiply(creditPercentage).multiply(new BigDecimal(timeInMonths)).setScale(2, 1));
        BigDecimal exchangeRate = exchangeRateProvider.check(amount.getCurrency(), homeCurrency);
        Money costHomeCurrency = new Money(homeCurrency, amount.getValue().multiply(exchangeRate).multiply(creditPercentage).multiply(new BigDecimal(timeInMonths)).setScale(2, 1));
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
        System.out.println("\nAccounts: ");
        accountDatabase.printDatabase();
    }

    public BankCurrency getHomeCurrency()
    {
        return homeCurrency;
    }

    public List<BankCurrency> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    public Money getRequiredIncomeForPremium() {
        return requiredIncomeForPremium;
    }

    public BigDecimal getCreditPercentage() {
        return creditPercentage;
    }

    private AccountType checkAccountType(Money income)
    {
        BigDecimal exchangeRate = exchangeRateProvider.check(homeCurrency, income.getCurrency());
        BigDecimal homeCurrencyIncome = income.getValue().multiply(exchangeRate);
        if (homeCurrencyIncome.compareTo(requiredIncomeForPremium.getValue()) >= 0) return AccountType.PREMIUM;
        else return AccountType.STANDARD;
    }
}
