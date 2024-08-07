package jobplanner.view.tablemodels;

import jobplanner.model.models.IJobPostModel.JobRecord;

import java.util.List;
import java.util.ArrayList;

/**
 * SavedJobTableModel is a custom table model for displaying a user's saved job records in a JTable.
 * It extends AbstractJobTableModel and includes additional functionality for tracking applied jobs.
 */
public class SavedJobTableModel extends AbstractJobTableModel {

    /** Column names for the saved job table. */
    private static final String[] COLUMN_NAMES = {"Selected", "Applied", "Title", 
        "Company", "Salary Range", "Location"};

    /** List to track applied job rows. */
    private List<Boolean> applied;

    /**
     * Constructs a SavedJobTableModel with the given list of jobs.
     * Initializes the selection and applied state for each job.
     *
     * @param jobs the list of job records to display
     */
    public SavedJobTableModel(List<JobRecord> jobs) {
        super(jobs, COLUMN_NAMES);
        this.applied = new ArrayList<>(this.jobs.size());
        for (int i = 0; i < this.jobs.size(); i++) {
            applied.add(false);
        }
    }

    /**
     * Returns the value at the specified cell in the table.
     * The value can represent different job attributes depending on the column index.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the value at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JobRecord job = jobs.get(rowIndex);
        switch (columnIndex) {
            case 0: return selected.get(rowIndex); // Selected status
            case 1: return applied.get(rowIndex); // Applied status
            case 2: return job.title(); // Job title
            case 3: return job.company().displayName(); // Company name
            case 4: return convertSalary(job.salaryMin()) + " - " + convertSalary(job.salaryMax()); // Salary range
            case 5: return job.location().displayName(); // Job location
            default: return null;
        }
    }

    /**
     * Sets the value at the specified cell. This is primarily used to update the selection and applied status.
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
     * Only the selection and applied columns (first two columns) are editable.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return true if the cell is editable, false otherwise
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1;
    }

    /**
     * Returns the class of the column's data. The first two columns are of type Boolean to render checkboxes.
     *
     * @param columnIndex the column index
     * @return the class of the column's data
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 || columnIndex == 1 ? Boolean.class : String.class;
    }

    /**
     * Returns a list of job records that have been applied to.
     *
     * @return a list of applied job records
     */
    public List<JobRecord> getAppliedJobs() {
        List<JobRecord> appliedJobs = new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            if (applied.get(i)) {
                appliedJobs.add(jobs.get(i));
            }
        }
        return appliedJobs;
    }

    /**
     * Updates the list of jobs and resets the selection and applied state.
     *
     * @param jobs the new list of job records
     */
    @Override
    public void setJobs(List<JobRecord> jobs) {
        super.setJobs(jobs);
        this.applied = new ArrayList<>(this.jobs.size());
        for (int i = 0; i < this.jobs.size(); i++) {
            applied.add(false);
        }
    }
}
