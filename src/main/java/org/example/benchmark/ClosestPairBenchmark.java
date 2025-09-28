package org.example.benchmark;

import org.example.algorithms.ClosestPair;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ClosestPairBenchmark {
    
    @Param({"100", "1000", "10000", "100000"})
    public int size;
    
    private ClosestPair.Point[] points;
    private final Random random = new Random(42);
    
    @Setup(Level.Iteration)
    public void setup() {
        points = generateRandomPoints(size);
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
                .include(ClosestPairBenchmark.class.getSimpleName())
                .build();
        
        new Runner(opt).run();
    }
}
