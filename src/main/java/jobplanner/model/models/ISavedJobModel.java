package jobplanner.model.models;

import java.util.List;
import jobplanner.model.models.IJobPostModel.JobRecord;

public interface ISavedJobModel {

    String FILEPATH = "savedJobs.json";
    
    int count();

    void addSavedJob(JobRecord job);

    void removeSavedJob(JobRecord job);

    List<JobRecord> getSavedJobs();

    void clearSavedJobs();

    static ISavedJobModel getInstance() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
