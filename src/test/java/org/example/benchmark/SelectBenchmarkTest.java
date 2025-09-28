package org.example.benchmark;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SelectBenchmarkTest {
    
    @Test
    public void testSelectBenchmarkCreation() {
        SelectBenchmark benchmark = new SelectBenchmark();
        assertNotNull(benchmark);
    }
    
    @Test
    public void testSelectBenchmarkSetup() {
        SelectBenchmark benchmark = new SelectBenchmark();
        benchmark.size = 1000;
        benchmark.setup();
        
        assertNotNull(benchmark.array);
        assertEquals(1000, benchmark.array.length);
        assertTrue(benchmark.k >= 0 && benchmark.k < benchmark.array.length);
    }
    
    @Test
    public void testDeterministicSelectBenchmark() {
        SelectBenchmark benchmark = new SelectBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.deterministicSelect());
    }
    
    @Test
    public void testJavaSortSelectBenchmark() {
        SelectBenchmark benchmark = new SelectBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.javaSortSelect());
    }
    
    @Test
    public void testQuickSelectBenchmark() {
        SelectBenchmark benchmark = new SelectBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.quickSelect());
    }
    
    @Test
    public void testQuickSelectConsistency() {
        SelectBenchmark benchmark = new SelectBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        int result1 = benchmark.quickSelect();
        int result2 = benchmark.javaSortSelect();
        
        assertEquals(result1, result2);
    }
}
