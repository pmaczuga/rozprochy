package currency;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CurrencyServer
{
    private int port = 50051;
    private Server server;

    private void start() throws IOException
    {
        ExchangeRateMap map = new ExchangeRateMap();
        map.startChangeSimulation();

        server = ServerBuilder.forPort(port)
                .addService(new StreamExchangeHandler(map))
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                CurrencyServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
        System.out.println("Starting currency server...");
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final CurrencyServer server = new CurrencyServer();
        server.start();
        server.blockUntilShutdown();
    }
}
