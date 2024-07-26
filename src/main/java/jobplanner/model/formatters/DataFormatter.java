package jobplanner.model.formatters;

import java.io.PrintStream;
import java.io.OutputStream;
import java.util.Collection;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import jobplanner.model.IJobPostModel.JobRecord;

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
     * Pretty print the data in a human readable format.
     * 
     * @param records the records to print
     * @param out the output stream to write to
     */
    private static void prettyPrint(Collection<JobRecord> records, OutputStream out) {
        PrintStream pout = new PrintStream(out); // so i can use println
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
        out.println(record.title());
        out.println("             Description: " + record.description());
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
     * Write the data as CSV.
     * 
     * @param records the records to write
     * @param out the output stream to write to
     */
    private static void writeCSVData(Collection<JobRecord> records, OutputStream out) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema schema = csvMapper.schemaFor(JobRecord.class).withHeader();
            csvMapper.writer(schema).writeValue(out, records);
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
            case JSON:
                writeJsonData(records, out);
                break;
            case CSV:
                writeCSVData(records, out);
                break;
            default:
                prettyPrint(records, out);

        }
    }



}
