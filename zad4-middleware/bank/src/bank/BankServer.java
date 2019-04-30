package bank;

import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import sr.rpc.thrift.AccountCreator;
import sr.rpc.thrift.BankCurrency;
import sr.rpc.thrift.PremiumAccount;
import sr.rpc.thrift.StandardAccount;

import java.util.Arrays;
import java.util.Scanner;

public class BankServer {
    public static void main(String[] args)
    {
        try
        {
            Bank bank = new Bank(Arrays.asList(BankCurrency.EUR, BankCurrency.USD, BankCurrency.PLN), BankCurrency.PLN);

            Runnable creator = new Runnable()
            {
                @Override
                public void run() { creator(bank); }
            };
            Runnable accountAccess = new Runnable() {
                @Override
                public void run() { accountAccess(bank); }
            };

            new Thread(creator).start();
            new Thread(accountAccess).start();
        } catch (TException e)
        {
            e.printStackTrace();
        }
    }

    private static void creator(Bank bank)
    {
        try
        {
            AccountCreator.Processor<AccountCreatorHandler> processor1 = new AccountCreator.Processor<AccountCreatorHandler>(new AccountCreatorHandler(1, bank));

            TServerTransport transport = new TServerSocket(9090);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            TServer server = new TSimpleServer(new TServer.Args(transport).protocolFactory(protocolFactory).processor(processor1));

            System.out.println("Starting creator server...");
            server.serve();

        } catch (TException e)
        {
            e.printStackTrace();
        }
    }

    private static void accountAccess(Bank bank)
    {
        try {
            StandardAccount.Processor<StandardAccountHandler> processor1 = new StandardAccount.Processor<StandardAccountHandler>(new StandardAccountHandler(2, bank));
            PremiumAccount.Processor<PremiumAccountHandler> processor2 = new PremiumAccount.Processor<PremiumAccountHandler>(new PremiumAccountHandler(3, bank));

            TServerTransport transport = new TServerSocket(9091);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            TMultiplexedProcessor multiplex = new TMultiplexedProcessor();
            multiplex.registerProcessor("Standard", processor1);
            multiplex.registerProcessor("Premium", processor2);

            TServer server = new TSimpleServer(new TServer.Args(transport).protocolFactory(protocolFactory).processor(multiplex));

            System.out.println("Starting account access sever...");
            server.serve();

        } catch (TException e)
        {
            e.printStackTrace();
        }
    }
}
