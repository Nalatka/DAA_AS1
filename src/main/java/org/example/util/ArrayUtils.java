package org.example.util;

import java.util.Random;

public class ArrayUtils {
    private static final Random random = new Random();
    
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public static void shuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(array, i, j);
        }
    }
    
    public static void shuffle(int[] array, int start, int end) {
        for (int i = end; i > start; i--) {
            int j = start + random.nextInt(i - start + 1);
            swap(array, i, j);
        }
    }
    
    public static int partition(int[] array, int left, int right, int pivotIndex) {
        swap(array, pivotIndex, right);
        int pivot = array[right];
        int i = left - 1;
        
        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        
        swap(array, i + 1, right);
        return i + 1;
    }
    
    public static int randomPartition(int[] array, int left, int right) {
        int randomIndex = left + random.nextInt(right - left + 1);
        return partition(array, left, right, randomIndex);
    }
    
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static int[] copyArray(int[] array) {
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }
    
    public static void fillRandom(int[] array, int bound) {
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(bound);
        }
    }
    
    public static void fillRandom(int[] array, int min, int max) {
        for (int i = 0; i < array.length; i++) {
            array[i] = min + random.nextInt(max - min + 1);
        }
    }
    
    public static int[] generateRandomArray(int size, int bound) {
        int[] array = new int[size];
        fillRandom(array, bound);
        return array;
    }
    
    public static int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        fillRandom(array, min, max);
        return array;
    }
    
    public static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
    
    public static int[] generateReverseSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i - 1;
        }
        return array;
    }
    
    public static int[] generateDuplicateArray(int size, int value) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = value;
        }
        return array;
    }
}
