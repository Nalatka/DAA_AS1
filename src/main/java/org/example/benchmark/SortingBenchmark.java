package org.example.benchmark;

import org.example.algorithms.MergeSort;
import org.example.algorithms.QuickSort;
import org.example.util.ArrayUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SortingBenchmark {
    
    @Param({"100", "1000", "10000", "100000"})
    public int size;
    
    private int[] array;
    
    @Setup(Level.Iteration)
    public void setup() {
        array = ArrayUtils.generateRandomArray(size, 1000);
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
        java.util.Arrays.sort(copy);
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SortingBenchmark.class.getSimpleName())
                .build();
        
        new Runner(opt).run();
    }
}
