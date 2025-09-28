package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Main class.
 */
public class AppTest 
{
    @Test
    public void testMain()
    {
        // Basic test to ensure Main class can be instantiated
        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });
    }
}
