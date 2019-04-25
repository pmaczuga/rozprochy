package sr.thrift.server;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

import sr.rpc.thrift.AdvancedCalculator;
import sr.rpc.thrift.Calculator;

import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

// Generated code

import java.util.HashMap;


public class ThriftServer {


	public static void main(String [] args) 
	{
		try {	
			Runnable simple = new Runnable() {
				public void run() {
					simple();
				}
			};      
			Runnable nonblock = new Runnable() {
				public void run() {
					nonblock();
				}
			};
			Runnable multiplex = new Runnable() {
				public void run() {
					multiplex();
				}
			};
			/*Runnable general = new Runnable() {
				public void run() {
					general(new Calculator.Processor(new CalculatorHandler(1)));
				}
			};*/
			
			new Thread(simple).start();
			new Thread(multiplex).start();
			new Thread(nonblock).start();
			//new Thread(general).start();
			//new Thread(secure).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static void simple() 
	{
		try {
			Calculator.Processor<CalculatorHandler> processor1 = new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(1));
			Calculator.Processor<CalculatorHandler> processor2 = new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(2));
			AdvancedCalculator.Processor<AdvancedCalculatorHandler> processor3 = new AdvancedCalculator.Processor<AdvancedCalculatorHandler>(new AdvancedCalculatorHandler(11));
			
			TServerTransport serverTransport = new TServerSocket(9090);
			
			TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
			//TProtocolFactory protocolFactory = new TJSONProtocol.Factory();
			//TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
			
			TServer server = new TSimpleServer(new Args(serverTransport).protocolFactory(protocolFactory).processor(processor1));

			System.out.println("Starting the simple server...");
			server.serve(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void multiplex() 
	{
		try {
			Calculator.Processor<CalculatorHandler> processor1 = new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(1));
			Calculator.Processor<CalculatorHandler> processor2 = new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(2));
			AdvancedCalculator.Processor<AdvancedCalculatorHandler> processor3 = new AdvancedCalculator.Processor<AdvancedCalculatorHandler>(new AdvancedCalculatorHandler(11));
			AdvancedCalculator.Processor<AdvancedCalculatorHandler> processor4 = new AdvancedCalculator.Processor<AdvancedCalculatorHandler>(new AdvancedCalculatorHandler(12));

			TServerTransport serverTransport = new TServerSocket(9091);

			TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
			//TProtocolFactory protocolFactory = new TJSONProtocol.Factory();
			//TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
			
			TMultiplexedProcessor multiplex = new TMultiplexedProcessor();
            multiplex.registerProcessor("S1", processor1);
            multiplex.registerProcessor("S2", processor2);
            multiplex.registerProcessor("A1", processor3);
            multiplex.registerProcessor("A2", processor4);

			TServer server = new TSimpleServer(new Args(serverTransport).protocolFactory(protocolFactory).processor(multiplex)); 
			
			System.out.println("Starting the multiplex server...");
			server.serve(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void nonblock() 
	{
		try {
			Calculator.Processor<CalculatorHandler> processor = new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(1));
			TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(9093);
			TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
			System.out.println("Starting the non-block server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void multithread() 
	{
		try {
			Calculator.Processor<CalculatorHandler> processor = new Calculator.Processor<CalculatorHandler>(new CalculatorHandler(1));
			TNonblockingServerTransport nonblockserverTransport = new TNonblockingServerSocket(9092);

			// Use this for a multithreaded server
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(nonblockserverTransport).processor(processor));
			System.out.println("Starting the multithread server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void general(TProcessor testProcessor)
	{
		String transport_type = "def";
		String server_type = "blocking";

		// Protocol factory
		TProtocolFactory tProtocolFactory = new TJSONProtocol.Factory();

		TTransportFactory tTransportFactory = null;

		if (transport_type.equals("framed")) {
			tTransportFactory = new TFramedTransport.Factory();
		} else if (transport_type.equals("fastframed")) {
			tTransportFactory = new TFastFramedTransport.Factory();
		} else { // .equals("buffered") => default value
			tTransportFactory = new TTransportFactory();
		}

		TServer serverEngine = null;


		if (server_type.equals("nonblocking") ||
				server_type.equals("threaded-selector")) {
			// Nonblocking servers
			TNonblockingServerSocket tNonblockingServerSocket = null;
			try {
				tNonblockingServerSocket = new TNonblockingServerSocket(new TNonblockingServerSocket.NonblockingAbstractServerSocketArgs().port(9090));
			} catch (TTransportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (server_type.equals("nonblocking")) {
				// Nonblocking Server
				TNonblockingServer.Args tNonblockingServerArgs
				= new TNonblockingServer.Args(tNonblockingServerSocket);
				tNonblockingServerArgs.processor(testProcessor);
				tNonblockingServerArgs.protocolFactory(tProtocolFactory);
				tNonblockingServerArgs.transportFactory(tTransportFactory);

				serverEngine = new TNonblockingServer(tNonblockingServerArgs);
			} else { // server_type.equals("threaded-selector")
				// ThreadedSelector Server
				TThreadedSelectorServer.Args tThreadedSelectorServerArgs
				= new TThreadedSelectorServer.Args(tNonblockingServerSocket);
				tThreadedSelectorServerArgs.processor(testProcessor);
				tThreadedSelectorServerArgs.protocolFactory(tProtocolFactory);
				tThreadedSelectorServerArgs.transportFactory(tTransportFactory);

				serverEngine = new TThreadedSelectorServer(tThreadedSelectorServerArgs);
			}
		} else {
			// Blocking servers

			// SSL socket
			TServerSocket tServerSocket = null;
			//if (ssl) {
			//	tServerSocket = TSSLTransportFactory.getServerSocket(port, 0);
			//} else {
			try {
				tServerSocket = new TServerSocket(new TServerSocket.ServerSocketTransportArgs().port(9090));
			} catch (TTransportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//}

			if (server_type.equals("simple")) {
				// Simple Server
				TServer.Args tServerArgs = new TServer.Args(tServerSocket);
				tServerArgs.processor(testProcessor);
				tServerArgs.protocolFactory(tProtocolFactory);
				tServerArgs.transportFactory(tTransportFactory);

				serverEngine = new TSimpleServer(tServerArgs);
			} else { // server_type.equals("threadpool")
				// ThreadPool Server
				TThreadPoolServer.Args tThreadPoolServerArgs
				= new TThreadPoolServer.Args(tServerSocket);
				tThreadPoolServerArgs.processor(testProcessor);
				tThreadPoolServerArgs.protocolFactory(tProtocolFactory);
				tThreadPoolServerArgs.transportFactory(tTransportFactory);

				serverEngine = new TThreadPoolServer(tThreadPoolServerArgs);
			}
		}


		//Set server event handler
		//serverEngine.setServerEventHandler(new TestServerEventHandler());

		// Run it
		System.out.println("Starting the server on port ...");
		serverEngine.serve();

		System.out.println("done.");

	}*/


}