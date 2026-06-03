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
        
        // NEW FEATURE: 3b. Read environment profile configuration
        System.out.println("Active Profile: " + loadConfigurationProfile());
        
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

    // NEW FEATURE METHOD: Reads an environment variable set by your Jenkins pipeline
    public static String loadConfigurationProfile() {
        // Looks for an environment variable named 'APP_PROFILE'
        String profile = System.getenv("APP_PROFILE");
        
        if (profile == null || profile.trim().isEmpty()) {
            // Fallback default value if Jenkins hasn't set it yet
            return "DEFAULT-LOCAL";
        }
        return profile.toUpperCase();
    }

    // New logic: Simple rule simulation to verify system readiness
    public static boolean validateEnvironment() {
        String osName = getOperatingSystemName();

        if (osName == null || osName.trim().isEmpty()) {
            System.out.println("Warning: Unable to determine operating system. Proceeding with environment validation by default.");
            return true;
        }

        String os = osName.toLowerCase();

        // Explicitly allow common OS families
        if (os.contains("linux") || os.contains("mac") || os.contains("windows")) {
            return true;
        }

        // For less common or container-specific OS names, log a warning but do not hard-fail
        System.out.println(
            "Warning: Detected unrecognized operating system '" + osName +
            "'. Proceeding, but some features may not be fully supported."
        );
        return true;
    }

    // Helper method to standardise text console logging outputs
    private static void logInfo(String message) {
        System.out.println("[INFO] [" + getCurrentTimestamp() + "] " + message);
    }
}

