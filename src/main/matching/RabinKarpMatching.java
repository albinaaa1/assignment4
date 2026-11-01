package main.matching;

import java.util.ArrayList;
import java.util.List;

public class RabinKarpMatching {
    private static final int PRIME = 101;

    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || text == null || text.length() < pattern.length()) {
            return matches;
        }

        int m = pattern.length();
        int n = text.length();
        long patternHash = 0;
        long textHash = 0;
        long h = 1;
        int d = 256;

        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % PRIME;
        }

        for (int i = 0; i < m; i++) {
            patternHash = (d * patternHash + pattern.charAt(i)) % PRIME;
            textHash = (d * textHash + text.charAt(i)) % PRIME;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    matches.add(i);
                }
            }

            if (i < n - m) {
                textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % PRIME;
                if (textHash < 0) {
                    textHash = textHash + PRIME;
                }
            }
        }
        return matches;
    }
}