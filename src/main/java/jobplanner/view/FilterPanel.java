package jobplanner.view;

import javax.swing.*;

import jobplanner.model.types.JobCategory;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * FilterPanel represents the panel that provides filtering options for job listings.
 * It includes filters for country, category, company, salary range, role type, and date posted.
 */
public class FilterPanel extends JPanel {

    /** ComboBox for selecting the country. */
    private JComboBox<String> countryComboBox;

    /** ComboBox for selecting the job category. */
    private JComboBox<String> categoryComboBox;

    /** TextField for entering the company name. */
    private JTextField companyTextField;

    /** TextField for entering the minimum salary. */
    private JTextField minSalaryField;

    /** TextField for entering the maximum salary. */
    private JTextField maxSalaryField;

    /** CheckBox for selecting full-time jobs. */
    private JCheckBox fullTimeCheckBox;

    /** CheckBox for selecting part-time jobs. */
    private JCheckBox partTimeCheckBox;

    /** CheckBox for selecting contract jobs. */
    private JCheckBox contractCheckBox;

    /** ComboBox for selecting the date posted filter. */
    private JComboBox<String> datePostedComboBox;

    /** Button to apply the filters. */
    private JButton applyFilterButton;

    /** Button to reset the filters. */
    private JButton resetFilterButton;

    /**
     * Constructs a new FilterPanel.
     * Initializes the filter components and layout.
     */
    public FilterPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 700));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initializeFilterComponents(); // initialize the Filter components
        initializeButtonPanel(); // initialize the Filter components


    }


    /**
     * Initializes the UI components for the filter panel.
     */
    private void initializeFilterComponents() {
        // Initialize and add the country combo box
        countryComboBox = new JComboBox<>(new String[]{"Select", "US", "GB", "AU"});
        add(createLabeledComboBox("Country", countryComboBox));
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Initialize and add the category combo box
        categoryComboBox = new JComboBox<>(jobCategoryLabelArray());
        add(createLabeledComboBox("Category", categoryComboBox));
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Initialize and add the company text field
        companyTextField = new JTextField(15);
        add(createLabeledTextField("Company", companyTextField));
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Initialize and add the salary range text fields
        minSalaryField = new JTextField(5);
        maxSalaryField = new JTextField(5);
        add(createLabeledSalaryRange("Salary Range", minSalaryField, maxSalaryField));
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Initialize and add the role type checkboxes
        fullTimeCheckBox = new JCheckBox("Full-time");
        partTimeCheckBox = new JCheckBox("Part-time");
        contractCheckBox = new JCheckBox("Contract");
        add(createRoleTypePanel());
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Initialize and add the date posted combo box
        datePostedComboBox = new JComboBox<>(new String[]{"Select", "Past week", "Past month", "Today"});
        add(createLabeledComboBox("Date Posted", datePostedComboBox));
        add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private static String[] jobCategoryLabelArray() {
        List<String> jobCategoryLabels = JobCategory.getCategoryLabels();
        jobCategoryLabels.add(0, "Select");
        return jobCategoryLabels.toArray(new String[jobCategoryLabels.size()]);
    }

    /**
     * Initializes the button components for the filter panel.
     */
    private void initializeButtonPanel() {
        // Initialize and add the apply and reset buttons
        applyFilterButton = new JButton("Apply");
        resetFilterButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // add color to the apply filter button
        applyFilterButton.setBackground(new Color(135, 206, 235));  // Set the background color to blue
        applyFilterButton.setForeground(Color.BLACK); // Set the text color to white
        applyFilterButton.setOpaque(true);            // Ensure the button is opaque
        applyFilterButton.setBorderPainted(false);    // Optional: remove the button border


        // Set grey color for the exportTxtButton
        resetFilterButton.setBackground(new Color(211, 211, 211)); // Light grey
        resetFilterButton.setForeground(Color.BLACK);
        resetFilterButton.setOpaque(true);
        resetFilterButton.setBorderPainted(false);

        buttonPanel.add(applyFilterButton);
        buttonPanel.add(resetFilterButton);
        add(buttonPanel);
    }
    /**
     * Creates a panel with a label and combo box.
     *
     * @param labelText the text for the label
     * @param comboBox  the combo box to add
     * @return the JPanel containing the label and combo box
     */
    private JPanel createLabeledComboBox(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a panel with a label and text field.
     *
     * @param labelText the text for the label
     * @param textField the text field to add
     * @return the JPanel containing the label and text field
     */
    private JPanel createLabeledTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a panel for the salary range input with labels.
     *
     * @param labelText the text for the label
     * @param minField  the text field for minimum salary
     * @param maxField  the text field for maximum salary
     * @return the JPanel containing the salary range input
     */
    private JPanel createLabeledSalaryRange(String labelText, JTextField minField, JTextField maxField) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        JPanel salaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        salaryPanel.add(minField);
        salaryPanel.add(new JLabel("to"));
        salaryPanel.add(maxField);
        panel.add(label, BorderLayout.NORTH);
        panel.add(salaryPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a panel for role type selection with checkboxes.
     *
     * @return the JPanel containing the role type checkboxes
     */
    private JPanel createRoleTypePanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel label = new JLabel("Role Type");
        panel.add(label);
        panel.add(fullTimeCheckBox);
        panel.add(partTimeCheckBox);
        panel.add(contractCheckBox);
        return panel;
    }

    /**
     * Resets all filter fields to their default values.
     */
    public void reset() {
        countryComboBox.setSelectedIndex(0);
        categoryComboBox.setSelectedIndex(0);
        companyTextField.setText("");
        minSalaryField.setText("");
        maxSalaryField.setText("");
        fullTimeCheckBox.setSelected(false);
        partTimeCheckBox.setSelected(false);
        contractCheckBox.setSelected(false);
        datePostedComboBox.setSelectedIndex(0);
    }

    /**
     * Gets the selected country from the country combo box.
     *
     * @return the selected country
     */
    public String getSelectedCountry() {
        return (String) countryComboBox.getSelectedItem();
    }

    /**
     * Gets the selected category from the category combo box.
     *
     * @return the selected category
     */
    public String getSelectedCategory() {
        return (String) categoryComboBox.getSelectedItem();
    }

    /**
     * Gets the company name entered in the company text field.
     *
     * @return the company name
     */
    public String getCompany() {
        return companyTextField.getText();
    }

    /**
     * Gets the minimum salary entered in the min salary field.
     *
     * @return the minimum salary
     */
    public String getMinSalary() {
        return minSalaryField.getText();
    }

    /**
     * Gets the maximum salary entered in the max salary field.
     *
     * @return the maximum salary
     */
    public String getMaxSalary() {
        return maxSalaryField.getText();
    }

    /**
     * Gets the selected role types from the checkboxes.
     *
     * @return a list of selected role types
     */
    public List<String> getSelectedRoleTypes() {
        List<String> selectedRoleTypes = new ArrayList<>();
        if (fullTimeCheckBox.isSelected()) {
            selectedRoleTypes.add("full_time");
        }
        if (partTimeCheckBox.isSelected()) {
            selectedRoleTypes.add("part_time");
        }
        if (contractCheckBox.isSelected()) {
            selectedRoleTypes.add("contract");
        }
        return selectedRoleTypes;
    }

    /**
     * Gets the selected date filter from the date posted combo box.
     *
     * @return the selected date filter
     */
    public String getDateFilter() {
        return (String) datePostedComboBox.getSelectedItem();
    }

    /**
     * Gets the apply filter button.
     *
     * @return the JButton for applying filters
     */
    public JButton getApplyFilterButton() {
        return applyFilterButton;
    }

    /**
     * Gets the reset filter button.
     *
     * @return the JButton for resetting filters
     */
    public JButton getResetFilterButton() {
        return resetFilterButton;
    }

    /**
     * Sets the action listeners for the buttons in the FilterPanel.
     *
     * @param listener the ActionListener to attach
     */
    public void setListeners(ActionListener listener) {
        applyFilterButton.setActionCommand("Apply Filter");
        applyFilterButton.addActionListener(listener);
        resetFilterButton.setActionCommand("Reset Filter");
        resetFilterButton.addActionListener(listener);
    }
}
