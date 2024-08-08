package jobplanner.model.api;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.IJobPostModel.JobRecord;
import io.github.cdimascio.dotenv.Dotenv;


/**
 * A class to help with pulling data from
 * https://api.adzuna.com/v1/api/jobs/.
 * 
 * You can read more about the API at https://developer.adzuna.com/docs/search.
 */
public final class JobPostUtil {
    /** API base url. */
    private static final String URL = "https://api.adzuna.com/v1/api/jobs/%s/%s/%s";
    /** API id. */
    private String appId;
    /** API key. */
    private String appKey;
    /** Default country. */
    private static final String COUNTRY = "us";
    /** Search endpoint. */
    private static final String SEARCH = "search";
    /** Default page number. */
    private static final String PAGES = "1";

    /**
     * Constructor for the JobPostUtil.
     * 
     * @param appId  The API id.
     * @param appKey The API key.
     */
    public JobPostUtil(String appId, String appKey) {
        Dotenv dotenv = Dotenv.load();
        this.appId = dotenv.get("ADZUNA_APP_ID");
        this.appKey = dotenv.get("ADZUNA_APP_KEY");
    }

    /**
     * Returns the base URL for the API request.
     * 
     * @param country  The country to search in.
     * @param endpoint The endpoint to search.
     * @param pages    The number of pages to search.
     * 
     * @return The base URL for the API request.
     */
    private static String getBaseUrl(String country, String endpoint, String pages) {
        return new StringBuilder(String.format(URL, country, endpoint, pages)).toString();
    }

    /**
     * Returns the search query.
     * 
     * @param endpoint The endpoint to search.
     * @param country  The country to search in.
     * @param params   The parameters to search for.
     * 
     * @return The search query.
     */
    private String buildQueryString(String endpoint, String country, Map<String, String> params) {
        if (country.isEmpty()) {
            country = COUNTRY;
        }

        String baseUrl = getBaseUrl(country, SEARCH, PAGES);
        StringBuilder query = new StringBuilder(baseUrl);

        query.append("?app_id=").append(appId);
        query.append("&app_key=").append(appKey);

        // Add the parameters to the query string
        for (String key : params.keySet()) {
            query.append("&").append(key).append("=")
                    .append(URLEncoder.encode(params.get(key), StandardCharsets.UTF_8));
        }

        return query.toString();
    }

    /**
     * Returns the job postings as an InputStream.
     * 
     * @return The job postings as an InputStream.
     */
    public InputStream getJobPostings() {
        return getJobPostings(COUNTRY, null);
    }

    /**
     * Returns the job postings as an InputStream.
     * 
     * @param country The country to search in.
     * @return The job postings as an InputStream.
     */
    public InputStream getJobPostings(String country) {
        return getJobPostings(country, null);
    }

    /**
     * Returns the job postings as an InputStream.
     * 
     * @param params The parameters to search for.
     * @return The job postings as an InputStream.
     */
    public InputStream getJobPostings(Map<String, String> params) {
        return getJobPostings(COUNTRY, params);
    }

    /**
     * Returns the job postings as an InputStream.
     * 
     * @param country The country to search in.
     * @param params  The parameters to search for.
     * @return The job postings as an InputStream.
     */
    public InputStream getJobPostings(String country, Map<String, String> params) {
        String query = buildQueryString(SEARCH, country, params);
        return getUrlContents(query);
    }

    /**
     * Get a list of job postings from the API.
     * 
     * @param country The country to search in.
     * @param params The parameters to search for.
     * @return The list of job postings.
     */
    public List<JobRecord> getJobPostingList(String country, Map<String, String> params) {
        InputStream is = getJobPostings(country, params);
        List<JobRecord> jobs = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonMap = mapper.readTree(is);
            JsonNode results = jsonMap.get("results");

            for (JsonNode result : results) {
                JobRecord job = mapper.treeToValue(result, JobRecord.class);
                jobs.add(job);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jobs;
    }

    /**
     * Gets the contents of a URL as an InputStream.
     * 
     * @param urlStr the URL to get the contents of
     * @return the contents of the URL as an InputStream, or the null InputStream if
     *         the connection
     *         fails
     * 
     *         Sourced from
     *         https://github.com/Su24-CS5004-Online-Lionelle/homework-07-Scarvy/blob/
     *          142c8726043fb851b0970b0955029db71d396662/src/main/java/student/model/net/NetUtils.java#L76
     */
    public InputStream getUrlContents(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                            + "(KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
            int status = con.getResponseCode();
            if (status == 200) {
                return con.getInputStream();
            } else {
                System.err.println("Failed to connect to " + urlStr);
            }

        } catch (Exception ex) {
            System.err.println("Failed to connect to " + urlStr);
        }
        return InputStream.nullInputStream();
    }

    /**
     * Main method to test the API request.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // test the API request
        Map<String, String> params = new HashMap<>();
        params.put("what", "java developer");
        params.put("salary_min", "50000");
        params.put("full_time", "1");
        params.put("where", "boston");
        params.put("category", "it-jobs");
        params.put("sort_by", "date");
        params.put("results_per_page", "50");

        String appId = System.getenv("ADZUNA_APP_ID");
        String appKey = System.getenv("ADZUNA_APP_KEY");

        JobPostUtil client = new JobPostUtil(appId, appKey);
        InputStream is = client.getJobPostings(params);

        // deserialize the JSON response to a list of JobRecord objects
        List<JobRecord> jobs = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonMap = mapper.readTree(is);
            JsonNode results = jsonMap.get("results");

            for (JsonNode result : results) {
                JobRecord job = mapper.treeToValue(result, JobRecord.class);
                jobs.add(job);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // write the list of jobs to a file in JSON format using outputstream
        DataFormatter.write(jobs, Formats.JSON, System.out);
    }
}
