package org.example;

import org.example.algorithms.*;
import org.example.metrics.CSVWriter;
import org.example.metrics.MetricsCollector;
import org.example.util.ArrayUtils;
import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final Random random = new Random(42);
    
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help") || args[0].equals("-h")) {
            printUsage();
            return;
        }
        
        try {
            String algorithm = args[0];
            int size = args.length > 1 ? Integer.parseInt(args[1]) : 1000;
            int runs = args.length > 2 ? Integer.parseInt(args[2]) : 1;
            String outputFile = args.length > 3 ? args[3] : "results.csv";
            
            runAlgorithm(algorithm, size, runs, outputFile);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            printUsage();
        }
    }
    
    private static void printUsage() {
        System.out.println("Divide-and-Conquer Algorithms Performance Tool");
        System.out.println();
        System.out.println("Usage: java -jar app.jar <algorithm> [size] [runs] [output]");
        System.out.println();
        System.out.println("Algorithms:");
        System.out.println("  mergesort    - MergeSort with linear merge and cutoff");
        System.out.println("  quicksort    - QuickSort with randomized pivot");
        System.out.println("  select       - Deterministic select (median-of-medians)");
        System.out.println("  closest      - Closest pair of points (2D)");
        System.out.println("  all          - Run all algorithms");
        System.out.println();
        System.out.println("Parameters:");
        System.out.println("  size         - Input size (default: 1000)");
        System.out.println("  runs         - Number of runs (default: 1)");
        System.out.println("  output       - Output CSV file (default: results.csv)");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar app.jar mergesort 10000 5 results.csv");
        System.out.println("  java -jar app.jar all 1000 10");
        System.out.println("  java -jar app.jar closest 500");
    }
    
    private static void runAlgorithm(String algorithm, int size, int runs, String outputFile) throws Exception {
        CSVWriter csvWriter = new CSVWriter(outputFile);
        csvWriter.setHeaders("Algorithm", "Size", "Run", "Time(ms)", "Comparisons", 
                           "ArrayAccesses", "Allocations", "Swaps", "MaxDepth");
        
        System.out.println("Running " + algorithm + " with size " + size + " for " + runs + " runs");
        
        switch (algorithm.toLowerCase()) {
            case "mergesort":
                runMergeSort(size, runs, csvWriter);
                break;
            case "quicksort":
                runQuickSort(size, runs, csvWriter);
                break;
            case "select":
                runSelect(size, runs, csvWriter);
                break;
            case "closest":
                runClosestPair(size, runs, csvWriter);
                break;
            case "all":
                runAllAlgorithms(size, runs, csvWriter);
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
        
        csvWriter.writeToFile();
        System.out.println("Results written to " + outputFile);
    }
    
    private static void runMergeSort(int size, int runs, CSVWriter csvWriter) throws Exception {
        for (int run = 1; run <= runs; run++) {
            int[] array = ArrayUtils.generateRandomArray(size, 1000);
            MetricsCollector metrics = new MetricsCollector();
            
            MergeSort.sort(array, metrics);
            
            csvWriter.addMetricsRow("MergeSort", size, metrics.getPerformanceMetrics(), 
                                  metrics.getDepthTracker());
            
            System.out.println("Run " + run + ": " + metrics.getSummary());
        }
    }
    
    private static void runQuickSort(int size, int runs, CSVWriter csvWriter) throws Exception {
        for (int run = 1; run <= runs; run++) {
            int[] array = ArrayUtils.generateRandomArray(size, 1000);
            MetricsCollector metrics = new MetricsCollector();
            
            QuickSort.sort(array, metrics);
            
            csvWriter.addMetricsRow("QuickSort", size, metrics.getPerformanceMetrics(), 
                                  metrics.getDepthTracker());
            
            System.out.println("Run " + run + ": " + metrics.getSummary());
        }
    }
    
    private static void runSelect(int size, int runs, CSVWriter csvWriter) throws Exception {
        for (int run = 1; run <= runs; run++) {
            int[] array = ArrayUtils.generateRandomArray(size, 1000);
            MetricsCollector metrics = new MetricsCollector();
            
            int k = random.nextInt(size);
            DeterministicSelect.select(array, k, metrics);
            
            csvWriter.addMetricsRow("Select", size, metrics.getPerformanceMetrics(), 
                                  metrics.getDepthTracker());
            
            System.out.println("Run " + run + ": " + metrics.getSummary());
        }
    }
    
    private static void runClosestPair(int size, int runs, CSVWriter csvWriter) throws Exception {
        for (int run = 1; run <= runs; run++) {
            ClosestPair.Point[] points = generateRandomPoints(size);
            MetricsCollector metrics = new MetricsCollector();
            
            ClosestPair.findClosestPair(points, metrics);
            
            csvWriter.addMetricsRow("ClosestPair", size, metrics.getPerformanceMetrics(), 
                                  metrics.getDepthTracker());
            
            System.out.println("Run " + run + ": " + metrics.getSummary());
        }
    }
    
    private static void runAllAlgorithms(int size, int runs, CSVWriter csvWriter) throws Exception {
        runMergeSort(size, runs, csvWriter);
        runQuickSort(size, runs, csvWriter);
        runSelect(size, runs, csvWriter);
        runClosestPair(size, runs, csvWriter);
    }
    
    private static ClosestPair.Point[] generateRandomPoints(int count) {
        ClosestPair.Point[] points = new ClosestPair.Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new ClosestPair.Point(
                random.nextDouble() * 1000,
                random.nextDouble() * 1000
            );
        }
        return points;
    }
}
