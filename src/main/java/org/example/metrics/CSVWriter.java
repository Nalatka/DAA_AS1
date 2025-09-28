package org.example.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {
    private final String filename;
    private final List<String> headers;
    private final List<List<String>> data;
    
    public CSVWriter(String filename) {
        this.filename = filename;
        this.headers = new ArrayList<>();
        this.data = new ArrayList<>();
    }
    
    public void setHeaders(String... headers) {
        this.headers.clear();
        for (String header : headers) {
            this.headers.add(header);
        }
    }
    
    /**
     * Add a row of data.
     */
    public void addRow(String... values) {
        List<String> row = new ArrayList<>();
        for (String value : values) {
            row.add(value);
        }
        data.add(row);
    }
    
    /**
     * Add performance metrics as a row.
     */
    public void addMetricsRow(String algorithm, int arraySize, PerformanceMetrics metrics, 
                             RecursionDepthTracker depthTracker) {
        addRow(
            algorithm,
            String.valueOf(arraySize),
            String.valueOf(metrics.getExecutionTimeMillis()),
            String.valueOf(metrics.getComparisons()),
            String.valueOf(metrics.getArrayAccesses()),
            String.valueOf(metrics.getAllocations()),
            String.valueOf(metrics.getSwaps()),
            String.valueOf(depthTracker.getMaxDepth())
        );
    }
    
    /**
     * Write all data to CSV file.
     */
    public void writeToFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write headers
            if (!headers.isEmpty()) {
                writer.println(String.join(",", headers));
            }
            
            // Write data rows
            for (List<String> row : data) {
                writer.println(String.join(",", row));
            }
        }
    }
    
    /**
     * Write data to CSV file with timestamp in filename.
     */
    public void writeToFileWithTimestamp() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filenameWithTimestamp = filename.replace(".csv", "_" + timestamp + ".csv");
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filenameWithTimestamp))) {
            // Write headers
            if (!headers.isEmpty()) {
                writer.println(String.join(",", headers));
            }
            
            // Write data rows
            for (List<String> row : data) {
                writer.println(String.join(",", row));
            }
        }
    }
    
    /**
     * Clear all data.
     */
    public void clear() {
        headers.clear();
        data.clear();
    }
    
    /**
     * Get number of data rows.
     */
    public int getRowCount() {
        return data.size();
    }
}
