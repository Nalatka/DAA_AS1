package org.example.algorithms;

import org.example.metrics.MetricsCollector;

public class MergeSort {
    private static final int CUTOFF_THRESHOLD = 15;
    
    public static void sort(int[] array, MetricsCollector metrics) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        metrics.start();
        int[] buffer = new int[array.length];
        sort(array, 0, array.length - 1, buffer, metrics);
        metrics.stop();
    }
    
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        int[] buffer = new int[array.length];
        sort(array, 0, array.length - 1, buffer, null);
    }
    
    private static void sort(int[] array, int left, int right, int[] buffer, MetricsCollector metrics) {
        if (left >= right) {
            return;
        }
        
        if (right - left + 1 <= CUTOFF_THRESHOLD) {
            insertionSort(array, left, right, metrics);
            return;
        }
        
        if (metrics != null) {
            metrics.enterRecursion();
        }
        
        int mid = left + (right - left) / 2;
        
        sort(array, left, mid, buffer, metrics);
        sort(array, mid + 1, right, buffer, metrics);
        
        merge(array, left, mid, right, buffer, metrics);
        
        if (metrics != null) {
            metrics.exitRecursion();
        }
    }
    
    private static void merge(int[] array, int left, int mid, int right, int[] buffer, MetricsCollector metrics) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        
        for (int i = 0; i < leftSize; i++) {
            buffer[i] = array[left + i];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2);
            }
        }
        
        for (int i = 0; i < rightSize; i++) {
            buffer[leftSize + i] = array[mid + 1 + i];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2);
            }
        }
        
        int i = 0, j = leftSize, k = left;
        
        while (i < leftSize && j < leftSize + rightSize) {
            if (metrics != null) {
                metrics.incrementComparisons();
                metrics.incrementArrayAccesses(2);
            }
            
            if (buffer[i] <= buffer[j]) {
                array[k] = buffer[i];
                i++;
            } else {
                array[k] = buffer[j];
                j++;
            }
            
            if (metrics != null) {
                metrics.incrementArrayAccesses();
            }
            k++;
        }
        
        while (i < leftSize) {
            array[k] = buffer[i];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2);
            }
            i++;
            k++;
        }
        
        while (j < leftSize + rightSize) {
            array[k] = buffer[j];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2);
            }
            j++;
            k++;
        }
    }
    
    private static void insertionSort(int[] array, int left, int right, MetricsCollector metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            if (metrics != null) {
                metrics.incrementArrayAccesses();
            }
            
            int j = i - 1;
            
            while (j >= left) {
                if (metrics != null) {
                    metrics.incrementComparisons();
                    metrics.incrementArrayAccesses();
                }
                
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    if (metrics != null) {
                        metrics.incrementArrayAccesses(2);
                    }
                    j--;
                } else {
                    break;
                }
            }
            
            array[j + 1] = key;
            if (metrics != null) {
                metrics.incrementArrayAccesses();
            }
        }
    }
}
