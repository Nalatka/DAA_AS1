package org.example.benchmark;

import org.example.algorithms.*;
import org.example.util.ArrayUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.OPERATIONS_PER_SECOND)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ComprehensiveBenchmark {
    
    @Param({"1000", "10000"})
    public int size;
    
    private int[] array;
    private ClosestPair.Point[] points;
    private int k;
    private final Random random = new Random(42);
    
    @Setup(Level.Iteration)
    public void setup() {
        array = ArrayUtils.generateRandomArray(size, 1000);
        points = generateRandomPoints(size);
        k = size / 2;
    }
    
    @Benchmark
    public void mergeSort() {
        MergeSort.sort(array.clone());
    }
    
    @Benchmark
    public void quickSort() {
        QuickSort.sort(array.clone());
    }
    
    @Benchmark
    public void javaSort() {
        int[] copy = array.clone();
        Arrays.sort(copy);
    }
    
    @Benchmark
    public int deterministicSelect() {
        return DeterministicSelect.select(array.clone(), k);
    }
    
    @Benchmark
    public int javaSortSelect() {
        int[] copy = array.clone();
        Arrays.sort(copy);
        return copy[k];
    }
    
    @Benchmark
    public ClosestPair.Pair closestPair() {
        return ClosestPair.findClosestPair(points.clone());
    }
    
    @Benchmark
    public ClosestPair.Pair bruteForceClosestPair() {
        return bruteForce(points.clone());
    }
    
    private ClosestPair.Pair bruteForce(ClosestPair.Point[] points) {
        double minDistance = Double.POSITIVE_INFINITY;
        ClosestPair.Point p1 = null, p2 = null;
        
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    p1 = points[i];
                    p2 = points[j];
                }
            }
        }
        
        return new ClosestPair.Pair(p1, p2);
    }
    
    private ClosestPair.Point[] generateRandomPoints(int count) {
        ClosestPair.Point[] points = new ClosestPair.Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new ClosestPair.Point(
                random.nextDouble() * 1000,
                random.nextDouble() * 1000
            );
        }
        return points;
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ComprehensiveBenchmark.class.getSimpleName())
                .build();
        
        new Runner(opt).run();
    }
}
