package currency;

import sr.grpc.gen.ExchangeCurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ExchangeRateMap
{
    private double maxStep = 0.1;
    private double maxSleepTime = 5000;

    private Map<ExchangeCurrency, Double> exchangeRate = new ConcurrentHashMap<ExchangeCurrency, Double>()
    {{
        put(ExchangeCurrency.PLN, 1.0);
        put(ExchangeCurrency.USD, 3.0);
        put(ExchangeCurrency.EUR, 4.0);
        put(ExchangeCurrency.GBP, 5.0);
    }};

    public void startChangeSimulation()
    {
        Runnable changer = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while(true)
                {
                    for (ExchangeCurrency currency: exchangeRate.keySet())
                    {
                        double change = 2.0 * maxStep * random.nextDouble() - maxStep;
                        double newRate = exchangeRate.get(currency) * (1.0 + change);

                        exchangeRate.replace(currency, newRate);

                        int sleepTime = (int) (maxSleepTime * random.nextDouble());
                        try { Thread.sleep(sleepTime); } catch (InterruptedException ignored) {}
                    }
                }
            }
        };

        new Thread(changer).start();
    }

    public Map<ExchangeCurrency, Double> getExchangeRate() {
        return exchangeRate;
    }
}
