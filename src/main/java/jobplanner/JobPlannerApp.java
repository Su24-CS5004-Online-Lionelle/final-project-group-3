package jobplanner;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.util.Map;
import java.util.HashMap;
import java.io.FileOutputStream;

import jobplanner.model.api.JobPostUtil;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.JobPostModel;
import jobplanner.model.models.SavedJobModel;
import jobplanner.model.models.ISavedJobModel;
import jobplanner.model.types.JobQueryParameter;
import jobplanner.controller.JobPlannerController;
import jobplanner.view.JobPlannerGUI;

import javax.swing.*;

/**
 * The main class for the job planner application.
 */
@Command(name = "jobplanner", subcommands = { JobPlannerSearch.class, JobPlannerList.class, JobPlannerGraphUI.class,
        CommandLine.HelpCommand.class }, version = "jobplanner 1.0", 
        description = "Search and save jobs.", mixinStandardHelpOptions = true)
public class JobPlannerApp implements Runnable {

    /** The command spec. */
    @Spec
    private CommandSpec spec;

    @Override
    public void run() {
        new CommandLine(new JobPlannerGraphUI()).execute();
    }

    /**
     * The main method for the job planner application.
     * 
     * @param args the command line arguments
     */
    public static void main(String... args) {
        if (args.length == 0) {
            args = new String[] {"gui"};
        }

        new CommandLine(new JobPlannerApp()).execute(args);
    }
}

/**
 * The search command for the job planner application.
 */
@Command(name = "search", description = "Search for job postings.")
class JobPlannerSearch implements Runnable {

    /** The country to search in. */
    @Option(names = { "--country" }, description = "The country to search in.")
    private String country = "us";

    /** The keyword to search for. */
    @Option(names = { "-k", "--keyword" }, description = "The keyword to search for.")
    private String[] keyword;

    /** The location to search in. */
    @Option(names = { "-l", "--location" }, description = "The location to search in.")
    private String location;

    /** The minimum salary to search for. */
    @Option(names = { "-s", "--salary-min" }, description = "The minimum salary to search for.")
    private String salaryMin;

    /** The maximum salary to search for. */
    @Option(names = { "-m", "--salary-max" }, description = "The maximum salary to search for.")
    private String salaryMax;

    /** The category to search in. */
    @Option(names = { "-c", "--category" }, description = "The category to search in.")
    private String category;

    /** The format to display the job postings in. */
    @Option(names = { "-f", "--format" }, description = "The format to display the job postings in.")
    private Formats format = Formats.PRETTY;

    /** The maximum days old for the job postings. */
    @Option(names = { "-d", "--days" }, description = "The maximum days old for the job postings.")
    private String days;

    @Override
    public void run() {
        Map<String, String> searchParams = new HashMap<>();

        if (keyword != null) {
            searchParams.put(JobQueryParameter.WHAT.toString(), String.join(" ", keyword));
        }
        if (location != null) {
            searchParams.put(JobQueryParameter.WHERE.toString(), location);
        }
        if (salaryMin != null) {
            searchParams.put(JobQueryParameter.SALARY_MIN.toString(), salaryMin);
        }
        if (salaryMax != null) {
            searchParams.put(JobQueryParameter.SALARY_MAX.toString(), salaryMax);
        }
        if (category != null) {
            searchParams.put(JobQueryParameter.CATEGORY.toString(), category);
        }
        if (days != null) {
            searchParams.put(JobQueryParameter.DATE_POSTED.toString(), days);
        }

        JobPostUtil client = new JobPostUtil(System.getenv("ADZUNA_APP_ID"), System.getenv("ADZUNA_APP_KEY"));
        DataFormatter.write(client.getJobPostingList(country, searchParams), format, System.out);
    }
}

/**
 * The list command for the job planner application.
 */
@Command(name = "list", description = "List saved job postings.")
class JobPlannerList implements Runnable {

    /** The format to display the job postings in. */
    private Formats format = Formats.PRETTY;

    /**
     * The output file to write to.
     * 
     * @param format the format to display the job postings in.
     */
    @Option(names = { "-f", "--format" }, description = "The format to display the job postings in.")
    void setFormat(String format) {
        this.format = Formats.valueOf(format.toUpperCase()) == null ? Formats.PRETTY
                : Formats.valueOf(format.toUpperCase());
    }

    /** The output file to write to. */
    @Option(names = { "-o", "--output" }, description = "The output file to write to.")
    private String output;

    /** The number of saved jobs. */
    @Option(names = { "-c", "--count" }, description = "The number of saved jobs.")
    private boolean count;

    @Override
    public void run() {
        ISavedJobModel jobs = SavedJobModel.loadFromJson();

        if (count) {
            System.out.println(SavedJobModel.loadFromJson().count());
            return;
        }

        if (output != null) {
            try {
                DataFormatter.write(jobs.getSavedJobs(), format, new FileOutputStream(output));
            } catch (Exception e) {
                System.err.println("Failed to write to file: " + output);
                return;
            }
        } else {
            DataFormatter.write(jobs.getSavedJobs(), format, System.out);
        }
    }
}

/**
 * The GUI command for the job planner application.
 */
@Command(name = "gui", description = "Open the GUI.")
class JobPlannerGraphUI implements Runnable {

    /** The file to load previously saved jobs from. */
    @Option(names = { "-f", "--file" }, description = "The file to load previously saved jobs from.")
    private String file = "data/savedjobs.json";

    @Override
    public void run() {
        // Load the model from JSON file
        JobPostModel jobs = (JobPostModel) JobPostModel.getInstance("data/jobpostings.json");

        // Load saved jobs from JSON file
        SavedJobModel savedJobs = (SavedJobModel) SavedJobModel.loadFromJson(file);

        // Ensure GUI runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Initialize the GUI components with the loaded model
            JobPlannerGUI view = new JobPlannerGUI(jobs, savedJobs);

            // Initialize the controller with the model and view
            JobPlannerController controller = new JobPlannerController(jobs, savedJobs, view);

            // Start the application
            controller.start();
        });
    }
}
