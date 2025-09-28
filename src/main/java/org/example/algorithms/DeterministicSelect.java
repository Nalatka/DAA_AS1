package org.example.algorithms;

import org.example.metrics.MetricsCollector;
import org.example.util.ValidationUtils;
import java.util.Arrays;

public class DeterministicSelect {
    
    public static int select(int[] array, int k, MetricsCollector metrics) {
        ValidationUtils.validateArray(array);
        ValidationUtils.validateKthElement(array, k);
        
        if (array.length == 1) {
            return array[0];
        }
        
        metrics.start();
        int result = select(array, 0, array.length - 1, k, metrics);
        metrics.stop();
        return result;
    }
    
    public static int select(int[] array, int k) {
        ValidationUtils.validateArray(array);
        ValidationUtils.validateKthElement(array, k);
        
        if (array.length == 1) {
            return array[0];
        }
        
        return select(array, 0, array.length - 1, k, null);
    }
    
    private static int select(int[] array, int left, int right, int k, MetricsCollector metrics) {
        if (left == right) {
            return array[left];
        }
        
        if (metrics != null) {
            metrics.enterRecursion();
        }
        
        int pivotIndex = medianOfMedians(array, left, right, metrics);
        pivotIndex = partition(array, left, right, pivotIndex, metrics);
        
        int leftSize = pivotIndex - left;
        
        if (k == leftSize) {
            if (metrics != null) {
                metrics.exitRecursion();
            }
            return array[pivotIndex];
        } else if (k < leftSize) {
            int result = select(array, left, pivotIndex - 1, k, metrics);
            if (metrics != null) {
                metrics.exitRecursion();
            }
            return result;
        } else {
            int result = select(array, pivotIndex + 1, right, k - leftSize - 1, metrics);
            if (metrics != null) {
                metrics.exitRecursion();
            }
            return result;
        }
    }
    
    private static int medianOfMedians(int[] array, int left, int right, MetricsCollector metrics) {
        int n = right - left + 1;
        
        if (n <= 5) {
            return medianOfFive(array, left, right, metrics);
        }
        
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];
        
        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);
            medians[i] = medianOfFive(array, groupLeft, groupRight, metrics);
        }
        
        return select(medians, 0, numGroups - 1, numGroups / 2, metrics);
    }
    
    private static int medianOfFive(int[] array, int left, int right, MetricsCollector metrics) {
        int[] temp = Arrays.copyOfRange(array, left, right + 1);
        Arrays.sort(temp);
        
        if (metrics != null) {
            metrics.incrementAllocations(temp.length);
            metrics.incrementArrayAccesses(temp.length * 2);
        }
        
        return left + (right - left) / 2;
    }
    
    private static int partition(int[] array, int left, int right, int pivotIndex, MetricsCollector metrics) {
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
    
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
