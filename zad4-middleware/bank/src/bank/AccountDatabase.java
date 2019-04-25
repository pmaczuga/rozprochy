package bank;

import java.util.List;

public class AccountDatabase
{
    private List<Account> accounts;

    public Account getAccount(String pesel)
    {
        for(Account account: accounts)
        {
            if (account.getPesel().equals(pesel))
            {
                return account;
            }
        }
        return null;
    }

    public void addAccount(Account account)
    {
        accounts.add(account);
    }

    public void printDatabase()
    {
        for (Account account: accounts)
        {
            System.out.println(account);
        }
    }
}
