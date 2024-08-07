package jobplanner.view.panels;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.view.tablemodels.JobTableModel;

/**
 * JobListPanel represents the panel that displays the list of job records in a table format.
 * It includes a search bar for filtering jobs and a button for showing saved jobs.
 */
public class JobListPanel extends JPanel {

    /** The table displaying job records. */
    private JTable jobTable;

    /** The table model for managing job records. */
    private JobTableModel jobTableModel;

    /** The search bar for filtering job records. */
    private JTextField searchBar;

    /** The button to show saved jobs. */
    private JButton showSavedJobsButton;

    /** The button to add selected jobs to saved list. */
    private JButton addSavedJobsButton;

    /**
     * Constructs a new JobListPanel.
     * Initializes the table for displaying job records and the search bar for filtering.
     *
     * @param jobTableModel The table model containing job data.
     */
    public JobListPanel(JobTableModel jobTableModel) {
        this.jobTableModel = jobTableModel;
        setLayout(new BorderLayout());
        initializeUIComponents();
    }

    /**
     * Initializes UI components and layout.
     */
    private void initializeUIComponents() {
        initializeSearchPanel();
        initializeJobTable();
        initializeButtonPanel();
    }

    /**
     * Initializes the search panel and search bar.
     */
    private void initializeSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchBar = new JTextField();
        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(searchBar, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);
    }

    /**
     * Initializes the job listings table.
     */
    private void initializeJobTable() {
        jobTable = new JTable(jobTableModel);
        jobTable.setFillsViewportHeight(true);
        jobTable.getColumnModel().getColumn(0).setMaxWidth(50); // Set max width for the selection column
        adjustCategoryColumnWidth();
        add(new JScrollPane(jobTable), BorderLayout.CENTER);

        // Add a mouse listener to show job details on double-click
        jobTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && jobTable.getSelectedRow() != -1) {
                    int selectedRow = jobTable.getSelectedRow();
                    JobRecord job = jobTableModel.getJobAt(selectedRow);
                    showJobDetails(job);
                }
            }
        });
    }

    /**
     * Initializes the button panel.
     */
    private void initializeButtonPanel() {
        showSavedJobsButton = new JButton("Show Saved Jobs");
        addSavedJobsButton = new JButton("Add to Saved Jobs");

        // add green color to the add saved jobs button
        addSavedJobsButton.setBackground(new Color(50, 205, 50));  // Set the background color to green
        addSavedJobsButton.setForeground(Color.BLACK); // Set the text color to white
        addSavedJobsButton.setOpaque(true);            // Ensure the button is opaque
        addSavedJobsButton.setBorderPainted(false);    // Optional: remove the button border

        // add color to the show saved jobs button
        showSavedJobsButton.setBackground(new Color(135, 206, 235));  // Set the background color to blue
        showSavedJobsButton.setForeground(Color.BLACK); // Set the text color to white
        showSavedJobsButton.setOpaque(true);            // Ensure the button is opaque
        showSavedJobsButton.setBorderPainted(false);    // Optional: remove the button border

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showSavedJobsButton);
        buttonPanel.add(addSavedJobsButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Adjusts the width of the "Category" column to fit the longest string.
     */
    private void adjustCategoryColumnWidth() {
        int categoryColumnIndex = 5; // Index of the "Category" column in the table model
        TableColumn categoryColumn = jobTable.getColumnModel().getColumn(categoryColumnIndex);
        FontMetrics fontMetrics = jobTable.getFontMetrics(jobTable.getFont());

        int maxWidth = 0;
        for (int row = 0; row < jobTableModel.getRowCount(); row++) {
            String category = (String) jobTableModel.getValueAt(row, categoryColumnIndex);
            if (category != null) {
                int width = fontMetrics.stringWidth(category);
                maxWidth = Math.max(maxWidth, width);
            }
        }

        int padding = 10; // Add padding to account for cell margins
        int maxColumnWidth = 150; // Set a maximum column width to avoid overly wide columns
        maxWidth = Math.min(maxWidth + padding, maxColumnWidth); // Limit to max width with padding

        categoryColumn.setPreferredWidth(maxWidth);
        categoryColumn.setMaxWidth(maxWidth);
    }

    /**
     * Displays a dialog with the details of the selected job.
     * The description is scrollable if it exceeds the display area.
     *
     * @param job The job record to display.
     */
    private void showJobDetails(JobRecord job) {
        String details = String.format("Title: %s\nCompany: %s\nLocation: %s\n\nDescription:\n%s",
                job.title(), job.company().displayName(), job.location().displayName(), job.description());

        JTextArea textArea = new JTextArea(details);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Job Details", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Returns the JTable component displaying the job records.
     *
     * @return The JTable displaying job records.
     */
    public JTable getJobTable() {
        return jobTable;
    }

    /**
     * Returns the JobTableModel used by this panel.
     *
     * @return The JobTableModel used by this panel.
     */
    public JobTableModel getJobTableModel() {
        return jobTableModel;
    }

    /**
     * Returns the text entered in the search bar.
     * Can be used for filtering job records.
     *
     * @return The search text entered by the user.
     */
    public String getSearchText() {
        return searchBar.getText();
    }

    /**
     * Gets the "Show Saved Jobs" button.
     *
     * @return The JButton for showing saved jobs.
     */
    public JButton getShowSavedJobsButton() {
        return showSavedJobsButton;
    }

    /**
     * Gets the "Add to Saved Jobs" button.
     *
     * @return The JButton for adding selected jobs to saved list.
     */
    public JButton getAddSavedJobsButton() {
        return addSavedJobsButton;
    }

    /**
     * Sets the action listeners for the buttons in the JobListPanel.
     *
     * @param listener The ActionListener to attach.
     */
    public void setListeners(ActionListener listener) {
        showSavedJobsButton.setActionCommand("Show Saved Jobs");
        showSavedJobsButton.addActionListener(listener);
        addSavedJobsButton.setActionCommand("Add to Saved Jobs");
        addSavedJobsButton.addActionListener(listener);
    }
}
