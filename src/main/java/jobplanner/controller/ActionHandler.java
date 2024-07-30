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
import jobplanner.model.models.IJobPostModel.JobRecord;

public class ActionHandler {
    IJobPostModel model;

    public ActionHandler(IJobPostModel model) {
        this.model = model;
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
