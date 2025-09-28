package org.example.algorithms;

import org.example.metrics.MetricsCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class MergeSortTest {
    private Random random;
    
    @BeforeEach
    public void setUp() {
        random = new Random(42); // Fixed seed for reproducible tests
    }
    
    @Test
    public void testSortEmptyArray() {
        int[] array = {};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }
    
    @Test
    public void testSortSingleElement() {
        int[] array = {42};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{42}, array);
    }
    
    @Test
    public void testSortTwoElements() {
        int[] array = {2, 1};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{1, 2}, array);
    }
    
    @Test
    public void testSortAlreadySorted() {
        int[] array = {1, 2, 3, 4, 5};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }
    
    @Test
    public void testSortReverseSorted() {
        int[] array = {5, 4, 3, 2, 1};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }
    
    @Test
    public void testSortRandomArray() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{11, 12, 22, 25, 34, 64, 90}, array);
    }
    
    @Test
    public void testSortWithDuplicates() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, array);
    }
    
    @Test
    public void testSortLargeRandomArray() {
        int[] array = generateRandomArray(1000);
        int[] expected = array.clone();
        Arrays.sort(expected);
        
        MergeSort.sort(array);
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void testSortWithMetrics() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        MetricsCollector metrics = new MetricsCollector();
        
        MergeSort.sort(array, metrics);
        
        assertArrayEquals(new int[]{11, 12, 22, 25, 34, 64, 90}, array);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
        assertTrue(metrics.getPerformanceMetrics().getExecutionTimeNanos() > 0);
        assertTrue(metrics.getDepthTracker().getMaxDepth() > 0);
    }
    
    @Test
    public void testSortSmallArrayUsesInsertionSort() {
        // Test with array size <= CUTOFF_THRESHOLD (15)
        int[] array = {5, 2, 8, 1, 9, 3, 7, 4, 6, 10, 11, 12, 13, 14, 15};
        MetricsCollector metrics = new MetricsCollector();
        
        MergeSort.sort(array, metrics);
        
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, array);
        // Should still work correctly even with insertion sort cutoff
    }
    
    @Test
    public void testSortNullArray() {
        assertDoesNotThrow(() -> MergeSort.sort(null));
    }
    
    @Test
    public void testSortWithNegativeNumbers() {
        int[] array = {-3, -1, -4, -1, -5, -9, -2, -6, -5, -3};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{-9, -6, -5, -5, -4, -3, -3, -2, -1, -1}, array);
    }
    
    @Test
    public void testSortWithMixedSigns() {
        int[] array = {3, -1, 4, -1, 5, -9, 2, -6, 5, -3};
        MergeSort.sort(array);
        assertArrayEquals(new int[]{-9, -6, -3, -1, -1, 2, 3, 4, 5, 5}, array);
    }
    
    @Test
    public void testSortPerformanceComparison() {
        int[] array = generateRandomArray(10000);
        int[] arrayCopy = array.clone();
        
        // Test without metrics
        long startTime = System.nanoTime();
        MergeSort.sort(array);
        long mergeSortTime = System.nanoTime() - startTime;
        
        // Test with metrics
        MetricsCollector metrics = new MetricsCollector();
        startTime = System.nanoTime();
        MergeSort.sort(arrayCopy, metrics);
        long mergeSortWithMetricsTime = System.nanoTime() - startTime;
        
        // Both should produce the same result
        assertArrayEquals(array, arrayCopy);
        
        // Metrics overhead should be minimal
        assertTrue(mergeSortWithMetricsTime < mergeSortTime * 2);
    }
    
    @Test
    public void testRecursionDepth() {
        int[] array = generateRandomArray(1000);
        MetricsCollector metrics = new MetricsCollector();
        
        MergeSort.sort(array, metrics);
        
        // For array of size 1000, max depth should be around log2(1000) ≈ 10
        int maxDepth = metrics.getDepthTracker().getMaxDepth();
        assertTrue(maxDepth >= 8 && maxDepth <= 12, 
                "Expected depth around log2(1000)≈10, got " + maxDepth);
    }
    
    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
}
