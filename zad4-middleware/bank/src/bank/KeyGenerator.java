package bank;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KeyGenerator
{
    private List<String> keys = Arrays.asList(
            "AlaMaKota",
            "IAmYourFather",
            "BondJamesBond",
            "Student1234",
            "HeresJohnny",
            "WorkWork"
    );

    public String getKey()
    {
        return keys.get(new Random().nextInt(keys.size()));
    }
}
