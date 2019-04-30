package bank;

import org.apache.commons.codec.digest.DigestUtils;
import sr.rpc.thrift.AccountInfo;
import sr.rpc.thrift.AccountType;

public class Account
{
    private String name;
    private String surname;
    private String pesel;
    private String key;
    private Money balance;
    private AccountType type;

    public Account(String name, String surname, String pesel, AccountType type, Money initBalance)
    {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.type = type;
        this.key = new KeyGenerator().getKey();
        this.balance = initBalance;
    }

    public boolean verify(String encryptedKey)
    {
        String properEncrypted = DigestUtils.md5Hex(key).toUpperCase();
        return properEncrypted.equals(encryptedKey);
    }

    public AccountInfo accountInfo()
    {
        return new AccountInfo(pesel, name, surname, type);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPesel() {
        return pesel;
    }

    public Money getBalance() {
        return balance;
    }

    public String getKey() {
        return key;
    }

    public AccountType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + ": " + pesel + "   key: " + key;
    }
}
