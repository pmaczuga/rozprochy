public interface SimpleStringMap
{
    public boolean containsKey(String key);

    public Integer get(String key);

    public void put(String key, Integer value);

    public Integer remove(String key);
}
