import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobplanner.model.models.IJobPostModel.JobRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;

import jobplanner.model.api.JobPostUtil;

class JobPostUtilTest {
    /** The api client wrapper object. */
    private JobPostUtil jobPostUtil;
    /** The app id. */
    private static final String APP_ID = "testAppId";
    /** The app key. */
    private static final String APP_KEY = "testAppKey";

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        jobPostUtil = new JobPostUtil(APP_ID, APP_KEY);
    }

    /**
     * Test the constructor.
     */
    @Test
    void testGetUrlContentsSuccess() throws Exception {
        String mockJsonResponse = "{\"results\": [{\"title\": \"Java Developer\", \"company\": \"Tech Co\"}]}";
        InputStream mockInputStream = new ByteArrayInputStream(mockJsonResponse.getBytes());

        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(200);
        when(mockConnection.getInputStream()).thenReturn(mockInputStream);

        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        InputStream resultStream = jobPostUtil.getUrlContents(mockUrl.toString());

        assertNotNull(resultStream);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonMap = mapper.readTree(resultStream);
        JsonNode results = jsonMap.get("results");
        assertEquals(1, results.size());
    }

    @Test
    void testGetUrlContentsFailure() throws Exception {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(404);

        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        InputStream resultStream = jobPostUtil.getUrlContents(mockUrl.toString());

        assertEquals(InputStream.nullInputStream(), resultStream);
    }

    @Test
    void testGetJobPostingList() throws Exception {
        // Mock API response JSON
        String mockJsonResponse = "{\"results\": [{\"title\": \"Java Developer\", \"company\": \"Tech Co\"}]}";
        InputStream mockInputStream = new ByteArrayInputStream(mockJsonResponse.getBytes());

        // Mock HttpURLConnection
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(200);
        when(mockConnection.getInputStream()).thenReturn(mockInputStream);

        // Mock URL
        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        // Spy on jobPostUtil to mock getUrlContents
        JobPostUtil spyJobPostUtil = Mockito.spy(jobPostUtil);
        doReturn(mockInputStream).when(spyJobPostUtil).getUrlContents(anyString());

        Map<String, String> params = new HashMap<>();
        params.put("what", "java developer");

        List<JobRecord> jobs = spyJobPostUtil.getJobPostingList("us", params);

        assertEquals(1, jobs.size());
        assertEquals("Java Developer", jobs.get(0).getTitle());
        assertEquals("Tech Co", jobs.get(0).getCompany());
    }
}
