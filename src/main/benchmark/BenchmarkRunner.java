package main.benchmark;

import main.dp.EditDistance;
import main.dp.LCS;
import main.matching.KMPMatching;
import main.matching.NaiveMatching;
import main.matching.RabinKarpMatching;
import main.advanced.SimpleSuffixArray;
import main.advanced.KasaiLCP;

import java.util.List;

public class BenchmarkRunner {
    private static final int WARMUP_RUNS = 2;
    private static final int MEASURE_RUNS = 5;

    public static void runAll() {
        System.out.println("==================================================");
        System.out.println("      ASSIGNMENT 4: PERFORMANCE BENCHMARKS      ");
        System.out.println("==================================================");

        runDynamicProgrammingBenchmarks();
        runStringMatchingBenchmarks();
        runKasaiBenchmarks();

        System.out.println("==================================================");
        System.out.println("           ALL BENCHMARKS COMPLETED               ");
        System.out.println("==================================================");
    }

    private static void runDynamicProgrammingBenchmarks() {
        System.out.println("\n--- 1. Dynamic Programming (LCS & Edit Distance) ---");
        int[] sizes = {100, 500, 1000};

        System.out.printf("%-10s | %-20s | %-20s%n", "Size", "LCS Avg Time (ms)", "EditDist Avg Time (ms)");
        System.out.println("-".repeat(58));

        for (int size : sizes) {
            String s1 = DataGenerator.generateRandomString(size);
            String s2 = DataGenerator.generateRandomString(size);

            for (int w = 0; w < WARMUP_RUNS; w++) {
                LCS.findLCSLength(s1, s2);
                EditDistance.calculate(s1, s2);
            }

            long totalLcsTime = 0;
            for (int r = 0; r < MEASURE_RUNS; r++) {
                long start = System.nanoTime();
                LCS.findLCSLength(s1, s2);
                totalLcsTime += (System.nanoTime() - start);
            }
            double avgLcsMs = (totalLcsTime / (double) MEASURE_RUNS) / 1_000_000.0;

            long totalEdTime = 0;
            for (int r = 0; r < MEASURE_RUNS; r++) {
                long start = System.nanoTime();
                EditDistance.calculate(s1, s2);
                totalEdTime += (System.nanoTime() - start);
            }
            double avgEdMs = (totalEdTime / (double) MEASURE_RUNS) / 1_000_000.0;

            System.out.printf("%-10d | %-20.4f | %-20.4f%n", size, avgLcsMs, avgEdMs);
        }
    }

    private static void runStringMatchingBenchmarks() {
        System.out.println("\n--- 2. String Matching (Naive, KMP, Rabin-Karp) ---");
        int[] textSizes = {10000, 50000, 100000};
        int patternLen = 50;

        System.out.printf("%-10s | %-15s | %-18s | %-18s%n", "Text Len", "Algorithm", "Present Time (ms)", "Absent Time (ms)");
        System.out.println("-".repeat(70));

        for (int textSize : textSizes) {
            String text = DataGenerator.generateRandomString(textSize);
            String presentPattern = text.substring(textSize / 2, textSize / 2 + patternLen);
            String absentPattern = "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";

            double naivePresent = benchmarkMatching("naive", text, presentPattern);
            double naiveAbsent = benchmarkMatching("naive", text, absentPattern);

            double kmpPresent = benchmarkMatching("kmp", text, presentPattern);
            double kmpAbsent = benchmarkMatching("kmp", text, absentPattern);

            double rkPresent = benchmarkMatching("rk", text, presentPattern);
            double rkAbsent = benchmarkMatching("rk", text, absentPattern);

            System.out.printf("%-10d | %-15s | %-18.4f | %-18.4f%n", textSize, "Naive", naivePresent, naiveAbsent);
            System.out.printf("%-10s | %-15s | %-18.4f | %-18.4f%n", "", "KMP", kmpPresent, kmpAbsent);
            System.out.printf("%-10s | %-15s | %-18.4f | %-18.4f%n", "", "Rabin-Karp", rkPresent, rkAbsent);
            System.out.println("-".repeat(70));
        }
    }

    private static double benchmarkMatching(String algo, String text, String pattern) {
        for (int w = 0; w < WARMUP_RUNS; w++) {
            runMatch(algo, text, pattern);
        }

        long totalTime = 0;
        for (int r = 0; r < MEASURE_RUNS; r++) {
            long start = System.nanoTime();
            runMatch(algo, text, pattern);
            totalTime += (System.nanoTime() - start);
        }
        return (totalTime / (double) MEASURE_RUNS) / 1_000_000.0;
    }

    private static List<Integer> runMatch(String algo, String text, String pattern) {
        return switch (algo) {
            case "naive" -> NaiveMatching.search(text, pattern);
            case "kmp" -> KMPMatching.search(text, pattern);
            case "rk" -> RabinKarpMatching.search(text, pattern);
            default -> throw new IllegalArgumentException("Unknown algo");
        };
    }

    private static void runKasaiBenchmarks() {
        System.out.println("\n--- 3. Kasai's LCP Algorithm ---");
        int[] sizes = {1000, 5000, 10000};

        System.out.printf("%-10s | %-25s%n", "String Len", "Kasai LCP Avg Time (ms)");
        System.out.println("-".repeat(40));

        for (int size : sizes) {
            String text = DataGenerator.generateRandomString(size);
            int[] sa = SimpleSuffixArray.build(text);

            for (int w = 0; w < WARMUP_RUNS; w++) {
                KasaiLCP.buildLCP(text, sa);
            }

            long totalTime = 0;
            for (int r = 0; r < MEASURE_RUNS; r++) {
                long start = System.nanoTime();
                KasaiLCP.buildLCP(text, sa);
                totalTime += (System.nanoTime() - start);
            }
            double avgMs = (totalTime / (double) MEASURE_RUNS) / 1_000_000.0;

            System.out.printf("%-10d | %-25.4f%n", size, avgMs);
        }
    }
}