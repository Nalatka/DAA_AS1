package org.example.algorithms;

import org.example.metrics.MetricsCollector;

/**
 * MergeSort implementation with linear merge, reusable buffer, and small-n cutoff.
 * Uses insertion sort for small arrays to improve performance.
 * 
 * Time Complexity: O(n log n) - Master Theorem Case 2
 * Space Complexity: O(n) - for the reusable buffer
 * Recursion Depth: O(log n)
 */
public class MergeSort {
    private static final int CUTOFF_THRESHOLD = 15; // Use insertion sort for arrays ≤ 15
    
    /**
     * Sort an array using MergeSort algorithm.
     * 
     * @param array the array to sort
     * @param metrics metrics collector for performance tracking
     */
    public static void sort(int[] array, MetricsCollector metrics) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        metrics.start();
        int[] buffer = new int[array.length];
        sort(array, 0, array.length - 1, buffer, metrics);
        metrics.stop();
    }
    
    /**
     * Sort an array using MergeSort algorithm without metrics.
     * 
     * @param array the array to sort
     */
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        int[] buffer = new int[array.length];
        sort(array, 0, array.length - 1, buffer, null);
    }
    
    /**
     * Recursive merge sort implementation.
     * 
     * @param array the array to sort
     * @param left left boundary (inclusive)
     * @param right right boundary (inclusive)
     * @param buffer reusable buffer for merging
     * @param metrics metrics collector (can be null)
     */
    private static void sort(int[] array, int left, int right, int[] buffer, MetricsCollector metrics) {
        if (left >= right) {
            return;
        }
        
        // Use insertion sort for small arrays
        if (right - left + 1 <= CUTOFF_THRESHOLD) {
            insertionSort(array, left, right, metrics);
            return;
        }
        
        if (metrics != null) {
            metrics.enterRecursion();
        }
        
        int mid = left + (right - left) / 2;
        
        // Recursively sort both halves
        sort(array, left, mid, buffer, metrics);
        sort(array, mid + 1, right, buffer, metrics);
        
        // Merge the sorted halves
        merge(array, left, mid, right, buffer, metrics);
        
        if (metrics != null) {
            metrics.exitRecursion();
        }
    }
    
    /**
     * Merge two sorted subarrays using linear merge with reusable buffer.
     * 
     * @param array the array containing the subarrays
     * @param left left boundary of first subarray
     * @param mid end of first subarray
     * @param right end of second subarray
     * @param buffer reusable buffer for merging
     * @param metrics metrics collector (can be null)
     */
    private static void merge(int[] array, int left, int mid, int right, int[] buffer, MetricsCollector metrics) {
        // Copy both subarrays to buffer
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        
        // Copy left subarray
        for (int i = 0; i < leftSize; i++) {
            buffer[i] = array[left + i];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2); // Read from array, write to buffer
            }
        }
        
        // Copy right subarray
        for (int i = 0; i < rightSize; i++) {
            buffer[leftSize + i] = array[mid + 1 + i];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2); // Read from array, write to buffer
            }
        }
        
        // Merge the two subarrays back into the original array
        int i = 0, j = leftSize, k = left;
        
        while (i < leftSize && j < leftSize + rightSize) {
            if (metrics != null) {
                metrics.incrementComparisons();
                metrics.incrementArrayAccesses(2); // Read from buffer
            }
            
            if (buffer[i] <= buffer[j]) {
                array[k] = buffer[i];
                i++;
            } else {
                array[k] = buffer[j];
                j++;
            }
            
            if (metrics != null) {
                metrics.incrementArrayAccesses(); // Write to array
            }
            k++;
        }
        
        // Copy remaining elements from left subarray
        while (i < leftSize) {
            array[k] = buffer[i];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2); // Read from buffer, write to array
            }
            i++;
            k++;
        }
        
        // Copy remaining elements from right subarray
        while (j < leftSize + rightSize) {
            array[k] = buffer[j];
            if (metrics != null) {
                metrics.incrementArrayAccesses(2); // Read from buffer, write to array
            }
            j++;
            k++;
        }
    }
    
    /**
     * Insertion sort for small arrays (cutoff optimization).
     * 
     * @param array the array to sort
     * @param left left boundary (inclusive)
     * @param right right boundary (inclusive)
     * @param metrics metrics collector (can be null)
     */
    private static void insertionSort(int[] array, int left, int right, MetricsCollector metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            if (metrics != null) {
                metrics.incrementArrayAccesses(); // Read array[i]
            }
            
            int j = i - 1;
            
            // Move elements greater than key one position ahead
            while (j >= left) {
                if (metrics != null) {
                    metrics.incrementComparisons();
                    metrics.incrementArrayAccesses(); // Read array[j]
                }
                
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    if (metrics != null) {
                        metrics.incrementArrayAccesses(2); // Read array[j], write array[j+1]
                    }
                    j--;
                } else {
                    break;
                }
            }
            
            array[j + 1] = key;
            if (metrics != null) {
                metrics.incrementArrayAccesses(); // Write array[j+1]
            }
        }
    }
}
