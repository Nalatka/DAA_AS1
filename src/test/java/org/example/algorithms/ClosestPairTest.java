package org.example.algorithms;

import org.example.metrics.MetricsCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class ClosestPairTest {
    private Random random;
    
    @BeforeEach
    public void setUp() {
        random = new Random(42);
    }
    
    @Test
    public void testTwoPoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(0, result.p1.x, 1e-9);
        assertEquals(0, result.p1.y, 1e-9);
        assertEquals(1, result.p2.x, 1e-9);
        assertEquals(1, result.p2.y, 1e-9);
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
    }
    
    @Test
    public void testThreePoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(2, 0)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    public void testFourPoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(0, 1),
            new ClosestPair.Point(1, 1)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    public void testHorizontalLine() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 0),
            new ClosestPair.Point(2, 0),
            new ClosestPair.Point(3, 0)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    public void testVerticalLine() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(0, 1),
            new ClosestPair.Point(0, 2),
            new ClosestPair.Point(0, 3)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    public void testDiagonalLine() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(2, 2),
            new ClosestPair.Point(3, 3)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
    }
    
    @Test
    public void testRandomPoints() {
        ClosestPair.Point[] points = generateRandomPoints(100);
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertNotNull(result);
        assertTrue(result.distance > 0);
    }
    
    @Test
    public void testWithMetrics() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1),
            new ClosestPair.Point(2, 0),
            new ClosestPair.Point(1, 0)
        };
        
        MetricsCollector metrics = new MetricsCollector();
        ClosestPair.Pair result = ClosestPair.findClosestPair(points, metrics);
        
        assertNotNull(result);
        assertTrue(metrics.getPerformanceMetrics().getComparisons() > 0);
        assertTrue(metrics.getPerformanceMetrics().getArrayAccesses() > 0);
        assertTrue(metrics.getPerformanceMetrics().getExecutionTimeNanos() > 0);
        assertTrue(metrics.getPerformanceMetrics().getAllocations() > 0);
    }
    
    @Test
    public void testNullArray() {
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.findClosestPair(null));
    }
    
    @Test
    public void testEmptyArray() {
        ClosestPair.Point[] points = {};
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.findClosestPair(points));
    }
    
    @Test
    public void testSinglePoint() {
        ClosestPair.Point[] points = {new ClosestPair.Point(0, 0)};
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.findClosestPair(points));
    }
    
    @Test
    public void testDuplicatePoints() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(0, 0),
            new ClosestPair.Point(1, 1)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(0.0, result.distance, 1e-9);
    }
    
    @Test
    public void testLargeRandomArray() {
        ClosestPair.Point[] points = generateRandomPoints(1000);
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertNotNull(result);
        assertTrue(result.distance > 0);
    }
    
    @Test
    public void testPerformanceComparison() {
        ClosestPair.Point[] points = generateRandomPoints(1000);
        
        long startTime = System.nanoTime();
        ClosestPair.Pair result1 = ClosestPair.findClosestPair(points);
        long algorithmTime = System.nanoTime() - startTime;
        
        MetricsCollector metrics = new MetricsCollector();
        startTime = System.nanoTime();
        ClosestPair.Pair result2 = ClosestPair.findClosestPair(points, metrics);
        long algorithmWithMetricsTime = System.nanoTime() - startTime;
        
        assertEquals(result1.distance, result2.distance, 1e-9);
        assertTrue(algorithmWithMetricsTime < algorithmTime * 2);
    }
    
    @Test
    public void testRecursionDepth() {
        ClosestPair.Point[] points = generateRandomPoints(1000);
        MetricsCollector metrics = new MetricsCollector();
        
        ClosestPair.findClosestPair(points, metrics);
        
        int maxDepth = metrics.getDepthTracker().getMaxDepth();
        assertTrue(maxDepth <= 2 * (int) Math.ceil(Math.log(points.length) / Math.log(2)) + 2,
                "Expected depth <= 2*log2(n)+2, got " + maxDepth);
    }
    
    @Test
    public void testMultipleRuns() {
        for (int i = 0; i < 10; i++) {
            ClosestPair.Point[] points = generateRandomPoints(100);
            ClosestPair.Pair result = ClosestPair.findClosestPair(points);
            
            assertNotNull(result);
            assertTrue(result.distance > 0);
        }
    }
    
    @Test
    public void testGridPoints() {
        ClosestPair.Point[] points = new ClosestPair.Point[100];
        int index = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                points[index++] = new ClosestPair.Point(i, j);
            }
        }
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertEquals(1.0, result.distance, 1e-9);
    }
    
    @Test
    public void testExtremeValues() {
        ClosestPair.Point[] points = {
            new ClosestPair.Point(Double.MAX_VALUE, Double.MAX_VALUE),
            new ClosestPair.Point(Double.MIN_VALUE, Double.MIN_VALUE),
            new ClosestPair.Point(0, 0)
        };
        
        ClosestPair.Pair result = ClosestPair.findClosestPair(points);
        
        assertNotNull(result);
        assertTrue(result.distance > 0);
    }
    
    @Test
    public void testPointDistance() {
        ClosestPair.Point p1 = new ClosestPair.Point(0, 0);
        ClosestPair.Point p2 = new ClosestPair.Point(3, 4);
        
        assertEquals(5.0, p1.distanceTo(p2), 1e-9);
        assertEquals(25.0, p1.distanceSquaredTo(p2), 1e-9);
    }
    
    @Test
    public void testPairToString() {
        ClosestPair.Point p1 = new ClosestPair.Point(0, 0);
        ClosestPair.Point p2 = new ClosestPair.Point(1, 1);
        ClosestPair.Pair pair = new ClosestPair.Pair(p1, p2);
        
        String str = pair.toString();
        assertTrue(str.contains("(0.0, 0.0)"));
        assertTrue(str.contains("(1.0, 1.0)"));
        assertTrue(str.contains("distance="));
    }
    
    private ClosestPair.Point[] generateRandomPoints(int count) {
        ClosestPair.Point[] points = new ClosestPair.Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new ClosestPair.Point(
                random.nextDouble() * 1000,
                random.nextDouble() * 1000
            );
        }
        return points;
    }
}
