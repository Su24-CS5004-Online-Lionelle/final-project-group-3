package jobplanner.model;

import java.io.OutputStream;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;


/**
 * Interface to the model.
 * 
 */
public interface IJobPostModel {
    /** Persistent database file path */
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
     * @param format the format to write the records in
     * @param out the output stream to write to
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
     * Good spot to get the InputStream from the DATABASE file, and use that stream to build the
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
        // you will want to implement this specific to your type of model - if you use it!
    }


    /**
     * Primary record to pass around between objects. Is immutable and uses Jackson annotations for
     * serialization.
     * 
     * @param title the title of the job
     * @param description the description of the job
     * @param company the company offering the job
     * @param location the location of the job
     * @param salary_min the minimum salary
     * @param salary_max the maximum salary
     * @param contract_time the contract time
     * @param created the date the job was created
     * @param redirect_url the URL to redirect to
     * @param adref the ad reference
     * @param category the category of the job
     * @param latitude the latitude of the job
     * @param longitude the longitude of the job
     * @param id the id of the job
     * @param salary_is_predicted if the salary is predicted
     * 
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JacksonXmlRootElement(localName = "job")
    @JsonPropertyOrder({"title", "description", "company", "location", "salary_min", "salary_max", "contract_time", "created", "redirect_url", "adref", "category", "latitude", "longitude", "id", "salary_is_predicted"})
    record JobRecord(String title, String description, String company, String location, double salary_min, double salary_max, String contract_time, String created, String redirect_url, String adref, JobCategory category, double latitude, double longitude, String id, String salary_is_predicted) {
        // empty
    }
}
