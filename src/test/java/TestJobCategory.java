import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jobplanner.model.types.JobCategory;

/**
 * Unit tests for the JobCategory enum.
 */
public class TestJobCategory {

    /** test get tag. */
    @Test
    public void testGetTag() {
        assertEquals("accounting-finance-jobs", JobCategory.ACCOUNTING_FINANCE.getTag());
        assertEquals("it-jobs", JobCategory.IT.getTag());
        assertEquals("sales-jobs", JobCategory.SALES.getTag());
        assertEquals("customer-services-jobs", JobCategory.CUSTOMER_SERVICES.getTag());
    }

    /** test get label. */
    @Test
    public void testGetLabel() {
        assertEquals("Accounting & Finance Jobs", JobCategory.ACCOUNTING_FINANCE.getLabel());
        assertEquals("IT Jobs", JobCategory.IT.getLabel());
        assertEquals("Sales Jobs", JobCategory.SALES.getLabel());
        assertEquals("Customer Services Jobs", JobCategory.CUSTOMER_SERVICES.getLabel());
    }

    /** test from tag. */
    @Test
    public void testFromTag() {
        assertEquals(JobCategory.ACCOUNTING_FINANCE, JobCategory.fromTag("accounting-finance-jobs"));
        assertEquals(JobCategory.IT, JobCategory.fromTag("it-jobs"));
        assertEquals(JobCategory.SALES, JobCategory.fromTag("sales-jobs"));
        assertEquals(JobCategory.CUSTOMER_SERVICES, JobCategory.fromTag("customer-services-jobs"));
        assertEquals(JobCategory.UNKNOWN, JobCategory.fromTag("non-existent-tag"));
    }

    /** test from label. */
    @Test
    public void testFromLabel() {
        assertEquals(JobCategory.ACCOUNTING_FINANCE, JobCategory.fromLabel("Accounting & Finance Jobs"));
        assertEquals(JobCategory.IT, JobCategory.fromLabel("IT Jobs"));
        assertEquals(JobCategory.SALES, JobCategory.fromLabel("Sales Jobs"));
        assertEquals(JobCategory.CUSTOMER_SERVICES, JobCategory.fromLabel("Customer Services Jobs"));
        assertEquals(JobCategory.UNKNOWN, JobCategory.fromLabel("Non-existent Label"));
    }

    /** test from string. */
    @Test
    public void testFromString() {
        assertEquals(JobCategory.ACCOUNTING_FINANCE, JobCategory.fromString("Accounting & Finance Jobs"));
        assertEquals(JobCategory.IT, JobCategory.fromString("IT Jobs"));
        assertEquals(JobCategory.SALES, JobCategory.fromString("Sales Jobs"));
        assertEquals(JobCategory.CUSTOMER_SERVICES, JobCategory.fromString("Customer Services Jobs"));
        assertEquals(JobCategory.UNKNOWN, JobCategory.fromString("Non-existent String"));
    }

    /** test get category labels. */
    @Test
    public void testGetCategoryLabels() {
        List<String> labels = JobCategory.getCategoryLabels();
        assertTrue(labels.contains("Accounting & Finance Jobs"));
        assertTrue(labels.contains("IT Jobs"));
        assertTrue(labels.contains("Sales Jobs"));
        assertTrue(labels.contains("Customer Services Jobs"));
    }

    /** test to string. */
    @Test
    public void testToString() {
        assertEquals("Accounting & Finance Jobs", JobCategory.ACCOUNTING_FINANCE.toString());
        assertEquals("IT Jobs", JobCategory.IT.toString());
        assertEquals("Sales Jobs", JobCategory.SALES.toString());
        assertEquals("Customer Services Jobs", JobCategory.CUSTOMER_SERVICES.toString());
    }
}
