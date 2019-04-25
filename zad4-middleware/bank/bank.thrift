namespace java sr.rpc.thrift

enum BankCurrency {
  PLN = 1,
  USD = 2,
  EUR = 3
}

enum AccountType {
  STANDARD = 1,
  PREMIUM = 2
}

struct MoneyStruct {
  1:string value,
  2:BankCurrency currency
}

struct AccountCreationInfo {
  1:string pesel
  2:string key
  3:AccountType type
}

struct CreditInfo {
  1:MoneyStruct costRequestetCurrency
  2:MoneyStruct costHomeCurrency
}

service AccountCreator{
  AccountCreationInfo create(1:string name, 2:string surname, 3:string pesel, 4:MoneyStruct income)
}

service AccountService {
  MoneyStruct balance(1:string pesel, 2:string encryptedkey)
  list<BankCurrency> supportedCurrencies()
}

service Standard extends AccountService {
}

service Premium extends AccountService {
  CreditInfo requestCredit(1:string pesel, 2:string encryptedkey, 3:MoneyStruct amount, 4: i32 timeInMonths)
}

exception InvalidArguments {
  1:i32 argNum
  2:string reason
}

exception NoSuchAccount {
  1:string pesel
}

exception WrongKey {
  1:string pesel
  2:string wrongEncryptedKey
}

exception NonPremiumOperationRequest {
}