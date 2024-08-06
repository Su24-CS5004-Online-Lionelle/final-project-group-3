import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import jobplanner.model.models.JobPostModel;
import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.model.models.IJobPostModel.Company;
import jobplanner.model.models.IJobPostModel.Location;
import jobplanner.model.models.IJobPostModel.Category;
import jobplanner.model.models.IJobPostModel;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the JobPostModel class.
 */
public class TestJobPostModel {

    /** Test database file path. */
    private static final String TEST_FILEPATH = "data/test_jobpostings.json";
    /** The job post model. */
    private IJobPostModel jobPostModel;

    /**
     * Set up the test environment by creating a test database file with sample
     * data.
     */
    @BeforeEach
    public void setUp() throws IOException {
        List<JobRecord> sampleJobs = List.of(
                new JobRecord("Software Engineer", "Description",
                        new Company("Company A"),
                        new Location("City A", List.of("Country", "State", "County", "City A")),
                        60000, 80000, "full_time", "2023-01-01", "http://example.com", "adref",
                        new Category("it", "IT Jobs"), 40.7128, -74.0060, "1", "true"),
                new JobRecord("Data Scientist", "Description",
                        new Company("Company B"),
                        new Location("City B", List.of("Country", "State", "County", "City B")),
                        70000, 90000, "full_time", "2023-02-01", "http://example.com", "adref",
                        new Category("data", "Data Jobs"), 34.0522, -118.2437, "2", "true"));

        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream os = new FileOutputStream(TEST_FILEPATH)) {
            mapper.writeValue(os, sampleJobs);
        }

        jobPostModel = JobPostModel.getInstance(TEST_FILEPATH);
    }

    /**
     * Clean up the test environment by deleting the test database file.
     */
    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILEPATH));
    }

    /**
     * Test loading job records from a JSON file.
     */
    @Test
    public void testLoadFromJson() {
        List<JobRecord> jobs = jobPostModel.getJobs();
        assertEquals(2, jobs.size());
        assertEquals("Software Engineer", jobs.get(0).title());
        assertEquals("Data Scientist", jobs.get(1).title());
    }

    /**
     * Test handling of invalid JSON file path.
     */
    @Test
    public void testLoadFromInvalidJson() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            JobPostModel.getInstance("invalid_file.json");
        });
        assertEquals("Failed to load model from invalid_file.json", exception.getMessage());
    }

    /**
     * Test retrieving job records.
     */
    @Test
    public void testGetJobs() {
        List<JobRecord> jobs = jobPostModel.getJobs();
        assertNotNull(jobs);
        assertEquals(2, jobs.size());
    }

    /**
     * Test empty job records.
     */
    @Test
    public void testGetEmptyJobs() throws IOException {
        // Create an empty JSON file
        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream os = new FileOutputStream(TEST_FILEPATH)) {
            mapper.writeValue(os, List.of());
        }

        // Reload jobPostModel with empty data
        jobPostModel = JobPostModel.getInstance(TEST_FILEPATH);

        List<JobRecord> jobs = jobPostModel.getJobs();
        assertNotNull(jobs);
        assertEquals(0, jobs.size());
    }
}
