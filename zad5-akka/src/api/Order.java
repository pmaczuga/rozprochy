package api;

import java.io.Serializable;

public class Order implements Serializable
{
    private final String title;

    public Order(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
