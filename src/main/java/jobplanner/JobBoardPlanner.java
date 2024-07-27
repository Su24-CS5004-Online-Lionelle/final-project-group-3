package jobplanner;

import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.IJobPostModel;
import jobplanner.model.models.JobPostModel;
import jobplanner.model.models.IJobPostModel.JobRecord;

import java.util.List;

public class JobBoardPlanner {
    public static void main(String[] args) {
        IJobPostModel model = JobPostModel.getInstance("data/jobpostings.json");
        List<JobRecord> jobs = model.getJobs();

        // Print out the jobs
        DataFormatter.write(jobs, Formats.JSON, System.out);
    }
}
