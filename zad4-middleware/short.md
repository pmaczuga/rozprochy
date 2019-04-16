# Najważniejsze rzeczy:

## CORBA
- Common Object Request Broker Architecture
- "matka wszystkich technologii"
- middlewae
- komunikacaja pomiędzy aplikacjami:
	- na różnych maszynach
	- różne os
	- różne języki
- dwa **objekty**
	- servive provider - dostarcza funkcjonalność
	- client - wymaga innego objektu (sp)

## Pojęcia:
- IDL:
	- język definicji interfejsów
	- definiuje kontakt pomiędzy klientem a serwerem
- stub i skeleton:
	- stub:
		- po stronie klienta
		- reprezentuje lokalnie objekt zadlany
	- skeleton:
		- po stronie serwera
		- punkt wejścia dla systemu rozproszonego
		- konwertuje napływające dane, wywołuje metody, zwraca wyniki
- objekt, serwant, serwer:
	- objekt:
		- abstrakcja posiadająca jednoznaczną identyfikację oraz interfejs i odpowiadajaca na żądania klientów
	- serwant:
		- element strony serwerowej, implementacja funkcjonalności interfejsu w konkretnym języku
	- serwer:
		- serwer :)
- object adapter:
	- ekspnuje gniazda do zdalnej komunikacji
	- kieruje żądania adresowe do odpowiednich serwantów
	- zarządza cyklem życia obiektów
	- connects request using object reference with the proper code to service that request
- RPC:
	- Remote Procedure Call
	- zdalne wywyołanie metody
	- wywołanie metody w innej przestrzeni adresowej (innym komputerze) bez kodowania szczegółów
- przezroczystość:
	- właściwość systemu powodująca postrzeganie systemu przez użytkownika jako całości, a nie poszczególnych składowych
