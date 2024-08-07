package jobplanner.view.tablemodels;

import jobplanner.model.models.IJobPostModel.JobRecord;
import java.util.List;

/**
 * JobTableModel is a custom table model for displaying job records in a JTable.
 * It extends AbstractJobTableModel and specifies the columns and data for job records.
 */
public class JobTableModel extends AbstractJobTableModel {

    /** Column names for the job table. */
    private static final String[] COLUMN_NAMES = {"Selected", "Title", "Location", "Description", "Salary", "Category", "Company"};

    /**
     * Constructs a JobTableModel with the given list of jobs.
     * Initializes the selection state for each job.
     *
     * @param jobs the list of job records to display
     */
    public JobTableModel(List<JobRecord> jobs) {
        super(jobs, COLUMN_NAMES);
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
            case 1: return job.title(); // Job title
            case 2: return job.location().displayName(); // Job location
            case 3: return job.description(); // Job description
            case 4: return convertSalary(job.salaryMin()) + " - " + convertSalary(job.salaryMax()); // Salary range
            case 5: return job.category().label(); // Job category
            case 6: return job.company().displayName(); // Company name
            default: return null;
        }
    }
}
