package jobplanner;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;
import picocli.CommandLine.ParameterException;

import java.util.Map;
import java.util.HashMap;

import jobplanner.controller.ActionHandler;
import jobplanner.model.formatters.DataFormatter;
import jobplanner.model.formatters.Formats;
import jobplanner.model.models.JobPostModel;
import jobplanner.view.JobBoardGUI;

@Command(name = "jobplanner", subcommands = { JobPlannerSearch.class, JobPlannerList.class, JobPlannerGUI.class,
        CommandLine.HelpCommand.class }, version = "jobplanner 1.0", description = "Search and save jobs.")
public class JobPlannerApp implements Runnable {

    @Spec
    CommandSpec spec;

    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new JobPlannerApp()).execute(args);
        System.exit(exitCode);
    }
}

@Command(name = "search", description = "Search for job postings.")
class JobPlannerSearch implements Runnable {

    @Option(names = {"--country"}, description = "The country to search in.")
    String country = "us";

    @Option(names = { "-k", "--keyword" }, description = "The keyword to search for.")
    String[] keyword;

    @Option(names = { "-l", "--location" }, description = "The location to search in.")
    String location;

    @Option(names = { "-s", "--salary-min" }, description = "The minimum salary to search for.")
    String salaryMin;

    @Option(names = {"-m", "--salary-max"}, description = "The maximum salary to search for.")
    String salaryMax;

    @Option(names = { "-c", "--category" }, description = "The category to search in.")
    String category;

    @Option(names = { "-f", "--format" }, description = "The format to display the job postings in.")
    Formats format = Formats.PRETTY;

    @Override
    public void run() {
        Map<String, String> searchParams = new HashMap<>();

        if (keyword != null) {
            searchParams.put("what", String.join(" ", keyword));
        }
        if (location != null) {
            searchParams.put("where", location);
        }
        if (salaryMin != null) {
            searchParams.put("salary_min", salaryMin);
        }
        if (salaryMax != null) {
            searchParams.put("salary_max", salaryMax);
        }
        if (category != null) {
            searchParams.put("category", category);
        }

        ActionHandler handler = new ActionHandler(JobPostModel.getInstance());
        DataFormatter.write(handler.search(country, searchParams), format, System.out);
    }
}

@Command(name = "list", description = "List saved job postings.")
class JobPlannerList implements Runnable {

    @Option(names = { "-f", "--format" }, description = "The format to display the job postings in.")
    Formats format = Formats.PRETTY;

    @Override
    public void run() {
        ActionHandler handler = new ActionHandler(JobPostModel.getInstance());
        DataFormatter.write(handler.getSavedJobs(), format, System.out);
    }
}

@Command(name = "gui", description = "Open the GUI.")
class JobPlannerGUI implements Runnable {
    @Override
    public void run() {
        ActionHandler controller = new ActionHandler(JobPostModel.getInstance());
        JobBoardGUI view = new JobBoardGUI(controller);
        view.start();
    }
}