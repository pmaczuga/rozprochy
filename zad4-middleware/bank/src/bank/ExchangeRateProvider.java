package bank;

import sr.rpc.thrift.BankCurrency;

import java.math.BigDecimal;

public class ExchangeRateProvider
{
    //TODO fill ExchangeRateProvider class

    public BigDecimal check(BankCurrency first, BankCurrency second)
    {
        return new BigDecimal("0.8");
    }
}
