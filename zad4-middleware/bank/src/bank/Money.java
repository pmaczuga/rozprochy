package bank;

import sr.rpc.thrift.BankCurrency;
import sr.rpc.thrift.MoneyStruct;
import sr.rpc.thrift.WrongMoney;

import java.math.BigDecimal;

public class Money
{
    private final BankCurrency currency;
    private final BigDecimal value;

    public Money(BankCurrency currency, BigDecimal value) throws WrongMoney
    {
        if (value.compareTo(new BigDecimal("0")) < 0) throw new WrongMoney(value.toString());
        if (value.scale() != 2) throw new WrongMoney(value.toString());
        this.currency = currency;
        this.value = value;
    }

    public Money(BankCurrency currency, String value) throws WrongMoney
    {
        this(currency, new BigDecimal(value));
    }

    public Money(MoneyStruct moneyStruct) throws WrongMoney
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

    @Override
    public String toString() {
        return value + " " + currency;
    }
}
