package org.example.benchmark;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComprehensiveBenchmarkTest {
    
    @Test
    public void testComprehensiveBenchmarkCreation() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        assertNotNull(benchmark);
    }
    
    @Test
    public void testComprehensiveBenchmarkSetup() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 1000;
        benchmark.setup();
        
        assertNotNull(benchmark.array);
        assertEquals(1000, benchmark.array.length);
        assertNotNull(benchmark.points);
        assertEquals(1000, benchmark.points.length);
        assertTrue(benchmark.k >= 0 && benchmark.k < benchmark.array.length);
    }
    
    @Test
    public void testMergeSortBenchmark() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.mergeSort());
    }
    
    @Test
    public void testQuickSortBenchmark() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.quickSort());
    }
    
    @Test
    public void testJavaSortBenchmark() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.javaSort());
    }
    
    @Test
    public void testDeterministicSelectBenchmark() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.deterministicSelect());
    }
    
    @Test
    public void testJavaSortSelectBenchmark() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.javaSortSelect());
    }
    
    @Test
    public void testClosestPairBenchmark() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.closestPair());
    }
    
    @Test
    public void testBruteForceClosestPairBenchmark() {
        ComprehensiveBenchmark benchmark = new ComprehensiveBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.bruteForceClosestPair());
    }
}
