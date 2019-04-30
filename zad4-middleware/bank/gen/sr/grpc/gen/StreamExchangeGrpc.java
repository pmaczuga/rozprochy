package sr.grpc.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: currency.proto")
public final class StreamExchangeGrpc {

  private StreamExchangeGrpc() {}

  public static final String SERVICE_NAME = "currency.StreamExchange";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<sr.grpc.gen.ExchangeRequest,
      sr.grpc.gen.ExchangeResponse> getRequestExchangeRateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "requestExchangeRate",
      requestType = sr.grpc.gen.ExchangeRequest.class,
      responseType = sr.grpc.gen.ExchangeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<sr.grpc.gen.ExchangeRequest,
      sr.grpc.gen.ExchangeResponse> getRequestExchangeRateMethod() {
    io.grpc.MethodDescriptor<sr.grpc.gen.ExchangeRequest, sr.grpc.gen.ExchangeResponse> getRequestExchangeRateMethod;
    if ((getRequestExchangeRateMethod = StreamExchangeGrpc.getRequestExchangeRateMethod) == null) {
      synchronized (StreamExchangeGrpc.class) {
        if ((getRequestExchangeRateMethod = StreamExchangeGrpc.getRequestExchangeRateMethod) == null) {
          StreamExchangeGrpc.getRequestExchangeRateMethod = getRequestExchangeRateMethod = 
              io.grpc.MethodDescriptor.<sr.grpc.gen.ExchangeRequest, sr.grpc.gen.ExchangeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "currency.StreamExchange", "requestExchangeRate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ExchangeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ExchangeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new StreamExchangeMethodDescriptorSupplier("requestExchangeRate"))
                  .build();
          }
        }
     }
     return getRequestExchangeRateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StreamExchangeStub newStub(io.grpc.Channel channel) {
    return new StreamExchangeStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StreamExchangeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StreamExchangeBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StreamExchangeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StreamExchangeFutureStub(channel);
  }

  /**
   */
  public static abstract class StreamExchangeImplBase implements io.grpc.BindableService {

    /**
     */
    public void requestExchangeRate(sr.grpc.gen.ExchangeRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ExchangeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRequestExchangeRateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRequestExchangeRateMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                sr.grpc.gen.ExchangeRequest,
                sr.grpc.gen.ExchangeResponse>(
                  this, METHODID_REQUEST_EXCHANGE_RATE)))
          .build();
    }
  }

  /**
   */
  public static final class StreamExchangeStub extends io.grpc.stub.AbstractStub<StreamExchangeStub> {
    private StreamExchangeStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamExchangeStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamExchangeStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamExchangeStub(channel, callOptions);
    }

    /**
     */
    public void requestExchangeRate(sr.grpc.gen.ExchangeRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ExchangeResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getRequestExchangeRateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StreamExchangeBlockingStub extends io.grpc.stub.AbstractStub<StreamExchangeBlockingStub> {
    private StreamExchangeBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamExchangeBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamExchangeBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamExchangeBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<sr.grpc.gen.ExchangeResponse> requestExchangeRate(
        sr.grpc.gen.ExchangeRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getRequestExchangeRateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StreamExchangeFutureStub extends io.grpc.stub.AbstractStub<StreamExchangeFutureStub> {
    private StreamExchangeFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamExchangeFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamExchangeFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamExchangeFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_REQUEST_EXCHANGE_RATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StreamExchangeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StreamExchangeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_EXCHANGE_RATE:
          serviceImpl.requestExchangeRate((sr.grpc.gen.ExchangeRequest) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ExchangeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StreamExchangeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StreamExchangeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.CurrencyProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StreamExchange");
    }
  }

  private static final class StreamExchangeFileDescriptorSupplier
      extends StreamExchangeBaseDescriptorSupplier {
    StreamExchangeFileDescriptorSupplier() {}
  }

  private static final class StreamExchangeMethodDescriptorSupplier
      extends StreamExchangeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StreamExchangeMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StreamExchangeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StreamExchangeFileDescriptorSupplier())
              .addMethod(getRequestExchangeRateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
