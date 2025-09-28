package org.example.util;

public class ValidationUtils {
    
    public static void validateArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
    }
    
    public static void validateArray(int[] array, String name) {
        if (array == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }
    
    public static void validateIndex(int[] array, int index) {
        validateArray(array);
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for array of length " + array.length);
        }
    }
    
    public static void validateRange(int[] array, int left, int right) {
        validateArray(array);
        if (left < 0 || right >= array.length || left > right) {
            throw new IllegalArgumentException("Invalid range [" + left + ", " + right + "] for array of length " + array.length);
        }
    }
    
    public static void validateKthElement(int[] array, int k) {
        validateArray(array);
        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k=" + k + " must be in range [0, " + (array.length - 1) + "]");
        }
    }
    
    public static void validatePositive(int value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive, got " + value);
        }
    }
    
    public static void validateNonNegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must be non-negative, got " + value);
        }
    }
    
    public static void validateNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }
}
