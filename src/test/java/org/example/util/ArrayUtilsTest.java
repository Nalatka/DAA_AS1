package org.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilsTest {
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testSwap() {
        int[] array = {1, 2, 3, 4, 5};
        ArrayUtils.swap(array, 0, 4);
        assertArrayEquals(new int[]{5, 2, 3, 4, 1}, array);
    }
    
    @Test
    public void testShuffle() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] original = array.clone();
        ArrayUtils.shuffle(array);
        
        assertFalse(ArrayUtils.isSorted(array));
        assertTrue(array.length == original.length);
    }
    
    @Test
    public void testShuffleRange() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayUtils.shuffle(array, 2, 7);
        
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(10, array[9]);
    }
    
    @Test
    public void testPartition() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = ArrayUtils.partition(array, 0, 7, 2);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
        
        for (int i = 0; i < pivotIndex; i++) {
            assertTrue(array[i] <= array[pivotIndex]);
        }
        for (int i = pivotIndex + 1; i < array.length; i++) {
            assertTrue(array[i] >= array[pivotIndex]);
        }
    }
    
    @Test
    public void testRandomPartition() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int pivotIndex = ArrayUtils.randomPartition(array, 0, 7);
        
        assertTrue(pivotIndex >= 0 && pivotIndex < array.length);
    }
    
    @Test
    public void testIsSorted() {
        assertTrue(ArrayUtils.isSorted(new int[]{1, 2, 3, 4, 5}));
        assertTrue(ArrayUtils.isSorted(new int[]{1}));
        assertTrue(ArrayUtils.isSorted(new int[]{}));
        assertFalse(ArrayUtils.isSorted(new int[]{1, 3, 2, 4, 5}));
    }
    
    @Test
    public void testCopyArray() {
        int[] original = {1, 2, 3, 4, 5};
        int[] copy = ArrayUtils.copyArray(original);
        
        assertArrayEquals(original, copy);
        assertNotSame(original, copy);
    }
    
    @Test
    public void testFillRandom() {
        int[] array = new int[10];
        ArrayUtils.fillRandom(array, 100);
        
        for (int value : array) {
            assertTrue(value >= 0 && value < 100);
        }
    }
    
    @Test
    public void testFillRandomRange() {
        int[] array = new int[10];
        ArrayUtils.fillRandom(array, 10, 20);
        
        for (int value : array) {
            assertTrue(value >= 10 && value <= 20);
        }
    }
    
    @Test
    public void testGenerateRandomArray() {
        int[] array = ArrayUtils.generateRandomArray(100, 50);
        
        assertEquals(100, array.length);
        for (int value : array) {
            assertTrue(value >= 0 && value < 50);
        }
    }
    
    @Test
    public void testGenerateRandomArrayRange() {
        int[] array = ArrayUtils.generateRandomArray(100, 10, 20);
        
        assertEquals(100, array.length);
        for (int value : array) {
            assertTrue(value >= 10 && value <= 20);
        }
    }
    
    @Test
    public void testGenerateSortedArray() {
        int[] array = ArrayUtils.generateSortedArray(10);
        
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, array);
    }
    
    @Test
    public void testGenerateReverseSortedArray() {
        int[] array = ArrayUtils.generateReverseSortedArray(5);
        
        assertArrayEquals(new int[]{4, 3, 2, 1, 0}, array);
    }
    
    @Test
    public void testGenerateDuplicateArray() {
        int[] array = ArrayUtils.generateDuplicateArray(5, 42);
        
        assertArrayEquals(new int[]{42, 42, 42, 42, 42}, array);
    }
}
