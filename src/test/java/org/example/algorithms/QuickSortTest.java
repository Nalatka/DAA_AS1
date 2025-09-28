package org.example.algorithms;

import org.example.metrics.MetricsCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {
    private Random random;
    
    @BeforeEach
    public void setUp() {
        random = new Random(42);
    }
    
    @Test
    public void testSortEmptyArray() {
        int[] array = {};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }
    
    @Test
    public void testSortSingleElement() {
        int[] array = {42};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{42}, array);
    }
    
    @Test
    public void testSortTwoElements() {
        int[] array = {2, 1};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{1, 2}, array);
    }
    
    @Test
    public void testSortAlreadySorted() {
        int[] array = {1, 2, 3, 4, 5};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }
    
    @Test
    public void testSortReverseSorted() {
        int[] array = {5, 4, 3, 2, 1};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }
    
    @Test
    public void testSortRandomArray() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{11, 12, 22, 25, 34, 64, 90}, array);
    }
    
    @Test
    public void testSortWithDuplicates() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, array);
    }
    
    @Test
    public void testSortLargeRandomArray() {
        int[] array = generateRandomArray(1000);
        int[] expected = array.clone();
        Arrays.sort(expected);
        
        QuickSort.sort(array);
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void testSortWithMetrics() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        MetricsCollector metrics = new MetricsCollector();
        
        QuickSort.sort(array, metrics);
        
        assertArrayEquals(new int[]{11, 12, 22, 25, 34, 64, 90}, array);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
        assertTrue(metrics.getPerformanceMetrics().getExecutionTimeNanos() > 0);
        assertTrue(metrics.getPerformanceMetrics().getSwaps() > 0);
    }
    
    @Test
    public void testSortNullArray() {
        assertDoesNotThrow(() -> QuickSort.sort(null));
    }
    
    @Test
    public void testSortWithNegativeNumbers() {
        int[] array = {-3, -1, -4, -1, -5, -9, -2, -6, -5, -3};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{-9, -6, -5, -5, -4, -3, -3, -2, -1, -1}, array);
    }
    
    @Test
    public void testSortWithMixedSigns() {
        int[] array = {3, -1, 4, -1, 5, -9, 2, -6, 5, -3};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{-9, -6, -3, -1, -1, 2, 3, 4, 5, 5}, array);
    }
    
    @Test
    public void testSortAdversarialCase() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        MetricsCollector metrics = new MetricsCollector();
        
        QuickSort.sort(array, metrics);
        
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, array);
    }
    
    @Test
    public void testSortAllSameElements() {
        int[] array = {5, 5, 5, 5, 5, 5, 5, 5};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5, 5, 5, 5}, array);
    }
    
    @Test
    public void testRecursionDepthBounded() {
        int[] array = generateRandomArray(1000);
        MetricsCollector metrics = new MetricsCollector();
        
        QuickSort.sort(array, metrics);
        
        int maxDepth = metrics.getDepthTracker().getMaxDepth();
        assertTrue(maxDepth <= 2 * (int) Math.ceil(Math.log(array.length) / Math.log(2)) + 2,
                "Expected depth <= 2*log2(n)+2, got " + maxDepth);
    }
    
    @Test
    public void testSortPerformanceComparison() {
        int[] array = generateRandomArray(10000);
        int[] arrayCopy = array.clone();
        
        long startTime = System.nanoTime();
        QuickSort.sort(array);
        long quickSortTime = System.nanoTime() - startTime;
        
        MetricsCollector metrics = new MetricsCollector();
        startTime = System.nanoTime();
        QuickSort.sort(arrayCopy, metrics);
        long quickSortWithMetricsTime = System.nanoTime() - startTime;
        
        assertArrayEquals(array, arrayCopy);
        assertTrue(quickSortWithMetricsTime < quickSortTime * 2);
    }
    
    @Test
    public void testSortMultipleRuns() {
        for (int i = 0; i < 10; i++) {
            int[] array = generateRandomArray(100);
            int[] expected = array.clone();
            Arrays.sort(expected);
            
            QuickSort.sort(array);
            assertArrayEquals(expected, array);
        }
    }
    
    @Test
    public void testSortWithExtremeValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -1, 1};
        QuickSort.sort(array);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, array);
    }
    
    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
}
