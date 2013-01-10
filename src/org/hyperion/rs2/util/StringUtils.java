package org.hyperion.rs2.util;

public class StringUtils {

    /**
     * Check that the given string is convertable to an int value.
     * 
     * @param s the string
     * @return is convertable
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
