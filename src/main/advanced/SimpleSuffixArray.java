package main.advanced;

import java.util.Arrays;

public class SimpleSuffixArray {
    public static int[] build(String text) {
        int n = text.length();
        Suffix[] suffixes = new Suffix[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = new Suffix(i, text.substring(i));
        }
        Arrays.sort(suffixes);
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = suffixes[i].index;
        }
        return sa;
    }

    private static class Suffix implements Comparable<Suffix> {
        int index;
        String text;

        Suffix(int index, String text) {
            this.index = index;
            this.text = text;
        }

        @Override
        public int compareTo(Suffix other) {
            return this.text.compareTo(other.text);
        }
    }
}