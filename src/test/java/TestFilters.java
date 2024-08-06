import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jobplanner.model.Filters;
import jobplanner.model.models.IJobPostModel.JobRecord;
import jobplanner.model.models.IJobPostModel.Company;
import jobplanner.model.models.IJobPostModel.Location;
import jobplanner.model.models.IJobPostModel.Category;
import jobplanner.model.types.JobCategory;

public class TestFilters {

    private Filters filters;
    private List<JobRecord> sampleJobs;

    @BeforeEach
    public void setUp() {
        filters = new Filters();
        sampleJobs = List.of(
                new JobRecord("Software Engineer", "Description",
                        new Company("Company A"), new Location("City A", List.of("Country", "State", "County", "City A")),
                        60000, 80000, "full_time", "2023-01-01T10:15:30", "http://example.com", "adref",
                        new Category("it-jobs", "IT Jobs"), 40.7128, -74.0060, "1", "true"),
                new JobRecord("Data Scientist", "Description",
                        new Company("Company B"), new Location("City B", List.of("Country", "State", "County", "City B")),
                        70000, 90000, "full_time", "2023-02-01T10:15:30", "http://example.com", "adref",
                        new Category("data-jobs", "Data Jobs"), 34.0522, -118.2437, "2", "true"),
                new JobRecord("Product Manager", "Description",
                        new Company("Company C"), new Location("City C", List.of("Country", "State", "County", "City C")),
                        80000, 100000, "part_time", "2024-08-04T10:15:30", "http://example.com", "adref",
                        new Category("product-jobs", "Product Management Jobs"), 37.7749, -122.4194, "3", "true")
        );
    }

    @Test
    public void testApplyFilters() {
        Predicate<JobRecord> byCompany = filters.byCompany("Company A");
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byCompany));
        assertEquals(1, filteredJobs.size());
        assertEquals("Software Engineer", filteredJobs.get(0).title());
    }

    @Test
    public void testByCountry() {
        Predicate<JobRecord> byCountry = filters.byCountry("Country");
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byCountry));
        assertEquals(3, filteredJobs.size());
    }

    @Test
    public void testByCategory() {
        Predicate<JobRecord> byCategory = filters.byCategory(JobCategory.IT);
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byCategory));

        // in this case, Software Engineer record has "it-jobs" tag
        assertEquals(1, filteredJobs.size());
        assertEquals("Software Engineer", filteredJobs.get(0).title());
    }


    @Test
    public void testFilterByCompany() {
        List<JobRecord> filteredJobs = filters.filterByCompany(sampleJobs, "Company B");

        // should have the Data Scientist record
        assertEquals(1, filteredJobs.size());
        assertEquals("Data Scientist", filteredJobs.get(0).title());
    }

    @Test
    public void testByCompany() {
        Predicate<JobRecord> byCompany = filters.byCompany("Company C");
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byCompany));

        // should have the Product Manager record
        assertEquals(1, filteredJobs.size());
        assertEquals("Product Manager", filteredJobs.get(0).title());
    }

    @Test
    public void testBySalaryRange() {
        Predicate<JobRecord> bySalaryRange = filters.bySalaryRange(50000, 85000);
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(bySalaryRange));

        // should have the Software Engineer record
        assertEquals(1, filteredJobs.size());
        assertEquals("Software Engineer", filteredJobs.get(0).title());
    }

    @Test
    public void testByRoleType() {
        Predicate<JobRecord> byRoleType = filters.byRoleType(List.of("full_time"));
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byRoleType));

        // should have the Software Engineer and Data Scientist records, both full_time
        assertEquals(2, filteredJobs.size());
        assertEquals("Software Engineer", filteredJobs.get(0).title());
        assertEquals("Data Scientist", filteredJobs.get(1).title());
    }

    @Test
    public void testByDatePosted() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 2, 1);
        Predicate<JobRecord> byDatePosted = filters.byDatePosted(startDate, endDate);
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byDatePosted));

        // should have the Software Engineer and Data Scientist records
        assertEquals(2, filteredJobs.size());
        assertEquals("Software Engineer", filteredJobs.get(0).title());
        assertEquals("Data Scientist", filteredJobs.get(1).title());
    }

    @Test
    public void testByRelativeDateFilterPastWeek() {
        Predicate<JobRecord> byRelativeDateFilter = filters.byRelativeDateFilter("Past week");

        // should have the Product Manager record, which was created in 2024-08-04T10:15:30
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byRelativeDateFilter));
        assertEquals(1, filteredJobs.size());
        assertEquals("Product Manager", filteredJobs.get(0).title());
    }

    @Test
    public void testByRelativeDateFilterPastMonth() {
        Predicate<JobRecord> byRelativeDateFilter = filters.byRelativeDateFilter("Past month");
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byRelativeDateFilter));

        // should have the Product Manager record, which was created in 2024-08-04T10:15:30
        assertEquals(1, filteredJobs.size());
        assertEquals("Product Manager", filteredJobs.get(0).title());
    }

    @Test
    public void testByRelativeDateFilterToday() {
        Predicate<JobRecord> byRelativeDateFilter = filters.byRelativeDateFilter("Today");
        List<JobRecord> filteredJobs = filters.applyFilters(sampleJobs, List.of(byRelativeDateFilter));
        assertEquals(0, filteredJobs.size());
    }
}
