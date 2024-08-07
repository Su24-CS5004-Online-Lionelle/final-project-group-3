package jobplanner.view.tablemodels;

import javax.swing.table.AbstractTableModel;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import jobplanner.model.models.IJobPostModel.JobRecord;

/**
 * AbstractJobTableModel is an abstract base class for table models displaying job records.
 * It handles common functionalities like managing the list of jobs and selection state.
 */
public abstract class AbstractJobTableModel extends AbstractTableModel {

    /** List of job records. */
    protected List<JobRecord> jobs;

    /** List to track selected rows. */
    protected List<Boolean> selected;

    /** Column names for the table. */
    protected String[] columnNames;

    /**
     * Constructs an AbstractJobTableModel with the given list of jobs and column names.
     * Initializes the selection state for each job.
     *
     * @param jobs the list of job records to display
     * @param columnNames the names of the columns
     */
    public AbstractJobTableModel(List<JobRecord> jobs, String[] columnNames) {
        this.jobs = jobs != null ? jobs : new ArrayList<>();
        this.selected = new ArrayList<>(this.jobs.size());
        for (int i = 0; i < this.jobs.size(); i++) {
            selected.add(false);
        }
        this.columnNames = columnNames;
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
     * Determines whether a cell is editable.
     * Only the selection column (first column) is editable.
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
        }
    }

    /**
     * Returns the class of the column's data. The first column is of type Boolean to render checkboxes.
     *
     * @param columnIndex the column index
     * @return the class of the column's data
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : String.class;
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

    /**
     * Updates the list of jobs and resets the selection state.
     *
     * @param jobs the new list of job records
     */
    public void setJobs(List<JobRecord> jobs) {
        this.jobs = jobs != null ? jobs : new ArrayList<>();
        this.selected = new ArrayList<>(this.jobs.size());
        for (int i = 0; i < this.jobs.size(); i++) {
            selected.add(false);
        }
        fireTableDataChanged();
    }

    /**
     * Converts a salary value to a formatted string in dollar amount.
     * If the salary is less than or equal to 0, returns "N/A".
     *
     * @param salary the salary to convert
     * @return the salary in dollar amount
     */
    protected String convertSalary(double salary) {
        if (salary <= 0) {
            return "N/A";
        }
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(0);
        formatter.setGroupingUsed(true);
        return formatter.format(salary);
    }
}
