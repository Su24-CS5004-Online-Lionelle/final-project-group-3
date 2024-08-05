package jobplanner.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

import jobplanner.model.api.JobPostUtil;
import jobplanner.model.models.IJobPostModel;
import jobplanner.model.models.ISavedJobModel;
import jobplanner.model.models.SavedJobModel;
import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.model.formatters.Formats;

public class ActionHandler {
    /** The job post model. */
    private IJobPostModel model;
    /** The saved jobs. */
    private ISavedJobModel savedJobs;

    /**
     * Create a new action handler.
     * 
     * @param model the job post model
     */
    public ActionHandler(IJobPostModel model) {
        this.model = model;
        this.savedJobs = SavedJobModel.loadFromJson();
    }

    /**
     * Get the saved jobs.
     * 
     * @return the saved jobs
     */
    public List<JobRecord> getSavedJobs() {
        return savedJobs.getSavedJobs();
    }

    /**
     * Add a job to the saved jobs.
     * 
     * @param job the job to add
     */
    public void saveJob(JobRecord job) {
        savedJobs.addSavedJob(job);
    }

    /**
     * Remove a job from the saved jobs.
     * 
     * @param job the job to remove
     */
    public void removeJob(JobRecord job) {
        savedJobs.removeSavedJob(job);
    }

    /**
     * Clear the saved jobs.
     */
    public void clearSavedJobs() {
        savedJobs.clearSavedJobs();
    }

    /**
     * Get the last date saved.
     * 
     * @return the last date saved
     */
    public int countSavedJobs() {
        return savedJobs.count();
    }

    /**
     * Save the jobs.
     */
    public void saveJobs() {
        savedJobs.save();
    }

    /**
     * Export the saved jobs.
     * 
     * @param format the format to export to
     */
    public void exportSavedJobs(Formats format) {
        savedJobs.export(format);
    }

    /**
     * Search for job postings.
     * 
     * @param country the country to search in
     * @param searchParams the search parameters
     * @return the job postings
     */
    public List<JobRecord> search(String country, Map<String, String> searchParams) {
        JobPostUtil client = new JobPostUtil(
            System.getenv("ADZUNA_APP_ID"), 
            System.getenv("ADZUNA_APP_KEY")
        );

        InputStream is = client.getJobPostings(country, searchParams);

        List<JobRecord> jobs = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonMap = mapper.readTree(is);
            JsonNode results = jsonMap.get("results");

            for (JsonNode result : results) {
                JobRecord job = mapper.treeToValue(result, JobRecord.class);
                jobs.add(job);
            }
            return jobs;

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
     * Get the job posts.
     * 
     * @return the job posts
     */
    public List<JobRecord> getJobPosts() {
        return model.getJobs();
    }
}
