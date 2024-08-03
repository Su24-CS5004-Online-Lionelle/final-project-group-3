package jobplanner.model.models;

import java.io.OutputStream;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;

/**
 * Interface to the model.
 * 
 */
public interface IJobPostModel {
    /** Persistent database file path. */
    String DATABASE = "data/jobpostings.json";

    /**
     * Get the jobs as a list.
     * 
     * @return the list of jobs
     */
    List<JobRecord> getJobs();

    /**
     * Writes out the records to the outputstream.
     * 
     * OutputStream could be System.out or a FileOutputStream.
     * 
     * @param records the records to write, could be a single entry.
     * @param format  the format to write the records in
     * @param out     the output stream to write to
     */
    static void writeRecords(List<JobRecord> records, Formats format, OutputStream out) {
        DataFormatter.write(records, format, out);
    }

    /**
     * Gets an instance of the model using the 'default' location.
     * 
     * @return the instance of the model
     */
    static IJobPostModel getInstance() {
        return getInstance(DATABASE);
    }

    /**
     * Gets an instance of the model using the 'default' class.
     * 
     * Good spot to get the InputStream from the DATABASE file, and use that stream
     * to build the
     * model.
     * 
     * From another class this would be called like
     * 
     * <pre>
     * DomainNameModel model = DomainNameModel.getInstance();
     * </pre>
     * 
     * @param database the name of the file to use
     * @return the instance of the model
     */
    static IJobPostModel getInstance(String database) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Primary record to pass around between objects. Is immutable and uses Jackson
     * annotations for
     * serialization.
     * 
     * @param title            the title of the job
     * @param description      the description of the job
     * @param company          the company of the job
     * @param location         the location of the job
     * @param salaryMin        the minimum salary of the job
     * @param salaryMax        the maximum salary of the job
     * @param contractTime     the contract time of the job
     * @param created          the date the job was created
     * @param redirectUrl      the redirect url of the job
     * @param adref            the ad reference of the job
     * @param category         the category of the job
     * @param latitude         the latitude of the job
     * @param longitude        the longitude of the job
     * @param id               the id of the job
     * @param salaryIsPredicted the salary is predicted
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonPropertyOrder({
        "title", "description", "company", "location", "salary_min", "salary_max", 
        "contract_time", "created", "redirect_url", "adref", "category", 
        "latitude", "longitude", "id", "salary_is_predicted"
    })
    public record JobRecord(
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("company") Company company,
        @JsonProperty("location") Location location,
        @JsonProperty("salary_min") double salaryMin,
        @JsonProperty("salary_max") double salaryMax,
        @JsonProperty("contract_time") String contractTime,
        @JsonProperty("created") String created,
        @JsonProperty("redirect_url") String redirectUrl,
        @JsonProperty("adref") String adref,
        @JsonProperty("category") Category category,
        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude,
        @JsonProperty("id") String id,
        @JsonProperty("salary_is_predicted") String salaryIsPredicted
    ) {
        // Empty
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Category(
        @JsonProperty("tag") String tag,
        @JsonProperty("label") String label
    ) {
        // Empty
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Company(
        @JsonProperty("display_name") String displayName
    ) {
        // Empty
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Location(
        @JsonProperty("display_name") String displayName,
        @JsonProperty("area") List<String> area
    ) {
        // Empty
    }
}
