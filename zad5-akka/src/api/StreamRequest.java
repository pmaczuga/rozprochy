package api;

import java.io.Serializable;

public class StreamRequest implements Serializable
{
    private final String title;

    public StreamRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
