package api;

import java.io.Serializable;

public class Find implements Serializable
{
    private final String title;

    public Find(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
