package jobplanner.view;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;

/**
 * SavedJobListPanel represents a panel displaying a list of saved job postings.
 * The panel includes a table with job details and buttons to export the list to
 * CSV or TXT.
 */
public class SavedJobsPanel extends JPanel {

    /** The table displaying saved job records. */
    private JTable savedJobTable;

    /** The table model for managing saved job records. */
    private SavedJobTableModel savedJobTableModel;

    /** Button for exporting the saved jobs as a CSV file. */
    private JButton exportCsvButton;

    /** Button for exporting the saved jobs as a TXT file. */
    private JButton exportTxtButton;

    /** Button for removing selected saved jobs. */
    private JButton removeButton;

    /**
     * Constructs a new SavedJobListPanel.
     * This panel includes a table for saved jobs, a label, and export buttons.
     *
     * @param savedJobTableModel The table model containing saved job data.
     */
    public SavedJobsPanel(SavedJobTableModel savedJobTableModel) {
        this.savedJobTableModel = savedJobTableModel;
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

        // Create the table with the saved job data
        savedJobTable = new JTable(savedJobTableModel);

        // Set the column widths
        savedJobTable.getColumnModel().getColumn(0).setMaxWidth(70);
        savedJobTable.getColumnModel().getColumn(1).setMaxWidth(70);
        
        // Set the column widths
        add(new JScrollPane(savedJobTable), BorderLayout.CENTER);
    }

    /** 
     * Get the job saved table.
     * 
     * @return the saved job table
    */
    public JTable getSavedJobTable() {
        return savedJobTable;
    }

    /** 
     * Get the job saved table model.
     * 
     * @return the saved job table model
     */
    public SavedJobTableModel getSavedJobTableModel() {
        return savedJobTableModel;
    }

    /**
     * Initializes the button panel for export options.
     */
    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        removeButton = new JButton("Remove Selected");
        exportCsvButton = new JButton("Export as CSV");
        exportTxtButton = new JButton("Export as TXT");

        // Set the background colors for the buttons
        removeButton.setBackground(new Color(255, 99, 71)); // Set the background color to red
        removeButton.setForeground(Color.BLACK); // Set the text color to black
        removeButton.setOpaque(true); // Ensure the button is opaque
        removeButton.setBorderPainted(false); // Optional: remove the button border

        exportCsvButton.setBackground(new Color(144, 238, 144)); // Set the background color to green
        exportCsvButton.setForeground(Color.BLACK); // Set the text color to black
        exportCsvButton.setOpaque(true); // Ensure the button is opaque
        exportCsvButton.setBorderPainted(false); // Optional: remove the button border

        exportTxtButton.setBackground(new Color(135, 206, 235)); // Set the background color to blue
        exportTxtButton.setForeground(Color.BLACK); // Set the text color to white
        exportTxtButton.setOpaque(true); // Ensure the button is opaque
        exportTxtButton.setBorderPainted(false); // Optional: remove the button border

        buttonPanel.add(exportCsvButton);
        buttonPanel.add(exportTxtButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }



    /**
     * Sets the action listeners for the buttons in the JobListPanel.
     *
     * @param listener The ActionListener to attach.
     */
    public void setListeners(ActionListener listener) {
        exportCsvButton.setActionCommand("Export as CSV");
        exportCsvButton.addActionListener(listener);

        exportTxtButton.setActionCommand("Export as TXT");
        exportTxtButton.addActionListener(listener);

        removeButton.setActionCommand("Remove Selected");
        removeButton.addActionListener(listener);
    }
}
