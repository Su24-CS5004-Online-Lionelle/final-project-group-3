package jobplanner.model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import jobplanner.model.IJobPostModel.JobRecord;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;

/**
 * A class to help with pulling data from
 * https://api.adzuna.com/v1/api/jobs/.
 * 
 * You can read more about the API at https://developer.adzuna.com/docs/search.
 */
public final class JobPostUtil {
    /** A Client Wrapper for Adzuna API */
    private static String BASE_URL = "https://api.adzuna.com/v1/api/jobs/%s/%s/";
    private static String APP_ID;
    private static String APP_KEY;
    private static String COUNTRY = "us";
    private static String SEARCH_ENDPOINT = "search";

    /**
     * Prevent instantiation.
     */
    public JobPostUtil(String appId, String appKey) {
        APP_ID = appId;
        APP_KEY = appKey;
    }

    /**
     * Returns the base URL for the API request.
     * 
     * @param country  The country to search in.
     * @param endpoint The endpoint to search.
     * @return The base URL for the API request.
     */
    private static String getBaseUrl(String country, String endpoint) {
        return new StringBuilder(String.format(BASE_URL, country, endpoint)).toString();
    }

    /**
     * Returns the search query
     * 
     * @param country The country to search in.
     * @param params  The parameters to search for.
     */
    public static String buildQueryString(String endpoint, String country, HashMap<String, String> params) {
        if (country.isEmpty()) {
            country = COUNTRY;
        }

        String baseUrl = getBaseUrl(country, SEARCH_ENDPOINT);
        StringBuilder query = new StringBuilder(baseUrl);

        query.append("?app_id=").append(APP_ID);
        query.append("&app_key=").append(APP_KEY);

        // Add the parameters to the query string
        for (String key : params.keySet()) {
            query.append("&").append(key).append("=")
                    .append(URLEncoder.encode(params.get(key), StandardCharsets.UTF_8));
        }

        return query.toString();
    }

    public InputStream getJobPostings() {
        return getJobPostings(COUNTRY, null);
    }

    public InputStream getJobPostings(String country) {
        return getJobPostings(country, null);
    }

    public InputStream getJobPostings(HashMap<String, String> params) {
        return getJobPostings(COUNTRY, params);
    }

    public InputStream getJobPostings(String country, HashMap<String, String> params) {
        String query = buildQueryString(SEARCH_ENDPOINT, country, params);
        return getUrlContents(query);
    }

    /**
     * Gets the contents of a URL as an InputStream.
     * 
     * @param urlStr the URL to get the contents of
     * @return the contents of the URL as an InputStream, or the null InputStream if
     *         the connection
     *         fails
     * 
     * Sourced from https://github.com/Su24-CS5004-Online-Lionelle/homework-07-Scarvy/blob/142c8726043fb851b0970b0955029db71d396662/src/main/java/student/model/net/NetUtils.java#L76
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

    public static void main(String[] args) {
        // test the API request
        HashMap<String, String> params = new HashMap<>();
        params.put("what", "java developer");
        params.put("salary_min", "50000");
        params.put("full_time", "1");
        params.put("where", "boston");
        params.put("category", "it-jobs");

        String app_id = System.getenv("ADZUNA_APP_ID");
        String app_key = System.getenv("ADZUNA_APP_KEY");

        JobPostUtil client = new JobPostUtil(app_id, app_key);
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
        try {
            DataFormatter.write(jobs, Formats.JSON, new FileOutputStream("data/jobpostings.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
