package main.benchmark;

import java.util.Random;

public class DataGenerator {
    private static final Random RANDOM = new Random(42);

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) ('A' + RANDOM.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
}
