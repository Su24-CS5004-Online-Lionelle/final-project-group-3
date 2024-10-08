package jobplanner.controller;

import jobplanner.model.api.JobPostUtil;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.ISavedJobModel;
import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.model.types.JobCategory;
import jobplanner.view.JobPlannerGUI;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The JobPlannerController class is responsible for handling the interactions
 * between the view (GUI) and the model in the job planning application.
 * It processes user input from the GUI, applies filters to job data, and
 * updates the view.
 */
public class JobPlannerController implements ActionListener {
    /** The main GUI view. */
    private JobPlannerGUI view;

    /** The model containing saved jobs. */
    private ISavedJobModel savedJobsModel;

    /**
     * Constructs a JobPlannerController with the specified model and view.
     * Sets up action listeners for the view components.
     *
     * @param savedJobs the data model containing saved job records
     * @param view      the view component of the MVC architecture
     */
    public JobPlannerController(ISavedJobModel savedJobs, JobPlannerGUI view) {
        this.view = view;
        this.savedJobsModel = savedJobs;
        view.setListeners(this);
    }

    /**
     * Starts the application by making the main GUI window visible.
     */
    public void start() {
        SwingUtilities.invokeLater(() -> view.setVisible(true));
    }

    /**
     * Handles the actions performed in the GUI.
     * It processes button clicks and other user actions.
     *
     * @param e the event generated by user actions
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Apply Filter":
                search();
                break;
            case "Reset Filter":
                resetFilters();
                break;
            case "Add to Saved Jobs":
                addSelectedJobsToList();
                break;
            case "Remove Selected":
                removeSelectedJobs();
                break;
            case "Show Saved Jobs":
                showSavedJobs();
                break;
            case "Export as CSV":
                exportList(Formats.CSV);
                break;
            case "Export as TXT":
                exportList(Formats.PRETTY);
                break;
            default:
                break;
        }
    }

    /**
     * Exports the list of jobs to a file, either CSV or TXT format.
     *
     * @param format the format to export the list as.
     */
    private void exportList(Formats format) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();

            // check if user inputs a valid extension for filename
            if (InputValidator.isValidExtension(filename, format)) {
                try {
                    DataFormatter.write(savedJobsModel.getSavedJobs(), format, new FileOutputStream(filename));
                } catch (Exception e) {
                    view.showErrorDialog("Error exporting file: " + e.getMessage());
                }
            } else {
                view.showErrorDialog("Invalid file extension for the selected format.");
            }
        }
    }

    /**
     * Get a list of job postings from the Adzuna API.
     * 
     * @param country      the country to search in
     * @param searchParams the search parameters
     * @return the job postings
     */
    public List<JobRecord> searchJobPostings(String country, Map<String, String> searchParams) {
        JobPostUtil client = new JobPostUtil(
                System.getenv("ADZUNA_APP_ID"),
                System.getenv("ADZUNA_APP_KEY"));

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
     * Update the view with the job postings from the Adzuna API.
     * 
     */
    private void search() {
        String searchStr = view.getJobListPanel().getSearchText();

        String country = view.getFilterPanel().getSelectedCountry().toLowerCase();

        String selectedCategory = view.getFilterPanel().getSelectedCategory();
        String company = view.getFilterPanel().getCompany();
        Integer minSalary = parseInt(view.getFilterPanel().getMinSalary());
        Integer maxSalary = parseInt(view.getFilterPanel().getMaxSalary());
        List<String> roleTypes = view.getFilterPanel().getSelectedRoleTypes();

        Map<String, String> searchParams = new HashMap<>();

        // Build search query only if the value is selected or inputed by the user
        if (country.isEmpty() || country.equals("select")) {
            country = "us"; // default to United States
        }

        // search for a specific keyword like "title" or "description"
        if (!searchStr.isEmpty()) {
            searchParams.put("what", searchStr);
        }

        if (selectedCategory != null && !selectedCategory.equals("Select")) {
            searchParams.put("category", JobCategory.fromString(selectedCategory).getTag());
        }

        if (!company.isEmpty()) {
            searchParams.put("company", company);
        }

        if (minSalary >= 0) {
            // make sure the min salary is a positive number
            searchParams.put("salary_min", String.valueOf(minSalary));
        }

        if (maxSalary >= 0) {
            searchParams.put("salary_max", String.valueOf(maxSalary));
        }

        if (!roleTypes.isEmpty()) {
            if (roleTypes.contains("Full-time")) {
                searchParams.put("full_time", "1");
            } else if (roleTypes.contains("Part-time")) {
                searchParams.put("part_time", "1");
            } else if (roleTypes.contains("Contract")) {
                searchParams.put("full_time", "1");
            }
        }

        // set the number of results per page, default to 50
        searchParams.put("results_per_page", "50");

        List<JobRecord> jobs = searchJobPostings(country, searchParams);
        updateJobList(jobs);

        // write the list of jobs to a file in JSON format using outputstream
        try {
            DataFormatter.write(jobs, Formats.JSON, new FileOutputStream("data/jobpostings.json"));
        } catch (Exception e) {
            view.showErrorDialog("Error exporting file: " + e.getMessage());
        }
    }

    /**
     * Resets the filters in the FilterPanel to their default values and updates the
     * view.
     */
    private void resetFilters() {
        view.getFilterPanel().reset();
    }

    /**
     * Shows the saved jobs in a separate window.
     */
    private void showSavedJobs() {
        // Open saved jobs window
        view.showSavedJobsPanel();
    }

    /**
     * Adds the selected jobs to the list of saved jobs.
     */
    private void addSelectedJobsToList() {
        // Get list of selected jobs from the view
        List<JobRecord> selectedJobs = view.getJobListPanel().getJobTableModel().getSelectedJobs();

        // Add selected jobs to the saved jobs list
        for (JobRecord job : selectedJobs) {
            savedJobsModel.addSavedJob(job);
        }

        // update view with saved jobs
        view.getSavedJobsPanel().getSavedJobTableModel().setJobs(savedJobsModel.getSavedJobs());

        // Update the last saved date
        savedJobsModel.setLastSaved(LocalDate.now());

        // write updated list to persistant storage file
        writeSavedJobListToFile(savedJobsModel.getSavedJobs());
    }

    /**
     * Removes the selected jobs from the list of saved jobs.
     */
    private void removeSelectedJobs() {
        // Get list of selected jobs from the view
        List<JobRecord> selectedJobs = view.getSavedJobsPanel().getSavedJobTableModel().getSelectedJobs();

        // Remove selected jobs from the saved jobs list
        for (JobRecord job : selectedJobs) {
            savedJobsModel.removeSavedJob(job);
        }

        // update view with saved jobs
        view.getSavedJobsPanel().getSavedJobTableModel().setJobs(savedJobsModel.getSavedJobs());

        // Update the last saved date
        savedJobsModel.setLastSaved(LocalDate.now());

        // write updated list to persistant storage file
        writeSavedJobListToFile(savedJobsModel.getSavedJobs());
    }

    /**
     * Parses a string to a integer value. Returns -1 if the parsing fails.
     *
     * @param value the string to parse
     * @return the parsed integer value or -1 if parsing fails
     */
    private Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Updates the job list in the view with the provided job records.
     *
     * @param jobs the list of job records to display
     */
    public void updateJobList(List<JobRecord> jobs) {
        view.getJobListPanel().getJobTableModel().setJobs(jobs);
    }

    /**
     * Updates the persistant storage of the saved job list.
     * 
     * @param jobs the list of saved jobs to
     */
    private void writeSavedJobListToFile(List<JobRecord> jobs) {
        try {
            DataFormatter.write(savedJobsModel.getSavedJobs(), Formats.JSON,
                    new FileOutputStream("data/savedjobs.json"));
        } catch (Exception e) {
            view.showErrorDialog("Error exporting file: " + e.getMessage());
        }
    }
}
