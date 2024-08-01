package jobplanner.model.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import jobplanner.model.models.IJobPostModel.JobRecord;

/**
 * JobTableModel is a custom table model for displaying job records in a JTable.
 * It handles job data, including the ability to select jobs for further actions.
 */
public class JobTableModel extends AbstractTableModel {

    /** List of job records. */
    private List<JobRecord> jobs;

    /** List to track selected rows. */
    private List<Boolean> selected;

    /** Column names for the table. */
    private String[] columnNames = {"Selected", "Title", "Location", "Description", "Salary", "Category", "Company"};

    /**
     * Constructs a JobTableModel with the given list of jobs.
     * Initializes the selection state for each job.
     *
     * @param jobs the list of job records to display
     */
    public JobTableModel(List<JobRecord> jobs) {
        this.jobs = jobs != null ? jobs : new ArrayList<>();
        this.selected = new ArrayList<>(this.jobs.size());
        for (int i = 0; i < this.jobs.size(); i++) {
            selected.add(false); // Initialize all as not selected
        }
    }

    /**
     * Returns the number of rows in the table, which corresponds to the number of jobs.
     *
     * @return the number of job records
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
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the value at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        JobRecord job = jobs.get(rowIndex);
        switch (columnIndex) {
            case 0: return selected.get(rowIndex); // selection status
            case 1: return job.title(); // job title
            case 2: return job.location().displayName(); // job location
            case 3: return job.description(); // job description
            case 4: return job.salaryMin() + " - " + job.salaryMax(); // salary range
            case 5: return job.category().label(); // job category
            case 6: return job.company().displayName(); // company name
            default: return null;
        }
    }

    /**
     * Sets the value at the specified cell. This is primarily used to update the selection status.
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
        }
    }

    /**
     * Determines whether a cell is editable. Only the selection column is editable.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return true if the cell is editable, false otherwise
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    /**
     * Returns the class of the data in each column, which is used by the JTable to render the cells.
     *
     * @param columnIndex the column index
     * @return the class of the column's data
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : String.class;
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
     * Updates the list of jobs and resets the selection state.
     *
     * @param jobs the new list of job records
     */
    public void setJobs(List<JobRecord> jobs) {
        this.jobs = jobs != null ? jobs : new ArrayList<>();
        this.selected = new ArrayList<>(jobs.size());
        for (int i = 0; i < jobs.size(); i++) {
            selected.add(false); // Initialize all as not selected
        }
        fireTableDataChanged(); // Notify listeners that the data has changed
    }

    /**
     * Returns a list of job records that have been selected.
     *
     * @return a list of selected job records
     */
    public List<JobRecord> getSelectedJobs() {
        List<JobRecord> selectedJobs = new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            if (selected.get(i)) {
                selectedJobs.add(jobs.get(i));
            }
        }
        return selectedJobs;
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
}
