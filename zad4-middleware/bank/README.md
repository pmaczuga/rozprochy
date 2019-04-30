## Interfejsy
 ### bank.thrift
 ```
 thrift --gen java bank.thrift
 thrift --gen py bank.thrift
 ```
 ### currency.proto
 ```
 protoc.exe -I=. --java_out=gen --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java.exe --grpc-java_out=gen currency.proto
 ```
 
 ## Implementacja
 ### Banku
 JAVA
 _src/bank_
 
 ### Currency Provider'a
 JAVA
 _src/currency_
 
 ### Klienta banku
 PYTHON
 _clinet.py_
 
 ## Wygenerowane pliki
 W folderach:
  - _gen_ - dla Currency Provider'a
  - _gen-java_ - dla banku
  - _gen-python_ - dla klient banku

## Uruchamanie
W kolejności:
 - CurrencyServer.java
 - BankServer.java
 - client.py
