package net.elearning.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    // Method to hash password
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString(); // Return the hashed password in hexadecimal form
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to verify if the entered password matches the stored hashed password
    public static boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        // Hash the entered password and compare it with the stored hashed password
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword != null && hashedEnteredPassword.equals(storedHashedPassword);
    }
}
