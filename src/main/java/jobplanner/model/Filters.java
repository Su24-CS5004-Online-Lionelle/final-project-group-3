package jobplanner.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.model.types.JobCategory;

/**
 * The Filters class provides a set of methods to create filtering conditions
 * (predicates) for job records. These predicates can be used to filter job
 * listings
 * based on various criteria such as country, category, company, salary range,
 * contract time, and date posted.
 */
public class Filters {

    /**
     * Applies a list of predicates to a list of job records.
     * This method iteratively applies each predicate to the list of jobs,
     * filtering out jobs that do not meet the criteria.
     *
     * @param jobs       a list of job records to filter
     * @param predicates a list of predicates (conditions) to apply
     * @return a list of jobs that satisfy all the predicates
     */
    public List<JobRecord> applyFilters(List<JobRecord> jobs, List<Predicate<JobRecord>> predicates) {
        for (Predicate<JobRecord> predicate : predicates) {
            jobs = jobs.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }
        return jobs;
    }

    /**
     * Creates a predicate that filters job records by country.
     *
     * @param country the country to filter by
     * @return a predicate that returns true if the job is located in the specified
     *         country
     */
    public Predicate<JobRecord> byCountry(String country) {
        return job -> job.location() != null && job.location().area() != null && !job.location().area().isEmpty()
                && job.location().area().get(0).equalsIgnoreCase(country);
    }

    /**
     * Creates a predicate that filters job records by category.
     *
     * @param category the job category to filter by
     * @return a predicate that returns true if the job belongs to the specified
     *         category
     */
    public Predicate<JobRecord> byCategory(JobCategory category) {
        return job -> JobCategory.fromTag(job.category().tag()) == category;
    }

    /**
     * Filters job records by company name.
     * This method is a specific implementation that uses the `byCompany` predicate.
     *
     * @param jobs        the list of job records to filter
     * @param companyName the company name to filter by
     * @return a list of jobs from the specified company
     */
    public List<JobRecord> filterByCompany(List<JobRecord> jobs, String companyName) {
        return jobs.stream()
                .filter(job -> job.company().displayName().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    /**
     * Creates a predicate that filters job records by company name.
     * It matches jobs whose company name contains the provided substring,
     * case-insensitively.
     *
     * @param companyName the substring to match against company names
     * @return a predicate that returns true if the job's company name contains the
     *         substring
     */
    public Predicate<JobRecord> byCompany(String companyName) {
        return job -> job.company().displayName().toLowerCase().contains(companyName.toLowerCase());
    }

    /**
     * Creates a predicate that filters job records by salary range.
     *
     * @param minSalary the minimum salary
     * @param maxSalary the maximum salary
     * @return a predicate that returns true if the job's salary falls within the
     *         specified range
     */
    public Predicate<JobRecord> bySalaryRange(double minSalary, double maxSalary) {
        return job -> job.salaryMin() >= minSalary && job.salaryMax() <= maxSalary;
    }

    /**
     * Creates a predicate that filters job records by contract time.
     * This version handles a list of contract times, returning true if any match.
     *
     * @param roleType the list of contract types (e.g., full-time, part-time,
     *                 contract)
     * @return a predicate that returns true if the job's contract time matches any
     *         in the list
     */
    public Predicate<JobRecord> byRoleType(List<String> roleType) {
        return job -> roleType.stream().anyMatch(type -> job.contractTime().equalsIgnoreCase(type));
    }

    /**
     * Creates a predicate that filters job records by the date posted.
     * This predicate returns jobs posted between the specified start and end dates.
     *
     * @param startDate the start date to filter from
     * @param endDate   the end date to filter to
     * @return a predicate that returns true if the job was posted within the
     *         specified date range
     */
    public Predicate<JobRecord> byDatePosted(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return job -> {
            LocalDate jobDate = LocalDateTime.parse(job.created(), formatter).toLocalDate();
            return (jobDate.isEqual(startDate) || jobDate.isAfter(startDate)) 
            && (jobDate.isEqual(endDate) || jobDate.isBefore(endDate));
        };
    }

    /**
     * Creates a predicate that filters job records based on a relative date filter,
     * such as "Past week", "Past month", or "Today".
     * The filtering is done relative to the current date.
     *
     * @param filter The string representation of the relative date filter.
     * @return A predicate that returns true if the job was posted within the
     *         specified time frame.
     */
    public Predicate<JobRecord> byRelativeDateFilter(String filter) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (filter.toLowerCase()) {
            case "past week":
                startDate = now.minusWeeks(1);
                break;
            case "past month":
                startDate = now.minusMonths(1);
                break;
            case "today":
                startDate = now;
                break;
            default:
                return job -> true; // No filtering if the filter is not recognized
        }

        return job -> {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDate jobDate = LocalDateTime.parse(job.created(), formatter).toLocalDate();
            return !jobDate.isBefore(startDate);
        };
    }

}
