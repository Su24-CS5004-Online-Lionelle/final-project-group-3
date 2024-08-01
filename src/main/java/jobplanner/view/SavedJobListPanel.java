package jobplanner.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import jobplanner.model.models.IJobPostModel.JobRecord;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * SavedJobListPanel represents a panel displaying a list of saved job postings.
 * The panel includes a table with job details and buttons to export the list to CSV or TXT.
 */
public class SavedJobListPanel extends JPanel {

    /** The table displaying saved job records. */
    private JTable savedJobTable;

    /** The table model for managing saved job records. */
    private DefaultTableModel savedJobTableModel;

    /** Button for exporting the saved jobs as a CSV file. */
    private JButton exportCsvButton;

    /** Button for exporting the saved jobs as a TXT file. */
    private JButton exportTxtButton;

    /**
     * Constructs a new SavedJobListPanel.
     * This panel includes a table for saved jobs, a label, and export buttons.
     */
    public SavedJobListPanel() {
        setLayout(new BorderLayout());
        initializeUIComponents();
    }

    /**
     * Initializes the UI components for the panel.
     */
    private void initializeUIComponents() {
        initializeHeaderLabel();
        initializeSavedJobTable();
        initializeButtonPanel();
    }

    /**
     * Initializes the header label.
     */
    private void initializeHeaderLabel() {
        JLabel savedJobsLabel = new JLabel("Saved Jobs");
        savedJobsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        savedJobsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(savedJobsLabel, BorderLayout.NORTH);
    }

    /**
     * Initializes the saved job table.
     */
    private void initializeSavedJobTable() {
        savedJobTableModel = new DefaultTableModel(new Object[]{"Applied", "Title", "Company", "Salary Range", "Location"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
        };

        savedJobTable = new JTable(savedJobTableModel);
        savedJobTable.getColumnModel().getColumn(0).setMaxWidth(70);
        add(new JScrollPane(savedJobTable), BorderLayout.CENTER);
    }

    /**
     * Initializes the button panel for export options.
     */
    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        exportCsvButton = new JButton("Export as CSV");
        exportTxtButton = new JButton("Export as TXT");

        // Set the background colors for the buttons
        exportCsvButton.setBackground(new Color(144, 238, 144)); // Set the background color to green
        exportCsvButton.setForeground(Color.BLACK); // Set the text color to black
        exportCsvButton.setOpaque(true);            // Ensure the button is opaque
        exportCsvButton.setBorderPainted(false);    // Optional: remove the button border

        exportTxtButton.setBackground(new Color(135, 206, 235));  // Set the background color to blue
        exportTxtButton.setForeground(Color.BLACK); // Set the text color to white
        exportTxtButton.setOpaque(true);            // Ensure the button is opaque
        exportTxtButton.setBorderPainted(false);    // Optional: remove the button border

        buttonPanel.add(exportCsvButton);
        buttonPanel.add(exportTxtButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the job records to be displayed in the table.
     *
     * @param jobs a list of JobRecord objects to display
     */
    public void setJobs(List<JobRecord> jobs) {
        savedJobTableModel.setRowCount(0);
        for (JobRecord job : jobs) {
            String salaryRange = job.salaryMin() + " - " + job.salaryMax();
            savedJobTableModel.addRow(new Object[]{
                    false, job.title(), job.company().displayName(), salaryRange, job.location().displayName()
            });
        }
    }

    /**
     * Returns the table model containing the saved job data.
     *
     * @return the DefaultTableModel used by the saved jobs table
     */
    public DefaultTableModel getModel() {
        return savedJobTableModel;
    }

    /**
     * Returns the button used to export the list as a CSV file.
     *
     * @return the JButton for exporting to CSV
     */
    public JButton getExportCsvButton() {
        return exportCsvButton;
    }

    /**
     * Returns the button used to export the list as a TXT file.
     *
     * @return the JButton for exporting to TXT
     */
    public JButton getExportTxtButton() {
        return exportTxtButton;
    }

    /**
     * Sets the action listeners for the buttons in the JobListPanel.
     *
     * @param listener The ActionListener to attach.
     */
    public void setListeners(ActionListener listener) {
        exportCsvButton.setActionCommand("Export as TXT");
        exportCsvButton.addActionListener(listener);

        exportTxtButton.setActionCommand("Export as TXT");
        exportTxtButton.addActionListener(listener);
    }
}
