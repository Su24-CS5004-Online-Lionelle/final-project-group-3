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
    IJobPostModel model;
    ISavedJobModel savedJobs;

    public ActionHandler(IJobPostModel model) {
        this.model = model;
        this.savedJobs = SavedJobModel.loadFromJson();
    }

    public List<JobRecord> getSavedJobs() {
        return savedJobs.getSavedJobs();
    }

    public void saveJob(JobRecord job) {
        savedJobs.addSavedJob(job);
    }

    public void removeJob(JobRecord job) {
        savedJobs.removeSavedJob(job);
    }

    public void clearSavedJobs() {
        savedJobs.clearSavedJobs();
    }

    public int countSavedJobs() {
        return savedJobs.count();
    }

    public void saveJobs() {
        savedJobs.save();
    }

    public void exportSavedJobs(Formats format) {
        savedJobs.export(format);
    }

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

    public List<JobRecord> getJobPosts() {
        return model.getJobs();
    }
}
