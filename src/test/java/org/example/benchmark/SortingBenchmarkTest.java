package org.example.benchmark;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SortingBenchmarkTest {
    
    @Test
    public void testSortingBenchmarkCreation() {
        SortingBenchmark benchmark = new SortingBenchmark();
        assertNotNull(benchmark);
    }
    
    @Test
    public void testSortingBenchmarkSetup() {
        SortingBenchmark benchmark = new SortingBenchmark();
        benchmark.size = 1000;
        benchmark.setup();
        
        assertNotNull(benchmark.array);
        assertEquals(1000, benchmark.array.length);
    }
    
    @Test
    public void testMergeSortBenchmark() {
        SortingBenchmark benchmark = new SortingBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.mergeSort());
    }
    
    @Test
    public void testQuickSortBenchmark() {
        SortingBenchmark benchmark = new SortingBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.quickSort());
    }
    
    @Test
    public void testJavaSortBenchmark() {
        SortingBenchmark benchmark = new SortingBenchmark();
        benchmark.size = 100;
        benchmark.setup();
        
        assertDoesNotThrow(() -> benchmark.javaSort());
    }
}
