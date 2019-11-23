package pl.lukaszdutka;

import java.util.List;
import java.util.Random;

public class MyUtil {

    private final static Random random = new Random(1); //seeded
//    private final static Random random = new Random(); //really random

    public static String getRandomElementFromList(List<String> values) {
        return values.get(random.nextInt(values.size()));
    }


}
