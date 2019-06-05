# Zookeeper

## How to

### Uruchomienie serwera

#### Standalone

1. Zmienić nazwę pliku `conf/zoo_sample.cfg` na `zoo.cfg`
2. Uruchomić serwer Zookeeper'a:   
   ```bin/zkServer.cmd``` - Windows   
   ```bin/zkServer.sh``` - Linux
3. Uruchomić klienta:   
   ```bin/zkCli.cmd```

#### Replicated

1. Stworzyć pliki:   
   `conf/zoo1.cfg`, `conf/zoo2.cfg`, `conf/zoo3.cfg`

   Z treścią:

   ```
   tickTime=2000
   initLimit=10
   syncLimit=5
   dataDir=/tmp/zookeeper/zk1
   clientPort=2181
   server.1=localhost:2888:3888
   server.2=localhost:2889:3889
   server.3=localhost:2890:3890
   ```

   Gdzie dla każdego pliku jest inny `clientport` oraz ścieżka `dataDir` - odpowiednio `zk1`, `zk2`, `zk3`

2. Stworzyć katalogi `zk1`, `zk2`, `zk3`. Dla powyższego będą to:
   ```C:\tmp\zookeeper\zk1```
   ```C:\tmp\zookeeper\zk2```
   ```C:\tmp\zookeeper\zk3```

3. W każdym katalogu stworzyć plik `myid` (bez rozszerzenia) z jedną cyfrą - numer id serwera, np. 2.

4. Uruchomić trzy serwery korzystając ze zmodyfikowanego pliku `zkServer2.cmd`:   

   ```zkServer2.cmd \zoo1.cfg``` dla każdego pliku `.cfg`

5. Uruchomić trzech klientów:   
   ```zkCli -server localhost:2181```  dla każdego portu `clientPort` z `.cfg`.

### Podstawowe operacje klienta

Utworzenie znode'a:   
```create /z Content```
```create /z/a Content2```

Usunięcie znode'a"
```delete /z```

Pokazanie dzieci danego node'a:
```ls /```
```ls /z```

### Aplikacaja Javowa

Po uruchomieniu podłącza się pod dany port serwera Zookeeper.

Po dodaniu znode'a `/z` otwiera się aplikacja pod ścieżką podaną w kodzie programu.

Po usunięciu `/z` aplikacja jest zamykana.

Po dodaniu/usunięciu dzieci `/z` wyświetlana jest ich aktualna ilość.

Dostępne komendy:   
`ls` - wypisuje drzewo znode'ów   
`q` - kończy działanie apikacji

## Instrukcja do zadania

Stworzyć aplikację w środowisku Java (Zookeeper) która wykorzystując mechanizm obserwatorów (watches) umożliwia następujące funkcjonalności:

- Jeśli tworzony jest znode o nazwie **"z"** uruchamiana jest zewnętrzna aplikacja (dowolna, określona w linii poleceń),
- Jeśli jest kasowany **"z"** aplikacja zewnętrzna jest zatrzymywana,
- Każde dodanie potomka do **"z"** powoduje wyświetlenie graficznej informacji na ekranie o aktualnej ilości potomków.

Dodatkowo aplikacja powinna mieć możliwość wyświetlenia całej struktury drzewa **"z"**.   

Stworzona aplikacja powinna działać w środowisku „Replicated ZooKeeper”.

ZooKeeper 3.5.5 API - http://zookeeper.apache.org/doc/r3.5.5/api/index.html