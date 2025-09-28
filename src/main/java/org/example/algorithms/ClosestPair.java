package org.example.algorithms;

import org.example.metrics.MetricsCollector;
import org.example.util.ValidationUtils;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    
    public static class Point {
        public final double x, y;
        
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        public double distanceTo(Point other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
        
        public double distanceSquaredTo(Point other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return dx * dx + dy * dy;
        }
        
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    
    public static class Pair {
        public final Point p1, p2;
        public final double distance;
        
        public Pair(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = p1.distanceTo(p2);
        }
        
        @Override
        public String toString() {
            return "Pair{" + p1 + ", " + p2 + ", distance=" + distance + "}";
        }
    }
    
    public static Pair findClosestPair(Point[] points, MetricsCollector metrics) {
        ValidationUtils.validateArray(points);
        if (points.length < 2) {
            throw new IllegalArgumentException("At least 2 points required");
        }
        
        metrics.start();
        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(p -> p.x));
        
        Point[] pointsByY = points.clone();
        Arrays.sort(pointsByY, Comparator.comparingDouble(p -> p.y));
        
        if (metrics != null) {
            metrics.incrementAllocations(points.length * 2);
            metrics.incrementArrayAccesses(points.length * 4);
        }
        
        Pair result = findClosestPair(pointsByX, pointsByY, 0, pointsByX.length - 1, metrics);
        metrics.stop();
        return result;
    }
    
    public static Pair findClosestPair(Point[] points) {
        return findClosestPair(points, null);
    }
    
    private static Pair findClosestPair(Point[] pointsByX, Point[] pointsByY, int left, int right, MetricsCollector metrics) {
        int n = right - left + 1;
        
        if (n <= 3) {
            return bruteForceClosestPair(pointsByX, left, right, metrics);
        }
        
        if (metrics != null) {
            metrics.enterRecursion();
        }
        
        int mid = left + (right - left) / 2;
        Point midPoint = pointsByX[mid];
        
        Point[] leftPointsByY = new Point[mid - left + 1];
        Point[] rightPointsByY = new Point[right - mid];
        
        int leftIdx = 0, rightIdx = 0;
        for (Point point : pointsByY) {
            if (metrics != null) {
                metrics.incrementComparisons();
                metrics.incrementArrayAccesses();
            }
            
            if (point.x <= midPoint.x) {
                leftPointsByY[leftIdx++] = point;
            } else {
                rightPointsByY[rightIdx++] = point;
            }
        }
        
        if (metrics != null) {
            metrics.incrementAllocations(n);
            metrics.incrementArrayAccesses(n);
        }
        
        Pair leftPair = findClosestPair(pointsByX, leftPointsByY, left, mid, metrics);
        Pair rightPair = findClosestPair(pointsByX, rightPointsByY, mid + 1, right, metrics);
        
        Pair minPair = leftPair.distance < rightPair.distance ? leftPair : rightPair;
        
        Point[] strip = new Point[n];
        int stripSize = 0;
        
        for (Point point : pointsByY) {
            if (metrics != null) {
                metrics.incrementComparisons();
                metrics.incrementArrayAccesses();
            }
            
            if (Math.abs(point.x - midPoint.x) < minPair.distance) {
                strip[stripSize++] = point;
            }
        }
        
        if (metrics != null) {
            metrics.incrementAllocations(n);
            metrics.incrementArrayAccesses(n);
        }
        
        Pair stripPair = findClosestInStrip(strip, stripSize, minPair.distance, metrics);
        
        if (metrics != null) {
            metrics.exitRecursion();
        }
        
        return minPair.distance < stripPair.distance ? minPair : stripPair;
    }
    
    private static Pair bruteForceClosestPair(Point[] points, int left, int right, MetricsCollector metrics) {
        double minDistance = Double.POSITIVE_INFINITY;
        Point p1 = null, p2 = null;
        
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                if (metrics != null) {
                    metrics.incrementComparisons();
                    metrics.incrementArrayAccesses(4);
                }
                
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    p1 = points[i];
                    p2 = points[j];
                }
            }
        }
        
        return new Pair(p1, p2);
    }
    
    private static Pair findClosestInStrip(Point[] strip, int stripSize, double minDistance, MetricsCollector metrics) {
        double minDist = minDistance;
        Point p1 = null, p2 = null;
        
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y - strip[i].y) < minDist; j++) {
                if (metrics != null) {
                    metrics.incrementComparisons();
                    metrics.incrementArrayAccesses(4);
                }
                
                double distance = strip[i].distanceTo(strip[j]);
                if (distance < minDist) {
                    minDist = distance;
                    p1 = strip[i];
                    p2 = strip[j];
                }
            }
        }
        
        return new Pair(p1, p2);
    }
}
