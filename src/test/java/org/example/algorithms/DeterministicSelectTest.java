package org.example.algorithms;

import org.example.metrics.MetricsCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    private Random random;
    
    @BeforeEach
    public void setUp() {
        random = new Random(42);
    }
    
    @Test
    public void testSelectSingleElement() {
        int[] array = {42};
        assertEquals(42, DeterministicSelect.select(array, 0));
    }
    
    @Test
    public void testSelectTwoElements() {
        int[] array = {3, 1};
        assertEquals(1, DeterministicSelect.select(array, 0));
        assertEquals(3, DeterministicSelect.select(array, 1));
    }
    
    @Test
    public void testSelectSortedArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < array.length; i++) {
            assertEquals(i + 1, DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectReverseSortedArray() {
        int[] array = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        for (int i = 0; i < array.length; i++) {
            assertEquals(i + 1, DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectRandomArray() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectWithDuplicates() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectLargeRandomArray() {
        int[] array = generateRandomArray(1000);
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        
        for (int i = 0; i < 100; i++) {
            int k = random.nextInt(array.length);
            assertEquals(sorted[k], DeterministicSelect.select(array, k));
        }
    }
    
    @Test
    public void testSelectWithMetrics() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        MetricsCollector metrics = new MetricsCollector();
        
        int result = DeterministicSelect.select(array, 3, metrics);
        
        assertEquals(25, result);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
        assertTrue(metrics.getPerformanceMetrics().getExecutionTimeNanos() > 0);
        assertTrue(metrics.getPerformanceMetrics().getSwaps() > 0);
    }
    
    @Test
    public void testSelectNullArray() {
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(null, 0));
    }
    
    @Test
    public void testSelectInvalidK() {
        int[] array = {1, 2, 3, 4, 5};
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(array, -1));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(array, 5));
    }
    
    @Test
    public void testSelectWithNegativeNumbers() {
        int[] array = {-3, -1, -4, -1, -5, -9, -2, -6, -5, -3};
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectWithMixedSigns() {
        int[] array = {3, -1, 4, -1, 5, -9, 2, -6, 5, -3};
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectAllSameElements() {
        int[] array = {5, 5, 5, 5, 5, 5, 5, 5};
        for (int i = 0; i < array.length; i++) {
            assertEquals(5, DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectPerformanceComparison() {
        int[] array = generateRandomArray(10000);
        int[] arrayCopy = array.clone();
        
        long startTime = System.nanoTime();
        int result1 = DeterministicSelect.select(array, 5000);
        long selectTime = System.nanoTime() - startTime;
        
        MetricsCollector metrics = new MetricsCollector();
        startTime = System.nanoTime();
        int result2 = DeterministicSelect.select(arrayCopy, 5000, metrics);
        long selectWithMetricsTime = System.nanoTime() - startTime;
        
        assertEquals(result1, result2);
        assertTrue(selectWithMetricsTime < selectTime * 2);
    }
    
    @Test
    public void testSelectRecursionDepth() {
        int[] array = generateRandomArray(1000);
        MetricsCollector metrics = new MetricsCollector();
        
        DeterministicSelect.select(array, 500, metrics);
        
        int maxDepth = metrics.getDepthTracker().getMaxDepth();
        assertTrue(maxDepth <= 2 * (int) Math.ceil(Math.log(array.length) / Math.log(2)) + 2,
                "Expected depth <= 2*log2(n)+2, got " + maxDepth);
    }
    
    @Test
    public void testSelectMultipleRuns() {
        for (int i = 0; i < 10; i++) {
            int[] array = generateRandomArray(100);
            int[] sorted = array.clone();
            Arrays.sort(sorted);
            
            int k = random.nextInt(array.length);
            assertEquals(sorted[k], DeterministicSelect.select(array, k));
        }
    }
    
    @Test
    public void testSelectWithExtremeValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -1, 1};
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], DeterministicSelect.select(array, i));
        }
    }
    
    @Test
    public void testSelectMedian() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        
        int medianIndex = array.length / 2;
        assertEquals(sorted[medianIndex], DeterministicSelect.select(array, medianIndex));
    }
    
    @Test
    public void testSelectMinimum() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        assertEquals(1, DeterministicSelect.select(array, 0));
    }
    
    @Test
    public void testSelectMaximum() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        assertEquals(9, DeterministicSelect.select(array, array.length - 1));
    }
    
    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
}
