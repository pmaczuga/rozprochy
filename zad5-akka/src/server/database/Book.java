package server.database;

public class Book
{
    private final String title;
    private final int price;

    public Book(String title, int price)
    {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Book(%s, %s)", title, price);
    }
}
