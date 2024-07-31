package jobplanner;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;
import picocli.CommandLine.ParameterException;

import java.util.Map;
import java.util.HashMap;
import java.io.FileOutputStream;

import jobplanner.controller.ActionHandler;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.JobPostModel;
import jobplanner.model.types.JobQueryParameter;
import jobplanner.view.JobBoardGUI;

/**
 * The main class for the job planner application.
 */
@Command(name = "jobplanner", subcommands = { JobPlannerSearch.class, JobPlannerList.class, JobPlannerGUI.class,
        CommandLine.HelpCommand.class }, version = "jobplanner 1.0", description = "Search and save jobs.")
public class JobPlannerApp implements Runnable {

    // The spec for the command
    @Spec
    CommandSpec spec;

    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    }

    /**
     * The main method for the job planner application.
     * 
     * @param args the command line arguments
     */
    public static void main(String... args) {
        int exitCode = new CommandLine(new JobPlannerApp()).execute(args);
        System.exit(exitCode);
    }
}

/**
 * The search command for the job planner application.
 */
@Command(name = "search", description = "Search for job postings.")
class JobPlannerSearch implements Runnable {

    @Option(names = { "--country" }, description = "The country to search in.")
    String country = "us";

    @Option(names = { "-k", "--keyword" }, description = "The keyword to search for.")
    String[] keyword;

    @Option(names = { "-l", "--location" }, description = "The location to search in.")
    String location;

    @Option(names = { "-s", "--salary-min" }, description = "The minimum salary to search for.")
    String salaryMin;

    @Option(names = { "-m", "--salary-max" }, description = "The maximum salary to search for.")
    String salaryMax;

    @Option(names = { "-c", "--category" }, description = "The category to search in.")
    String category;

    @Option(names = { "-f", "--format" }, description = "The format to display the job postings in.")
    Formats format = Formats.PRETTY;

    @Option(names = { "-d", "--days" }, description = "The maximum days old for the job postings.")
    String days;

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

        ActionHandler handler = new ActionHandler(JobPostModel.getInstance());
        DataFormatter.write(handler.search(country, searchParams), format, System.out);
    }
}

/**
 * The list command for the job planner application.
 */
@Command(name = "list", description = "List saved job postings.")
class JobPlannerList implements Runnable {

    Formats format = Formats.PRETTY;

    @Option(names = { "-f", "--format" }, description = "The format to display the job postings in.")
    void setFormat(String format) {
        this.format = Formats.valueOf(format.toUpperCase()) == null ? Formats.PRETTY
                : Formats.valueOf(format.toUpperCase());
    }

    @Option(names = { "-o", "--output" }, description = "The output file to write to.")
    String output;

    @Option(names = {"-c", "--count"}, description = "The number of saved jobs.")
    boolean count;

    @Override
    public void run() {
        ActionHandler handler = new ActionHandler(JobPostModel.getInstance());
        if (count) {
            System.out.println(handler.countSavedJobs());
            return;
        }

        if (output != null) {
            try {
                DataFormatter.write(handler.getSavedJobs(), format, new FileOutputStream(output));
            } catch (Exception e) {
                System.err.println("Failed to write to file: " + output);
                return;
            }
        } else {
            DataFormatter.write(handler.getSavedJobs(), format, System.out);
        }
    }
}

/**
 * The GUI command for the job planner application.
 */
@Command(name = "gui", description = "Open the GUI.")
class JobPlannerGUI implements Runnable {
    @Override
    public void run() {
        ActionHandler controller = new ActionHandler(JobPostModel.getInstance());
        JobBoardGUI view = new JobBoardGUI(controller);
        view.start();
    }
}