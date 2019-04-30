package bank;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import sr.grpc.gen.ExchangeCurrency;
import sr.grpc.gen.ExchangeRequest;
import sr.grpc.gen.ExchangeResponse;
import sr.grpc.gen.StreamExchangeGrpc;
import sr.rpc.thrift.BankCurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExchangeRateProvider
{
    private Map<BankCurrency, Double> exchangeRate = new ConcurrentHashMap<>();
    private List<BankCurrency> supportedCurrencies;

    private final ManagedChannel channel;
    private final StreamExchangeGrpc.StreamExchangeBlockingStub stub;

    public ExchangeRateProvider(List<BankCurrency> supportedCurrencies)
    {
        List<String> exchangeCurrencies = new ArrayList<>();
        for (ExchangeCurrency currency: ExchangeCurrency.values()) { exchangeCurrencies.add(currency.name()); }
        for (BankCurrency currency: supportedCurrencies)
        {
            if (!exchangeCurrencies.contains(currency.name())) throw new IllegalArgumentException(currency + " not in exchange rate provider currencies");
        }
        this.supportedCurrencies = new ArrayList<>(supportedCurrencies);
        for (BankCurrency currency: supportedCurrencies)
        {
            exchangeRate.put(currency, 1.0);
        }

        // ------------------------------ important -----------------------
        // -------------------------- channel and stub --------------------
        channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
        stub = StreamExchangeGrpc.newBlockingStub(channel);

        Runnable exchangeGetter = new Runnable() {
            @Override
            public void run() {
                start();
            }
        };
        Thread thread = new Thread(exchangeGetter);
        thread.start();
    }

    public BigDecimal check(BankCurrency first, BankCurrency second)
    {
        double value = exchangeRate.get(first) / exchangeRate.get(second);
        return new BigDecimal(value).setScale(5, 1);
    }

    private void start()
    {
        System.out.println("Connecting to currency server...");
        ExchangeRequest request = ExchangeRequest.newBuilder().addAllCurrencies(bankToExchangeList(supportedCurrencies)).build();

        // get stream from StreamExchangeGrp
        Iterator<ExchangeResponse> responses;
        try
        {
            responses = stub.requestExchangeRate(request);
        } catch (StatusRuntimeException e)
        {
            System.out.println("Can't connect to ExchangeProvider");
            return;
        }

        System.out.println("Currency server connected");
        while(responses.hasNext())
        {
            ExchangeResponse response = responses.next();
            BankCurrency currency = exchangeToBank(response.getCurrency());
            double rate = response.getExchangeRate();
            exchangeRate.put(currency, rate);
        }
    }

    private List<ExchangeCurrency> bankToExchangeList(List<BankCurrency> currencies)
    {
        List<ExchangeCurrency> exchangeCurrencies = new ArrayList<>();
        for (BankCurrency currency: currencies)
        {
            exchangeCurrencies.add(bankToExchange(currency));
        }
        return exchangeCurrencies;
    }

    private ExchangeCurrency bankToExchange(BankCurrency currency)
    {
        return ExchangeCurrency.valueOf(currency.name());
    }

    private BankCurrency exchangeToBank(ExchangeCurrency currency)
    {
        return BankCurrency.valueOf(currency.name());
    }

}
