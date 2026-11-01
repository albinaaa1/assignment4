package main.matching;

import java.util.ArrayList;
import java.util.List;

public class NaiveMatching {
    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || text == null || text.length() < pattern.length()) {
            return matches;
        }
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }
            if (j == m) {
                matches.add(i);
            }
        }
        return matches;
    }
}