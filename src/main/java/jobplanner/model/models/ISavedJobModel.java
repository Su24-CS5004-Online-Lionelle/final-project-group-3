package jobplanner.model.models;

import java.time.LocalDate;
import java.util.List;

import jobplanner.model.models.IJobPostModel.JobRecord;
/**
 * Interface to the saved job model.
 * 
 */
public interface ISavedJobModel {

    /** Persistent database file path. */
    String FILEPATH = "data/savedJobs.json";
    
    /**
     * Get the last date saved.
     * 
     * @return the last date saved
     */
    LocalDate getLastSaved();

    /**
     * Set the last date saved.
     * 
     * @param date the last date saved
     */
    void setLastSaved(LocalDate date);

    /**
     * Get the number of saved jobs.
     * 
     * @return the number of saved jobs
     */
    int count();

    /**
     * Add a job to the saved jobs.
     * 
     * @param job the job to add
     */
    void addSavedJob(JobRecord job);

    /**
     * Remove a job from the saved jobs.
     * 
     * @param job the job to remove
     */
    void removeSavedJob(JobRecord job);

    /**
     * Get the saved jobs as a list.
     * 
     * @return the list of saved jobs
     */
    List<JobRecord> getSavedJobs();

    /**
     * Set the saved jobs as a list.
     *
     * @param jobs the list of saved jobs
     */
    void setSavedJobs(List<JobRecord> jobs);

    /**
     * Clear all saved jobs.
     */
    void clearSavedJobs();

    /**
     * Load the saved jobs from a file.
     * 
     * @return the saved job model
     */
    static ISavedJobModel loadFromJson() {
        return loadFromJson(FILEPATH);
    }

    /**
     * Get an instance of the model using the 'default' location.
     * 
     * @param filePath the file path to load from
     * @return the instance of the model
     */
    static ISavedJobModel loadFromJson(String filePath) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
