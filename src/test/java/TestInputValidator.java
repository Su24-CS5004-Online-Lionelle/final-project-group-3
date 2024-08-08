import jobplanner.model.formatters.Formats;
import jobplanner.controller.InputValidator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the InputValidator class.
 */
public class TestInputValidator {

    /** Test valid CSV Extension. */
    @Test
    void testValidCSVExtension() {
        assertTrue(InputValidator.isValidExtension("file.csv", Formats.CSV), 
                "CSV extension should be valid for CSV format");
    }

    /** Test invalid CSV Extension. */
    @Test
    void testInvalidCSVExtension() {
        assertFalse(InputValidator.isValidExtension("file.txt", Formats.CSV), 
                "TXT extension should not be valid for CSV format");
    }

    /** Test valid PRETTY Extension. */
    @Test
    void testValidPrettyExtension() {
        assertTrue(InputValidator.isValidExtension("file.txt", Formats.PRETTY), 
                "TXT extension should be valid for PRETTY format");
    }

    /** Test invalid PRETTY Extension. */
    @Test
    void testInvalidPrettyExtension() {
        assertFalse(InputValidator.isValidExtension("file.csv", Formats.PRETTY), 
                "CSV extension should not be valid for PRETTY format");
    }

    /** Test no extension. */
    @Test
    void testNoExtension() {
        assertFalse(InputValidator.isValidExtension("file", Formats.CSV), 
                "No extension should not be valid for any format");
    }

    /** Test empty extension. */
    @Test
    void testUnsupportedFormat() {
        assertFalse(InputValidator.isValidExtension("file.json", Formats.CSV), 
                "JSON extension should not be valid for CSV format");
        assertFalse(InputValidator.isValidExtension("file.json", Formats.PRETTY), 
                "JSON extension should not be valid for PRETTY format");
    }

    /** Test mixed case extension. */
    @Test
    void testMixedCaseExtension() {
        assertTrue(InputValidator.isValidExtension("file.CsV", Formats.CSV), 
                "Mixed case CSV extension should be valid for CSV format");
        assertTrue(InputValidator.isValidExtension("file.TxT", Formats.PRETTY), 
                "Mixed case TXT extension should be valid for PRETTY format");
    }

    /** Test empty filename. */
    @Test
    void testEmptyFilename() {
        assertFalse(InputValidator.isValidExtension("", Formats.CSV), 
                "Empty filename should not be valid for any format");
    }

    /** Test dot filename. */
    @Test
    void testDotFilename() {
        assertFalse(InputValidator.isValidExtension(".", Formats.CSV), 
                "Dot filename should not be valid for any format");
    }

    /** Test dot-dot filename. */
    @Test
    void testDotDotFilename() {
        assertFalse(InputValidator.isValidExtension("..", Formats.CSV), 
                "Dot-dot filename should not be valid for any format");
    }
}
