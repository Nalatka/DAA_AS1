package org.example.benchmark;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairBenchmarkTest {
    
    @Test
    public void testClosestPairBenchmarkCreation() {
        ClosestPairBenchmark benchmark = new ClosestPairBenchmark();
        assertNotNull(benchmark);
    }
    
    @Test
    public void testClosestPairBenchmarkSetup() {
        ClosestPairBenchmark benchmark = new ClosestPairBenchmark();
        benchmark.size = 1000;
        benchmark.setup();
        
        assertNotNull(benchmark.points);
        assertEquals(1000, benchmark.points.length);
    }
    
    @Test
    public void testClosestPairBenchmark() {
        ClosestPairBenchmark benchmark = new ClosestPairBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.closestPair());
    }
    
    @Test
    public void testBruteForceClosestPairBenchmark() {
        ClosestPairBenchmark benchmark = new ClosestPairBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.bruteForceClosestPair());
    }
    
    @Test
    public void testClosestPairConsistency() {
        ClosestPairBenchmark benchmark = new ClosestPairBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        var result1 = benchmark.closestPair();
        var result2 = benchmark.bruteForceClosestPair();
        
        assertEquals(result1.distance, result2.distance, 1e-9);
    }
}
