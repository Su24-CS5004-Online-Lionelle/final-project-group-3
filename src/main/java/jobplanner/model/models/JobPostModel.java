package jobplanner.model.models;

import java.util.List;
import java.io.FileInputStream;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A class to represent the model of the job postings.
 */
public final class JobPostModel implements IJobPostModel {
    /** Persistent database file path */
    private List<JobRecord> jobs;

    /**
     * Private constructor to prevent instantiation.
     * 
     * @param jobs the list of jobs
     */
    private JobPostModel(List<JobRecord> jobs) {
        this.jobs = jobs;
    }

    @Override
    public List<JobRecord> getJobs() {
        return jobs;
    }

    /**
     * Gets an instance of the model using the 'default' location.
     * 
     * @return the instance of the model
     */
    public static IJobPostModel getInstance() {
        return getInstance(DATABASE);
    }

    /**
     * Gets an instance of the model using the specified database file.
     * 
     * @param database the name of the file to use
     * @return the instance of the model
     */
    public static IJobPostModel getInstance(String database) {
        try (InputStream inputStream = new FileInputStream(database)) {
            ObjectMapper jsonMapper = new ObjectMapper();
            List<JobRecord> records = jsonMapper.readValue(inputStream,
                    jsonMapper.getTypeFactory().constructCollectionType(List.class, JobRecord.class));
            return new JobPostModel(records);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load model from " + database, e);
        }
    }
}
