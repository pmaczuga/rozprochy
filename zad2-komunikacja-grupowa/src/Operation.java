import java.awt.*;
import java.io.Serializable;

public class Operation implements Serializable
{
    public enum OperationType
    {
        ADD,
        REMOVE
    }

    private OperationType type;
    private String key;
    private Integer value;

    public Operation(String key, Integer value)
    {
        this.type = OperationType.ADD;
        this.key = key;
        this.value = value;
    }

    public Operation(String key)
    {
        this.type = OperationType.REMOVE;
        this.key = key;
    }

    public OperationType getType()
    {
        return type;
    }

    public String getKey()
    {
        return key;
    }

    public Integer getValue()
    {
        return value;
    }
}
