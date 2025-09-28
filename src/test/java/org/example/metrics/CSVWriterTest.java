package org.example.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVWriterTest {
    private CSVWriter writer;
    private final String testFilename = "test_metrics.csv";
    
    @BeforeEach
    public void setUp() {
        writer = new CSVWriter(testFilename);
    }
    
    @Test
    public void testSetHeaders() {
        writer.setHeaders("Algorithm", "Size", "Time");
        writer.addRow("MergeSort", "100", "1.5");
        
        assertEquals(1, writer.getRowCount());
    }
    
    @Test
    public void testAddRow() {
        writer.setHeaders("A", "B", "C");
        writer.addRow("1", "2", "3");
        writer.addRow("4", "5", "6");
        
        assertEquals(2, writer.getRowCount());
    }
    
    @Test
    public void testAddMetricsRow() {
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.incrementComparisons(10);
        metrics.incrementArrayAccesses(20);
        metrics.incrementAllocations(5);
        metrics.incrementSwaps(3);
        
        RecursionDepthTracker depthTracker = new RecursionDepthTracker();
        depthTracker.enterRecursion();
        depthTracker.enterRecursion();
        
        writer.addMetricsRow("QuickSort", 100, metrics, depthTracker);
        
        assertEquals(1, writer.getRowCount());
    }
    
    @Test
    public void testWriteToFile() throws IOException {
        writer.setHeaders("Algorithm", "Size", "Time");
        writer.addRow("MergeSort", "100", "1.5");
        writer.addRow("QuickSort", "200", "2.0");
        
        writer.writeToFile();
        
        assertTrue(Files.exists(Paths.get(testFilename)));
        
        List<String> lines = Files.readAllLines(Paths.get(testFilename));
        assertEquals(3, lines.size()); // Headers + 2 data rows
        assertEquals("Algorithm,Size,Time", lines.get(0));
        assertEquals("MergeSort,100,1.5", lines.get(1));
        assertEquals("QuickSort,200,2.0", lines.get(2));
        
        // Clean up
        Files.deleteIfExists(Paths.get(testFilename));
    }
    
    @Test
    public void testClear() {
        writer.setHeaders("A", "B");
        writer.addRow("1", "2");
        writer.addRow("3", "4");
        
        assertEquals(2, writer.getRowCount());
        
        writer.clear();
        
        assertEquals(0, writer.getRowCount());
    }
}
