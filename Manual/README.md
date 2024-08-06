# Job Planner User Manual

## Table of Contents
- [Job Planner User Manual](#job-planner-user-manual)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Using the Application](#using-the-application)
    - [Command Line Mode](#command-line-mode)
    - [GUI Mode](#gui-mode)
  - [Features](#features)
    - [Filtering Jobs](#filtering-jobs)
      - [Filters](#filters)
    - [Resetting to default values](#resetting-to-default-values)
    - [Saving the list of jobs](#saving-the-list-of-jobs)
    - [Loading back saved jobs](#loading-back-saved-jobs)

## Introduction
Welcome to Job Planner! [Add description]

## Using the Application

### Command Line Mode
To use application in command line mode, follow these steps:

Run the application with the required arguments:
   
   ```
    ./gradlew run --args="<commands>"
   ```

Example:
   
    ```
    ./gradlew run --args="search -k 'python developer' -l boston -s 50000 -c it-jobs"
    ```

### GUI Mode

To use the application in GUI mode, run the application with the following command: `gui`

```
./gradlew run --args="gui"
```

The GUI window will appear.

**Help Message:**

```
Usage: jobplanner [-hV] [COMMAND]
Search and save jobs.
-h, --help      Show this help message and exit.
-V, --version   Print version information and exit.
Commands:
search  Search for job postings.
list    List saved job postings.
gui     Open the GUI.
help    Display help information about the specified command.
```

**Search Help Message:**

```
Usage: jobplanner search [-c=<category>] [--country=<country>] [-d=<days>]
                         [-f=<format>] [-l=<location>] [-m=<salaryMax>]
                         [-s=<salaryMin>] [-k=<keyword>]...
Search for job postings.
  -c, --category=<category> The category to search in.
      --country=<country>   The country to search in.
  -d, --days=<days>         The maximum days old for the job postings.
  -f, --format=<format>     The format to display the job postings in.
  -k, --keyword=<keyword>   The keyword to search for.
  -l, --location=<location> The location to search in.
  -m, --salary-max=<salaryMax>
                            The maximum salary to search for.
  -s, --salary-min=<salaryMin>
                            The minimum salary to search for.
```


## Features

### Filtering Jobs

- Filter jobs using the available filters.
- Hit the "Apply" button to apply changes to the main table.

[Add screenshot of filter panel]

#### Filters
1. **Country**: Filter available jobs based on the country

[Add more filters]


### Resetting to default values
- Hit the reset button at the bottom of the Filters pane to clear or reset to the default values.
- This will display the list of all available jobs in the main table. 

[Add screenshot after hitting reset button]


### Saving the list of jobs

[Add screenshot of Saved Jobs window with marked list of jobs]

- To save the output to a file to a destination:
    1. click the "Choose File..." button
    2. select output file path
    3. name the output file

[Add screenshot of Saved Jobs window with export to file]

### Loading back saved jobs
