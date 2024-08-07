import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.model.models.IJobPostModel.Company;
import jobplanner.model.models.IJobPostModel.Location;
import jobplanner.model.models.IJobPostModel.Category;

import static org.junit.jupiter.api.Assertions.*;

import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;

public class TestDataFormatter {
    private static final String TEST_FILEPATH = "data/test_jobs.json";
    private List<JobRecord> sampleJobs;

    @BeforeEach
    public void setUp() throws Exception {
        sampleJobs = List.of(
                new JobRecord("Software Engineer", "Job Description",
                        new Company("MockCompany"),
                        new Location("Boston", List.of("USA", "MA", "Suffolk", "Boston")),
                        50000, 100000, "Full-time", "2024-08-07", "http://example.com", "adref",
                        new Category("it", "IT Jobs"), 42.3601, -71.0589, "1", "true")
        );

        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream os = Files.newOutputStream(Paths.get(TEST_FILEPATH))) {
            mapper.writeValue(os, sampleJobs);
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(TEST_FILEPATH));
    }

    @Test
    public void testWritePretty() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        DataFormatter.write(sampleJobs, Formats.PRETTY, out);
        String prettyOutput = out.toString();
        String expectedPrettyOutput = "Title: Software Engineer\n" +
                "   Description: Job Description\n" +
                "   Company: MockCompany\n" +
                "   Location: Boston, MA\n" +
                "   Salary Range: 50,000 - 100,000\n" +
                "   Role Type: Full-time\n" +
                "   Date Posted: 2024-08-07\n\n";

        assertEquals(expectedPrettyOutput, prettyOutput);
    }
    @Test
    public void testWriteJson() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        DataFormatter.write(sampleJobs, Formats.JSON, out);
        String jsonOutput = out.toString();
        assertTrue(jsonOutput.contains("\"title\" : \"Software Engineer\""));
        assertTrue(jsonOutput.contains("\"description\" : \"Job Description\""));
        assertTrue(jsonOutput.contains("\"display_name\" : \"Boston\""));
        assertTrue(jsonOutput.contains("\"display_name\" : \"MockCompany\""));
        assertTrue(jsonOutput.contains("\"salary_min\" : 50000.0"));
        assertTrue(jsonOutput.contains("\"salary_max\" : 100000.0"));
        assertTrue(jsonOutput.contains("\"contract_time\" : \"Full-time\""));
        assertTrue(jsonOutput.contains("\"created\" : \"2024-08-07\""));
    }

    @Test
    public void testWriteCsv() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        DataFormatter.write(sampleJobs, Formats.CSV, out);
        String csvOutput = out.toString();
        String expectedCsvOutput = """
                Title,Company,Location,"Salary Min","Salary Max","Role Type","Date Posted"
                "Software Engineer",MockCompany,"Boston, MA","50,000","100,000",Full-time,2024-08-07
                """;

        assertEquals(expectedCsvOutput, csvOutput);
    }

    @Test
    public void testConvertSalaryZero() {
        assertEquals("N/A", DataFormatter.convertSalary(0));
    }

    @Test
    public void testConvertSalaryNegative() {
        assertEquals("N/A", DataFormatter.convertSalary(-5000));
    }

    @Test
    public void testConvertSalaryPositive() {
        assertEquals("50,000", DataFormatter.convertSalary(50000));
    }

    @Test
    public void testConvertSalaryLargeNumber() {
        assertEquals("1,000,000", DataFormatter.convertSalary(1000000));
    }

    @Test
    public void testConvertSalarySmallNumber() {
        assertEquals("1", DataFormatter.convertSalary(1));
    }
}
