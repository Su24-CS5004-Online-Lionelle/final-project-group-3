package jobplanner.model.formatters;

import java.io.PrintStream;
import java.io.OutputStream;
import java.util.Collection;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import jobplanner.model.models.IJobPostModel.JobRecord;

/**
 * A class to format the data in different ways.
 */
public final class DataFormatter {

    /**
     * Private constructor to prevent instantiation.
     */
    private DataFormatter() {
        // empty
    }

    /**
     * Pretty print the data in a human-readable format.
     * 
     * @param records the records to print
     * @param out the output stream to write to
     */
    private static void prettyPrint(Collection<JobRecord> records, OutputStream out) {
        PrintStream pout = new PrintStream(out);
        for (JobRecord record : records) {
            prettySingle(record, pout);
            pout.println();
        }
    }

    /**
     * Pretty print a single record.
     * 
     * @param record the record to print
     * @param out the output stream to write to
     */
    private static void prettySingle(@Nonnull JobRecord record, @Nonnull PrintStream out) {
        // Extract city and state from the location area list
        String city = record.location().displayName();
        String state = record.location().area().size() > 1 ? record.location().area().get(1) : "";

        out.println("Title: " + record.title());
        out.println("   Description: " + record.description());
        out.println("   Company: " + record.company().displayName());
        out.println("   Location: " + city + ", " + state);
        out.println("   Salary Range: " + record.salaryMin() + " - " + record.salaryMax());
        out.println("   Role Type: " + record.contractTime());
        out.println("   Date Posted: " + record.created());
    }

    /**
     * Write the data as JSON.
     * 
     * @param records the records to write
     * @param out the output stream to write to
     */
    private static void writeJsonData(Collection<JobRecord> records, OutputStream out) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonMapper.writeValue(out, records);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write JSON data", e);
        }
    }

    /**
     * Write the data as CSV using a custom CSV schema builder.
     * 
     * @param records the records to write
     * @param out the output stream to write to
     */
    private static void writeCSVData(Collection<JobRecord> records, OutputStream out) {
        try {
            CsvMapper csvMapper = new CsvMapper();

            // define the schema
            CsvSchema schema = CsvSchema.builder()
                    .addColumn("Title")
                    .addColumn("Company")
                    .addColumn("Location")
                    .addColumn("Salary Min")
                    .addColumn("Salary Max")
                    .addColumn("Role Type")
                    .addColumn("Date Posted")
                    .setUseHeader(true)
                    .build();

            csvMapper.writer(schema).writeValue(out, records.stream().map(record -> {
                return new Object[]{
                        record.title(),
                        record.company().displayName(),
                        record.location().displayName() + ", " + (record.location().area().size() > 1 ? record.location().area().get(1) : ""),
                        record.salaryMin(),
                        record.salaryMax(),
                        record.contractTime(),
                        record.created()
                };
            }).toArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to write CSV data", e);
        }
    }

    /**
     * Write the data in the specified format.
     * 
     * @param records the records to write
     * @param format the format to write the records in
     * @param out the output stream to write to
     */
    public static void write(@Nonnull Collection<JobRecord> records, @Nonnull Formats format,
            @Nonnull OutputStream out) {

        switch (format) {
            case PRETTY:
                prettyPrint(records, out);
                break;
            case JSON:
                writeJsonData(records, out);
                break;
            case CSV:
                writeCSVData(records, out);
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }
}
