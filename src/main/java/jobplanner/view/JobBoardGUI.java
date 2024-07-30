package jobplanner.view;

import java.util.List;

import jobplanner.controller.ActionHandler;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.IJobPostModel.JobRecord;

public class JobBoardGUI {

    ActionHandler controller;

    public JobBoardGUI(ActionHandler controller) {
        this.controller = controller;
    }

    public void start() {
        System.out.println("Starting Job Board GUI");
        List<JobRecord> jobs = controller.getJobPosts();
        DataFormatter.write(jobs, Formats.PRETTY, System.out);
    }
}
