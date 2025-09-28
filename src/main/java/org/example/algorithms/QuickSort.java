package org.example.algorithms;

import org.example.metrics.MetricsCollector;
import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();
    
    public static void sort(int[] array, MetricsCollector metrics) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        metrics.start();
        sort(array, 0, array.length - 1, metrics);
        metrics.stop();
    }
    
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        sort(array, 0, array.length - 1, null);
    }
    
    private static void sort(int[] array, int left, int right, MetricsCollector metrics) {
        while (left < right) {
            int pivotIndex = partition(array, left, right, metrics);
            
            if (metrics != null) {
                metrics.enterRecursion();
            }
            
            int leftSize = pivotIndex - left;
            int rightSize = right - pivotIndex;
            
            if (leftSize < rightSize) {
                sort(array, left, pivotIndex - 1, metrics);
                left = pivotIndex + 1;
            } else {
                sort(array, pivotIndex + 1, right, metrics);
                right = pivotIndex - 1;
            }
            
            if (metrics != null) {
                metrics.exitRecursion();
            }
        }
    }
    
    private static int partition(int[] array, int left, int right, MetricsCollector metrics) {
        int randomIndex = left + random.nextInt(right - left + 1);
        swap(array, randomIndex, right);
        
        if (metrics != null) {
            metrics.incrementSwaps();
            metrics.incrementArrayAccesses(4);
        }
        
        int pivot = array[right];
        int i = left - 1;
        
        for (int j = left; j < right; j++) {
            if (metrics != null) {
                metrics.incrementComparisons();
                metrics.incrementArrayAccesses(2);
            }
            
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
                
                if (metrics != null) {
                    metrics.incrementSwaps();
                    metrics.incrementArrayAccesses(4);
                }
            }
        }
        
        swap(array, i + 1, right);
        
        if (metrics != null) {
            metrics.incrementSwaps();
            metrics.incrementArrayAccesses(4);
        }
        
        return i + 1;
    }
    
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
