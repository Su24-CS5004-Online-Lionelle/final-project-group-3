package jobplanner.model.models;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.IJobPostModel.JobRecord;

/**
 * A class to represent the model of the saved job postings.
 */
public final class SavedJobModel implements ISavedJobModel {
   
    /** A list of jobs. */
    private List<JobRecord> savedJobs = new ArrayList<>();
    /** The last saved date. */
    private Date lastSaved = null;

    /** private contructor. 
     * 
     * @param savedJobs the list of saved jobs
    */
    private SavedJobModel(List<JobRecord> savedJobs) {
        this.savedJobs = savedJobs;
    }

    /**
     * The last saved date.
     * 
     * @return the last saved date
     */
    public Date lastSaved() {
        return lastSaved;
    }

    @Override
    public List<JobRecord> getSavedJobs() {
        return savedJobs;
    }

    @Override
    public void setSavedJobs(List<JobRecord> jobs) {
        this.savedJobs = new ArrayList<>(jobs);
    }

    @Override
    public void addSavedJob(JobRecord job) {
        savedJobs.add(job);
    }

    @Override
    public void removeSavedJob(JobRecord job) {
        savedJobs.remove(job);
    }

    @Override
    public void clearSavedJobs() {
        savedJobs.clear();
    }

    @Override
    public int count() {
        return savedJobs.size();
    }


    /**
     * Load the saved jobs from a file.
     * 
     * @return the saved job model
     */
    public static ISavedJobModel loadFromJson() {
        return loadFromJson(FILEPATH);
    }

    /**
     * Get an instance of the model using the 'default' location.
     * 
     * @param filePath the file path to load from
     * @return the instance of the model
     */
    public static ISavedJobModel loadFromJson(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            ObjectMapper jsonMapper = new ObjectMapper();
            List<JobRecord> records = jsonMapper.readValue(inputStream,
                    jsonMapper.getTypeFactory().constructCollectionType(List.class, JobRecord.class));
            return new SavedJobModel(records);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load model from " + filePath, e);
        }
    }
}
