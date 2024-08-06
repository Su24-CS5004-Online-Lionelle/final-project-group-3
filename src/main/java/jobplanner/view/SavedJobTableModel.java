package jobplanner.view;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;

import jobplanner.model.models.IJobPostModel.JobRecord;

/**
 * JobTableModel is a custom table model for displaying a user's saved job records in a JTable.
 * It handles job data, including the ability to select jobs for further actions.
 */
public class SavedJobTableModel extends AbstractTableModel {

    /** List of saved job records. */
    private List<JobRecord> jobs;

    /** List to track selected rows. */
    private List<Boolean> selected;

    /** List to track applied job rows. */
    private List<Boolean> applied;

    /** Column names for the table. */
    private String[] columnNames = {"Selected", "Applied", "Title", "Company", "Salary Range", "Location"};

    /**
     * Constructs a JobTableModel with the given list of jobs.
     * Initializes the selection state for each job.
     *
     * @param jobs the list of job records to display
     */
    public SavedJobTableModel(List<JobRecord> jobs) {
        this.jobs = jobs != null ? jobs : new ArrayList<>();
        
        this.selected = new ArrayList<>(this.jobs.size());
        this.applied = new ArrayList<>(this.jobs.size());

        for (int i = 0; i < this.jobs.size(); i++) {
            selected.add(false); // Initialize all as not selected
            applied.add(false); // Initialize all as not applied
        }
    }

    /**
     * Returns the number of rows in the table, which corresponds to the number of saved jobs.
     *
     * @return the number of saved job records
     */
    @Override
    public int getRowCount() {
        return jobs.size();
    }

    /**
     * Returns the number of columns in the table.
     *
     * @return the number of columns
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the value at the specified cell in the table.
     * The value can represent different job attributes depending on the column index.
     *
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the value at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JobRecord job = jobs.get(rowIndex);
        switch (columnIndex) {
            // returns the corresponding value (true or false) from the 'selected' list.
            case 0: return selected.get(rowIndex); // selected status
            case 1: return applied.get(rowIndex); // applied status
            case 2: return job.title(); // job title
            case 3: return job.company().displayName(); // company name
            case 4: return convertSalary(job.salaryMin()) + " - " + convertSalary(job.salaryMax()); // salary range
            case 5: return job.location().displayName(); // job location
            default: return null;
        }
    }

    /**
     * Sets the value at the specified cell. This is primarily used to update the selection status.
     *
     * When a user clicks on a checkbox in the table, the JTable registers this interaction
     * and determines that the cell's value needs to be updated.
     *
     * @param aValue the value to set
     * @param rowIndex the row index
     * @param columnIndex the column index
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            selected.set(rowIndex, (Boolean) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        } else if (columnIndex == 1) {
            applied.set(rowIndex, (Boolean) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    /**
     * Determines whether a cell is editable.
     * Only the selection column (first column) is editable.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return true if the cell is editable, false otherwise
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1; // Only allow selection and applied status to be edited
    }

    /**
     * Returns Boolean.class for the first column. This tells the JTable that the data in this column
     * is of type Boolean, which allows the table to render checkboxes for this column.
     *
     * @param columnIndex the column index
     * @return the class of the column's data
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0 || columnIndex == 1) {
            return Boolean.class; // First two columns are checkboxes
        }
        return String.class;
    }

    /**
     * Returns the name of the column at the specified index.
     *
     * @param column the column index
     * @return the name of the column
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Returns a list of job records that have been selected.
     *
     * @return a list of selected job records
     */
    public List<JobRecord> getSelectedJobs() {
        List<JobRecord> selectedJobs = new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            if (selected.get(i)) { // if checkbox is marked, add to the list of saved jobs
                selectedJobs.add(jobs.get(i));
            }
        }
        return selectedJobs;
    }

    /**
     * Returns a list of job records that have been applied to.
     *
     * @return a list of applied job records
     */
    public List<JobRecord> getAppliedJobs() {
        List<JobRecord> appliedJobs = new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            if (applied.get(i)) { // if checkbox is marked, add to the list of saved jobs
                appliedJobs.add(jobs.get(i));
            }
        }
        return appliedJobs;
    }

    /**
     * Returns the job record at the specified row index.
     *
     * @param rowIndex the index of the row
     * @return the JobRecord at the specified row
     */
    public JobRecord getJobAt(int rowIndex) {
        return jobs.get(rowIndex);
    }

    /**
     * Sets the job records to be displayed in the table.
     *
     * @param jobs the list of job records
     */
    public void setJobs(List<JobRecord> jobs) {
        this.jobs = jobs;
        this.selected = new ArrayList<>(this.jobs.size());
        this.applied = new ArrayList<>(this.jobs.size());
        for (int i = 0; i < this.jobs.size(); i++) {
            selected.add(false); // Initialize all as not selected
            applied.add(false); // Initialize all as not applied
        }
        fireTableDataChanged();
    }

    /**
     * Convert salary to dollar amount.
     * 
     * @param salary the salary to convert
     * @return the salary in dollar amount
     */
    private String convertSalary(double salary) {
        if (salary <= 0) {
            return "N/A";
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(salary);
    }
}
