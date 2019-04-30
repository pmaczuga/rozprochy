// thrift --gen java bank.thrift
// thrift --gen py bank.thrift

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

struct AccountInfo {
  1:string pesel
  2:string name
  3:string surname
  4:AccountType type
}

struct CreditInfo {
  1:MoneyStruct costRequestedCurrency
  2:MoneyStruct costHomeCurrency
}

exception NoSuchAccount {
  1:string pesel
}

exception WrongKey {
  1:string pesel
  2:string wrongEncryptedKey
}

exception WrongMoney {
  1:string money
}

exception UnsupportedCurrency {
  1:string currency
}

exception NonPremiumOperationRequest {
}

service AccountCreator{
  AccountCreationInfo create(1:string name, 2:string surname, 3:string pesel, 4:MoneyStruct income)
    throws (1:WrongMoney wm, 2:UnsupportedCurrency uc)
  list<BankCurrency> supportedCurrencies()
}

service StandardAccount {
  MoneyStruct balance(1:string pesel, 2:string encryptedkey)
    throws (1:NoSuchAccount nsa, 2:WrongKey wk)
  AccountInfo validate(1:string pesel, 2:string encryptedkey)
    throws (1:NoSuchAccount nsa, 2:WrongKey wk)
  
}

service PremiumAccount extends StandardAccount {
  CreditInfo requestCredit(1:string pesel, 2:string encryptedkey, 3:MoneyStruct amount, 4: i32 timeInMonths)
    throws (1:NoSuchAccount nsa, 2:WrongKey wk, 3:UnsupportedCurrency uc, 4:WrongMoney wm)
}