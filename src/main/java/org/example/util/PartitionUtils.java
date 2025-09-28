package org.example.util;

import org.example.metrics.MetricsCollector;

public class PartitionUtils {
    
    public static int partition(int[] array, int left, int right, int pivotIndex, MetricsCollector metrics) {
        swap(array, pivotIndex, right);
        
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
    
    public static int randomPartition(int[] array, int left, int right, MetricsCollector metrics) {
        int randomIndex = left + (int) (Math.random() * (right - left + 1));
        return partition(array, left, right, randomIndex, metrics);
    }
    
    public static int medianOfThree(int[] array, int left, int right) {
        int mid = left + (right - left) / 2;
        
        if (array[left] > array[mid]) {
            swap(array, left, mid);
        }
        if (array[left] > array[right]) {
            swap(array, left, right);
        }
        if (array[mid] > array[right]) {
            swap(array, mid, right);
        }
        
        return mid;
    }
    
    public static int medianOfThree(int[] array, int left, int right, MetricsCollector metrics) {
        int mid = left + (right - left) / 2;
        
        if (metrics != null) {
            metrics.incrementComparisons();
            metrics.incrementArrayAccesses(2);
        }
        
        if (array[left] > array[mid]) {
            swap(array, left, mid);
            if (metrics != null) {
                metrics.incrementSwaps();
                metrics.incrementArrayAccesses(4);
            }
        }
        
        if (metrics != null) {
            metrics.incrementComparisons();
            metrics.incrementArrayAccesses(2);
        }
        
        if (array[left] > array[right]) {
            swap(array, left, right);
            if (metrics != null) {
                metrics.incrementSwaps();
                metrics.incrementArrayAccesses(4);
            }
        }
        
        if (metrics != null) {
            metrics.incrementComparisons();
            metrics.incrementArrayAccesses(2);
        }
        
        if (array[mid] > array[right]) {
            swap(array, mid, right);
            if (metrics != null) {
                metrics.incrementSwaps();
                metrics.incrementArrayAccesses(4);
            }
        }
        
        return mid;
    }
    
    public static int hoarePartition(int[] array, int left, int right, int pivotIndex, MetricsCollector metrics) {
        swap(array, pivotIndex, left);
        
        if (metrics != null) {
            metrics.incrementSwaps();
            metrics.incrementArrayAccesses(4);
        }
        
        int pivot = array[left];
        int i = left - 1;
        int j = right + 1;
        
        while (true) {
            do {
                i++;
                if (metrics != null) {
                    metrics.incrementComparisons();
                    metrics.incrementArrayAccesses();
                }
            } while (array[i] < pivot);
            
            do {
                j--;
                if (metrics != null) {
                    metrics.incrementComparisons();
                    metrics.incrementArrayAccesses();
                }
            } while (array[j] > pivot);
            
            if (i >= j) {
                return j;
            }
            
            swap(array, i, j);
            
            if (metrics != null) {
                metrics.incrementSwaps();
                metrics.incrementArrayAccesses(4);
            }
        }
    }
    
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
