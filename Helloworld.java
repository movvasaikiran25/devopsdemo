package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    
    public static void main(String[] args) {
        // 1. Simulating application startup logs
        logInfo("Application starting up...");
        
        // 2. Displaying the greeting
        System.out.println(getGreeting());
        
        // 3. Displaying dynamic environment runtime information
        System.out.println("Build Run Time: " + getCurrentTimestamp());
        System.out.println("Operating System: " + getOperatingSystemName());
        
        // 4. Standard validation check
        if (validateEnvironment()) {
            logInfo("Application completed successfully.");
        } else {
            System.err.println("[ERROR] System configuration validation failed.");
            System.exit(1); // Forces Jenkins to register a build failure if triggered
        }
    }

    // Existing greeting method
    public static String getGreeting() {
        return "Hello World from Java!";
    }

    // New logic: Returns the current system time (Great for checking CI execution time)
    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    // New logic: Automatically detects the OS (Will output 'Linux' on Ubuntu)
    public static String getOperatingSystemName() {
        return System.getProperty("os.name");
    }

    // New logic: Simple rule simulation to verify system readiness
    public static boolean validateEnvironment() {
        String os = getOperatingSystemName().toLowerCase(java.util.Locale.ROOT);
        // Returns true if running on a standard OS environment
        return os.contains("linux") || os.contains("mac") || os.contains("windows");
    }

    // Helper method to standardise text console logging outputs
    private static void logInfo(String message) {
        System.out.println("[INFO] [" + getCurrentTimestamp() + "] " + message);
    }
}

