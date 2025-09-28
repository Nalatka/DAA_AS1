package org.example.benchmark;

import org.example.algorithms.DeterministicSelect;
import org.example.util.ArrayUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SelectBenchmark {
    
    @Param({"100", "1000", "10000", "100000"})
    public int size;
    
    private int[] array;
    private int k;
    
    @Setup(Level.Iteration)
    public void setup() {
        array = ArrayUtils.generateRandomArray(size, 1000);
        k = size / 2;
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
    public int quickSelect() {
        int[] copy = array.clone();
        return quickSelect(copy, 0, copy.length - 1, k);
    }
    
    private int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }
        
        int pivotIndex = partition(arr, left, right);
        int leftSize = pivotIndex - left;
        
        if (k == leftSize) {
            return arr[pivotIndex];
        } else if (k < leftSize) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k - leftSize - 1);
        }
    }
    
    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;
        
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, right);
        return i + 1;
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SelectBenchmark.class.getSimpleName())
                .build();
        
        new Runner(opt).run();
    }
}
