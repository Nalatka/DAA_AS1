package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest {
    
    @Test
    public void testMainWithHelp() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Main.main(new String[]{"--help"});
        
        String output = outContent.toString();
        assertTrue(output.contains("Divide-and-Conquer Algorithms Performance Tool"));
        assertTrue(output.contains("Usage:"));
        assertTrue(output.contains("Algorithms:"));
        assertTrue(output.contains("mergesort"));
        assertTrue(output.contains("quicksort"));
        assertTrue(output.contains("select"));
        assertTrue(output.contains("closest"));
        assertTrue(output.contains("all"));
        
        System.setOut(System.out);
    }
    
    @Test
    public void testMainWithNoArgs() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Main.main(new String[]{});
        
        String output = outContent.toString();
        assertTrue(output.contains("Divide-and-Conquer Algorithms Performance Tool"));
        
        System.setOut(System.out);
    }
    
    @Test
    public void testMainWithInvalidAlgorithm() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        
        Main.main(new String[]{"invalid"});
        
        String output = errContent.toString();
        assertTrue(output.contains("Error:"));
        assertTrue(output.contains("Unknown algorithm"));
        
        System.setErr(System.err);
    }
    
    @Test
    public void testMainWithInvalidSize() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        
        Main.main(new String[]{"mergesort", "invalid"});
        
        String output = errContent.toString();
        assertTrue(output.contains("Error:"));
        
        System.setErr(System.err);
    }
}
