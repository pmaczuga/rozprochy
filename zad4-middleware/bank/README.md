Interfejsy:
 - bank.thrift
 '''
 thrift --gen java bank.thrift
 thrift --gen py bank.thrift
 '''
 - currency.proto
 '''
 protoc.exe -I=. --java_out=gen --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java.exe --grpc-java_out=gen currency.proto
 '''