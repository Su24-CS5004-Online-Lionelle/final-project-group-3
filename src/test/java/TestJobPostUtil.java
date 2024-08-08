import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobplanner.model.models.IJobPostModel.JobRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import jobplanner.model.api.JobPostUtil;

/**
 * Unit tests for the JobPostUtil class.
 */
public class TestJobPostUtil {

    /** JobPostUtil instance. */
    private JobPostUtil jobPostUtil;

    @BeforeEach
    void setUp() {
        String appId = "2489f9d0";
        String appKey = "c03e5ba3db6fb0c3edab2dfc03d8e4c9";

        jobPostUtil = new JobPostUtil(appId, appKey);
    }

    @Test
    void testGetJobPostings() {
        Map<String, String> params = new HashMap<>();
        params.put("what", "java developer");
        params.put("where", "boston");

        InputStream inputStream = jobPostUtil.getJobPostings("us", params);
        assertNotNull(inputStream, "InputStream should not be null");

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonMap = mapper.readTree(inputStream);
            assertTrue(jsonMap.has("results"), "JSON response should contain 'results' key");

            JsonNode results = jsonMap.get("results");
            assertTrue(results.isArray(), "'results' should be an array");

        } catch (Exception e) {
            fail("Exception thrown while parsing JSON: " + e.getMessage());
        }
    }

    @Test
    void testGetJobPostingList() {
        Map<String, String> params = new HashMap<>();
        params.put("what", "java developer");
        params.put("where", "boston");

        List<JobRecord> jobRecords = jobPostUtil.getJobPostingList("us", params);
        assertNotNull(jobRecords, "Job records list should not be null");
        assertFalse(jobRecords.isEmpty(), "Job records list should not be empty");

        for (JobRecord jobRecord : jobRecords) {
            assertNotNull(jobRecord.title(), "Job ID should not be null");
            assertNotNull(jobRecord.company(), "Job title should not be null");
            assertNotNull(jobRecord.description(), "Job description should not be null");
            assertNotNull(jobRecord.location(), "Job location should not be null");
        }
    }
}
