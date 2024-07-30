package jobplanner.model.models;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jobplanner.model.models.IJobPostModel.JobRecord;

public class SavedJobModel implements ISavedJobModel {
    List<JobRecord> savedJobs;

    public SavedJobModel(List<JobRecord> savedJobs) {
        this.savedJobs = savedJobs;
    }

    public List<JobRecord> getSavedJobs() {
        return savedJobs;
    }

    public void addSavedJob(JobRecord job) {
        savedJobs.add(job);
    }

    public void removeSavedJob(JobRecord job) {
        savedJobs.remove(job);
    }

    public void clearSavedJobs() {
        savedJobs.clear();
    }

    public int count() {
        return savedJobs.size();
    }

    public SavedJobModel getInstance() {
        return getInstance(FILEPATH);
    }

    public static SavedJobModel getInstance(String filePath) {
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
