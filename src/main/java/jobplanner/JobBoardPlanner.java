package jobplanner;

import jobplanner.controller.JobPlannerController;
import jobplanner.model.models.JobPostModel;
import jobplanner.view.JobPlannerGUI;

import javax.swing.*;

public class JobBoardPlanner {
    public static void main(String[] args) {
        // Load the model from JSON file
        JobPostModel model = (JobPostModel) JobPostModel.getInstance("data/jobpostings.json");

        // Initialize the GUI components with the loaded model
        JobPlannerGUI view = new JobPlannerGUI(model);

        // Initialize the controller with the model and view
        JobPlannerController controller = new JobPlannerController(model, view);

        // Start the application
        SwingUtilities.invokeLater(controller::start);
    }
}

