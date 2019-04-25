package bank;

public class BankServer {
    public static void main(String[] args)
    {
        try
        {
            Runnable simple = new Runnable()
            {
                public void run() { simple(); }
            };
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void simple()
    {

    }
}
