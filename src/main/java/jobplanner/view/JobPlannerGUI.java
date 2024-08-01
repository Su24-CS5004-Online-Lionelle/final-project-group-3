package jobplanner.view;

import jobplanner.model.models.IJobPostModel;
import jobplanner.model.models.JobPostModel;
import jobplanner.model.models.JobTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * JobPlannerGUI represents the main GUI frame for the Job Planner application.
 * It includes panels for filters, job listings, and saved jobs.
 */
public class JobPlannerGUI extends JFrame {

    /** The panel for filtering job listings. */
    private FilterPanel filterPanel;

    /** The panel for displaying job listings. */
    private JobListPanel jobListPanel;

    /** The panel for displaying saved job listings. */
    private SavedJobListPanel savedJobListPanel;

    /**
     * Constructs a new JobPlannerGUI.
     * Initializes the main GUI components and layout.
     *
     * @param model The JobPostModel containing job data.
     */
    public JobPlannerGUI(JobPostModel model) {
        setTitle("Job Search Planner");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize panels
        filterPanel = new FilterPanel();
        jobListPanel = new JobListPanel(new JobTableModel(model.getJobs()));
        savedJobListPanel = new SavedJobListPanel();

        initializePanels(); // Set up the panels in the frame
    }

    /**
     * Initializes and adds the panels to the main frame.
     */
    private void initializePanels() {
        // Create and configure the filter label
        JLabel filterLabel = new JLabel("Filter By:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        filterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and configure the job listings label
        JLabel jobListingsLabel = new JLabel("Job Listings");
        jobListingsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        jobListingsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel for the filter section and add components
        JPanel filterSection = new JPanel(new BorderLayout());
        filterSection.add(filterLabel, BorderLayout.NORTH);
        filterSection.add(filterPanel, BorderLayout.CENTER);

        // Create a panel for the job listings section and add components
        JPanel jobListingsSection = new JPanel(new BorderLayout());
        jobListingsSection.add(jobListingsLabel, BorderLayout.NORTH);
        jobListingsSection.add(new JScrollPane(jobListPanel), BorderLayout.CENTER);

        // Add the filter and job listings sections to the main frame
        add(filterSection, BorderLayout.WEST);
        add(jobListingsSection, BorderLayout.CENTER);
    }

    /**
     * Displays a new window with saved job listings.
     *
     * @param savedJobs A list of saved job records to display.
     */
    public void showSavedJobsPanel(List<IJobPostModel.JobRecord> savedJobs) {
        savedJobListPanel.setJobs(savedJobs);

        JFrame savedJobsFrame = new JFrame("Saved Jobs");
        savedJobsFrame.setSize(800, 600);
        savedJobsFrame.setLayout(new BorderLayout());
        savedJobsFrame.add(savedJobListPanel, BorderLayout.CENTER);
        savedJobsFrame.setVisible(true);
    }

    /**
     * Sets the action listeners for components in the GUI.
     *
     * @param listener The ActionListener to attach.
     */
    public void setListeners(ActionListener listener) {
        filterPanel.setListeners(listener);
        jobListPanel.setListeners(listener);
        savedJobListPanel.setListeners(listener);
    }

    /**
     * Returns the filter panel component.
     *
     * @return The FilterPanel component.
     */
    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    /**
     * Returns the job list panel component.
     *
     * @return The JobListPanel component.
     */
    public JobListPanel getJobListPanel() {
        return jobListPanel;
    }

    /**
     * Returns the saved job list panel component.
     *
     * @return The SavedJobListPanel component.
     */
    public SavedJobListPanel getSavedJobListPanel() {
        return savedJobListPanel;
    }

    /**
     * Displays an error dialog with a specified message.
     *
     * @param message The error message to display.
     */
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
