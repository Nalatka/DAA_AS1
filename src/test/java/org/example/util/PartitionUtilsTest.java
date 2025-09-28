package org.example.util;

import org.example.metrics.MetricsCollector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PartitionUtilsTest {
    
    @Test
    public void testPartition() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = PartitionUtils.partition(array, 0, 7, 2, null);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
        
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(array[i] <= array[pivotIndex]);
        }
        for (int i = pivotIndex + 1; i < array.length; i++) {
            assertTrue(array[i] >= array[pivotIndex]);
        }
    }
    
    @Test
    public void testPartitionWithMetrics() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        MetricsCollector metrics = new MetricsCollector();
        int pivotIndex = PartitionUtils.partition(array, 0, 7, 2, metrics);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
        assertTrue(metrics.getPerformanceMetrics().getSwaps() > 0);
    }
    
    @Test
    public void testRandomPartition() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = PartitionUtils.randomPartition(array, 0, 7, null);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
    }
    
    @Test
    public void testRandomPartitionWithMetrics() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        MetricsCollector metrics = new MetricsCollector();
        int pivotIndex = PartitionUtils.randomPartition(array, 0, 7, metrics);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
        assertTrue(metrics.getPerformanceMetrics().getSwaps() > 0);
    }
    
    @Test
    public void testMedianOfThree() {
        int[] array = {9, 1, 5, 3, 7, 2, 8, 4, 6};
        int medianIndex = PartitionUtils.medianOfThree(array, 0, 8);
        
        assertTrue(medianIndex >= 0 && medianIndex < array.length);
    }
    
    @Test
    public void testMedianOfThreeWithMetrics() {
        int[] array = {9, 1, 5, 3, 7, 2, 8, 4, 6};
        MetricsCollector metrics = new MetricsCollector();
        int medianIndex = PartitionUtils.medianOfThree(array, 0, 8, metrics);
        
        assertTrue(medianIndex >= 0 && medianIndex < array.length);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
    }
    
    @Test
    public void testHoarePartition() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = PartitionUtils.hoarePartition(array, 0, 7, 2, null);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
    }
    
    @Test
    public void testHoarePartitionWithMetrics() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        MetricsCollector metrics = new MetricsCollector();
        int pivotIndex = PartitionUtils.hoarePartition(array, 0, 7, 2, metrics);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
        assertTrue(metrics.getPerformanceMetrics().getSwaps() > 0);
    }
    
    @Test
    public void testPartitionSingleElement() {
        int[] array = {42};
        int pivotIndex = PartitionUtils.partition(array, 0, 0, 0, null);
        
        assertEquals(0, pivotIndex);
    }
    
    @Test
    public void testPartitionTwoElements() {
        int[] array = {3, 1};
        int pivotIndex = PartitionUtils.partition(array, 0, 1, 0, null);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
    }
    
    @Test
    public void testPartitionAllSameElements() {
        int[] array = {5, 5, 5, 5, 5};
        int pivotIndex = PartitionUtils.partition(array, 0, 4, 2, null);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
    }
}
