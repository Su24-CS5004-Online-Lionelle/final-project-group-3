package jobplanner.controller;

import jobplanner.model.formatters.Formats;

/**
 * Validates the input file based on the desired format.
 */
public final class InputValidator {

    /**
     * Private constructor to prevent instantiation.
     */
    private InputValidator() {
        // empty
    }

    /**
     * Validates the file extension based on the desired format.
     *
     * @param filename the name of the file to check
     * @param format   the expected format
     * @return true if the file extension matches the format, false otherwise
     */
    public static boolean isValidExtension(String filename, Formats format) {
        String fileExtension = getFileExtension(filename);
        switch (format) {
            case CSV:
                return "csv".equalsIgnoreCase(fileExtension);
            case PRETTY:
                return "txt".equalsIgnoreCase(fileExtension);
            default:
                return false;
        }
    }

    /**
     * Extracts the file extension from the filename.
     *
     * @param filename the name of the file
     * @return the file extension
     */
    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
}
