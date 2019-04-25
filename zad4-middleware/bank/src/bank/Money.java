package bank;

import sr.rpc.thrift.BankCurrency;
import sr.rpc.thrift.MoneyStruct;

import java.math.BigDecimal;

public class Money
{
    private final BankCurrency currency;
    private final BigDecimal value;

    public Money(BankCurrency currency, BigDecimal value)
    {
        this.currency = currency;
        this.value = value;
    }

    public Money(BankCurrency currency, String value)
    {
        this(currency, new BigDecimal(value));
    }

    public Money(MoneyStruct moneyStruct)
    {
        this(moneyStruct.currency, moneyStruct.value);
    }

    public MoneyStruct toMoneyStruct()
    {
        return new MoneyStruct(value.toPlainString(), currency);
    }

    public BankCurrency getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }
}
