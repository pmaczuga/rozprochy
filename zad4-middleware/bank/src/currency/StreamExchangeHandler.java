package currency;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.ExchangeCurrency;
import sr.grpc.gen.ExchangeRequest;
import sr.grpc.gen.ExchangeResponse;
import sr.grpc.gen.StreamExchangeGrpc;

import java.util.ArrayList;
import java.util.List;


public class StreamExchangeHandler extends StreamExchangeGrpc.StreamExchangeImplBase
{
    private ExchangeRateMap exchangeRateMap;

    public StreamExchangeHandler(ExchangeRateMap map)
    {
        this.exchangeRateMap = map;
    }

    @Override
    public void requestExchangeRate(ExchangeRequest request, StreamObserver<ExchangeResponse> responseObserver) {
        System.out.println("Request for exchange rate");
        List<ExchangeCurrency> requestedCurrencies = new ArrayList<>(request.getCurrenciesList());

        while (true)
        {
            for (ExchangeCurrency currency: requestedCurrencies)
            {
                double rate = exchangeRateMap.getExchangeRate().get(currency);
                ExchangeResponse response = ExchangeResponse.newBuilder().setCurrency(currency).setExchangeRate(rate).build();
                responseObserver.onNext(response);
            }
            try{ Thread.sleep(3000); } catch (InterruptedException ignored) {}
        }
    }
}
