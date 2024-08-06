import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import jobplanner.model.models.SavedJobModel;
import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.model.models.IJobPostModel.Company;
import jobplanner.model.models.IJobPostModel.Location;
import jobplanner.model.models.IJobPostModel.Category;
import jobplanner.model.models.ISavedJobModel;

import static org.junit.jupiter.api.Assertions.*;

public class TestSavedJobModel {

    private static final String TEST_FILEPATH = "data/test_savedjobs.json";
    private ISavedJobModel savedJobModel;


    /**
     * Set up the test environment by creating a test database file with sample data.
     *
     * @throws IOException if an I/O error occurs
     */
    @BeforeEach
    public void setUp() throws IOException {
        // Create a test database file with sample data
        List<JobRecord> sampleJobs = List.of(
                new JobRecord("Software Engineer", "Description",
                        new Company("Company A"), new Location("City A", List.of("Country", "State", "County", "City A")),
                        60000, 80000, "full_time", "2023-01-01", "http://example.com", "adref",
                        new Category("it", "IT Jobs"), 40.7128, -74.0060, "1", "true"),
                new JobRecord("Data Scientist", "Description",
                        new Company("Company B"), new Location("City B", List.of("Country", "State", "County", "City B")),
                        70000, 90000, "full_time", "2023-02-01", "http://example.com", "adref",
                        new Category("data", "Data Jobs"), 34.0522, -118.2437, "2", "true")
        );

        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream os = new FileOutputStream(TEST_FILEPATH)) {
            mapper.writeValue(os, sampleJobs);
        }

        // Load the saved jobs from the test file
        savedJobModel = SavedJobModel.loadFromJson(TEST_FILEPATH);
    }

    /**
     * Clean up the test environment by deleting the test database file.
     *
     * @throws IOException if an I/O error occurs
     */
    @AfterEach
    public void tearDown() throws IOException {
        // Clean up the test database file
        Files.deleteIfExists(Paths.get(TEST_FILEPATH));
    }

    /**
     * Test the loading of job records from a JSON file.
     */
    @Test
    public void testLoadFromJson() {
        List<JobRecord> jobs = savedJobModel.getSavedJobs();
        assertEquals(2, jobs.size());
        assertEquals("Software Engineer", jobs.get(0).title());
        assertEquals("Data Scientist", jobs.get(1).title());
    }


    /**
     * Test adding a job record to the saved jobs list.
     */
    @Test
    public void testAddSavedJob() {
        JobRecord newJob = new JobRecord("Product Manager", "Description",
                new Company("Company C"), new Location("City C", List.of("Country", "State", "County", "City C")),
                80000, 100000, "Full-time", "2023-03-01", "http://example.com", "adref",
                new Category("pm", "Product Management Jobs"), 37.7749, -122.4194, "3", "true");

        savedJobModel.addSavedJob(newJob);
        List<JobRecord> jobs = savedJobModel.getSavedJobs();
        assertEquals(3, jobs.size());
        assertEquals("Product Manager", jobs.get(2).title());
    }

    /**
     * Test removing a job record from the saved jobs list.
     */
    @Test
    public void testRemoveSavedJob() {
        // Select job to remove
        JobRecord jobToRemove = savedJobModel.getSavedJobs().get(0);

        // Remove job
        savedJobModel.removeSavedJob(jobToRemove);

        // Get updated list of jobs and check if size changed
        List<JobRecord> jobs = savedJobModel.getSavedJobs();
        assertEquals(1, jobs.size());
        assertEquals("Data Scientist", jobs.get(0).title());
    }

    /**
     * Test clearing a job record from the saved jobs list.
     */
    @Test
    public void testClearSavedJobs() {
        savedJobModel.clearSavedJobs();
        assertEquals(0, savedJobModel.getSavedJobs().size());
    }

    /**
     * Test setting a list of job records to the saved jobs list.
     */
    @Test
    public void testSetSavedJobs() {
        // Count initial jobs in list
        List<JobRecord> initialJobs = savedJobModel.getSavedJobs();
        assertEquals(2, initialJobs.size());

        // Create a new job record
        JobRecord newJob = new JobRecord("Product Manager", "Description",
                new Company("Company C"), new Location("City C", List.of("Country", "State", "County", "City C")),
                80000, 100000, "Full-time", "2023-03-01", "http://example.com", "adref",
                new Category("pm", "Product Management Jobs"), 37.7749, -122.4194, "3", "true");

        // Set SavedJobsModel list with new job record
        savedJobModel.setSavedJobs(List.of(newJob));

        // Check if the saved list of jobs successfully gets updated
        List<JobRecord> jobs = savedJobModel.getSavedJobs();
        assertEquals(1, jobs.size());
        assertEquals("Product Manager", jobs.get(0).title());
    }

    /**
     * Test counting the number of job records in the saved jobs list.
     */
    @Test
    public void testCount() {

        // Count jobs in current list
        assertEquals(2, savedJobModel.count());

        // Add new job record to the list
        JobRecord newJob = new JobRecord("Product Manager", "Description",
                new Company("Company C"), new Location("City C", List.of("Country", "State", "County", "City C")),
                80000, 100000, "Full-time", "2023-03-01", "http://example.com", "adref",
                new Category("pm", "Product Management Jobs"), 37.7749, -122.4194, "3", "true");

        savedJobModel.addSavedJob(newJob);

        // Count should change to 3
        assertEquals(3, savedJobModel.count());
    }

    /**
     * Test setting and getting the last saved date.
     */
    @Test
    public void testLastSaved() {
        // Initially lastSaved should be null
        assertNull(savedJobModel.getLastSaved());

        // Update last saved date
        savedJobModel.setLastSaved(LocalDate.now());

        // Check if lastSaved is updated (not null)
        assertNotNull(savedJobModel.getLastSaved());
    }

    /**
     * Test loading job records from an invalid JSON file.
     */
    @Test
    public void testLoadFromInvalidJson() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            SavedJobModel.loadFromJson("invalid_file.json");
        });
        assertEquals("Failed to load model from invalid_file.json", exception.getMessage());
    }
}