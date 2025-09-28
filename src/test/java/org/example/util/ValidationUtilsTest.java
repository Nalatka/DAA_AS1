package org.example.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilsTest {
    
    @Test
    public void testValidateArray() {
        assertDoesNotThrow(() -> ValidationUtils.validateArray(new int[]{1, 2, 3}));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateArray(null));
    }
    
    @Test
    public void testValidateArrayWithName() {
        assertDoesNotThrow(() -> ValidationUtils.validateArray(new int[]{1, 2, 3}, "test"));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateArray(null, "test"));
    }
    
    @Test
    public void testValidateIndex() {
        int[] array = {1, 2, 3, 4, 5};
        
        assertDoesNotThrow(() -> ValidationUtils.validateIndex(array, 0));
        assertDoesNotThrow(() -> ValidationUtils.validateIndex(array, 4));
        
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateIndex(null, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> ValidationUtils.validateIndex(array, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> ValidationUtils.validateIndex(array, 5));
    }
    
    @Test
    public void testValidateRange() {
        int[] array = {1, 2, 3, 4, 5};
        
        assertDoesNotThrow(() -> ValidationUtils.validateRange(array, 0, 4));
        assertDoesNotThrow(() -> ValidationUtils.validateRange(array, 2, 3));
        
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateRange(null, 0, 4));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateRange(array, -1, 4));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateRange(array, 0, 5));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateRange(array, 3, 2));
    }
    
    @Test
    public void testValidateKthElement() {
        int[] array = {1, 2, 3, 4, 5};
        
        assertDoesNotThrow(() -> ValidationUtils.validateKthElement(array, 0));
        assertDoesNotThrow(() -> ValidationUtils.validateKthElement(array, 4));
        
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateKthElement(null, 0));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateKthElement(array, -1));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateKthElement(array, 5));
    }
    
    @Test
    public void testValidatePositive() {
        assertDoesNotThrow(() -> ValidationUtils.validatePositive(1, "test"));
        assertDoesNotThrow(() -> ValidationUtils.validatePositive(100, "test"));
        
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validatePositive(0, "test"));
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validatePositive(-1, "test"));
    }
    
    @Test
    public void testValidateNonNegative() {
        assertDoesNotThrow(() -> ValidationUtils.validateNonNegative(0, "test"));
        assertDoesNotThrow(() -> ValidationUtils.validateNonNegative(1, "test"));
        assertDoesNotThrow(() -> ValidationUtils.validateNonNegative(100, "test"));
        
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateNonNegative(-1, "test"));
    }
    
    @Test
    public void testValidateNotNull() {
        assertDoesNotThrow(() -> ValidationUtils.validateNotNull("test", "test"));
        assertDoesNotThrow(() -> ValidationUtils.validateNotNull(new Object(), "test"));
        
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateNotNull(null, "test"));
    }
}
