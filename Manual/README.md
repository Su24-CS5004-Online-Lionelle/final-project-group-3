# Job Planner User Manual

## Table of Contents
- [Job Planner User Manual](#job-planner-user-manual)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Using the Application](#using-the-application)
    - [GUI Mode](#gui-mode)
    - [Command Line Mode](#command-line-mode)
  - [Features](#features)
    - [Filtering Jobs](#filtering-jobs)
      - [Filters](#filters)
    - [Resetting to default values](#resetting-to-default-values)
    - [Saving the list of jobs](#saving-the-list-of-jobs)
    - [Loading back saved jobs](#loading-back-saved-jobs)

## Introduction

Welcome to Job Planner! 

A easy-to-use job search and planner tool. Find jobs from the command-line or from a easy to navigate GUI.

![alt text](<./images/main-screen-with-saved-list.png>)

## Using the Application

### GUI Mode

To use the application in GUI mode, run the application with the following command: `gui`

```
./gradlew run --args="gui"
```

The GUI window will appear.

![alt text](<./images/opening-screen.png>)

### Command Line Mode

To use application in command line mode, follow these steps:

Run the application with the required arguments:
   
   ```
    ./gradlew run --args="<commands>"
   ```

Example - Search for jobs in the command-line:
   
    ```bash
    $ ./gradlew run --args="search -k 'python developer' -l boston -s 50000 -c it-jobs"

    Title: Python Developer
      Description: hOS is an early-stage technology company founded in 2021 by several DataRobot alumni and former executives, including founder and former CEO Jeremy Achin. The company is operating in stealth-mode developing scalable technologies and AI-driven products that will make significant positive change in the world. About the role In this role, you will engineer and develop the backend that will serve 10s of millions of users. There is no legacy codebase which means you don’t need to suffer from old poo…
      Company: hOS
      Location: Boston, Suffolk County, Massachusetts
      Salary Range: 98624.12 - 98624.12
      Role Type: full_time
      Date Posted: 2024-08-01T05:37:28Z

    Title: Sr. Python Developer (Remote)
      Description: Senior Python Developer Remote - Long term contract Apply directly to ldavissyrinx.com U.S. Citizens and those authorized to work in the U.S. are encouraged to apply. We are unable to sponsor at this time. No Corp to Corp. ​ Description We are looking for a senior level python web application developer to build a new Customer Service automation product for a major travel site. This is a green field project and you will on a very small team with major independence. Job Responsibilities Collabora…
      Company: Syrinx
      Location: Boston, Suffolk County, Massachusetts
      Salary Range: 123904.02 - 123904.02
      Role Type: null
      Date Posted: 2024-07-31T10:18:42Z

    ...
    
    Title: Vet Academy-Java or Python Software Development Engineer 2 - U.S. Military and Military Spouse
      Description: Job Description The Oracle Cloud Infrastructure (OCI) team can provide you with the opportunity to collaborate, build, and operate a suite of massive-scale, integrated cloud services in a broadly distributed, multi-tenant cloud environment. OCI is committed to providing the best cloud products that meet the needs of our customers, who are tackling some of the world's biggest opportunities. We offer unique opportunities for smart, hands-on engineers with the expertise and passion to solve diffic…
      Company: Oracle
      Location: Boston, Suffolk County, Massachusetts
      Salary Range: 114116.67 - 114116.67
      Role Type: null
      Date Posted: 2024-08-02T15:30:55Z
    ```

To get further help, run this command with one of the subcommands:

```bash
./gradlew run --args="help <gui|list|search>
```

**Help Message:**

```bash
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

```bash
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

**List Help**:

```bash
Usage: jobplanner list [-c] [-f=<format>] [-o=<output>]
List saved job postings.
  -c, --count             The number of saved jobs.
  -f, --format=<format>   The format to display the job postings in.
  -o, --output=<output>   The output file to write to.
```

## Features

### Filtering Jobs

- Filter jobs using the available filters.
- Hit the "Apply" button to apply changes to the main table.

[Add screenshot of filter panel]

#### Filters

1. **Country**: Filter available jobs based on the country
2. **Category**: Filter available jobs based on the category
[add the rest]

### Resetting to default values

- Hit the reset button at the bottom of the Filters pane to clear or reset to the default values.
- This will display the list of all available jobs in the main table. 

[Add screenshot after hitting reset button]


### Saving the list of jobs

[![alt text](<./images/saved-job-list-many.png>)

- To save the output to a file to a destination:
    1. click the "Choose File..." button
    2. select output file path
    3. name the output file

![alt text](<./images/export-job-list.png>)

Click "save" and the output format should match the examples below:

**Plain text**:

```text
Title: Senior Java and DB2 Developer - AVP
   Description: The Cash Flow module application is undergoing several changes in keeping with State Street’s strategic and client needs. As a result, we are looking for a senior application developer well versed in Web application development using Java, DB2 and Payment Initiation concepts. Knowledge of Payment Initiation systems is required. Additional knowledge of APIs to accounting systems desirable. Ideal candidate will have 10 years of experience developing web applications in an agile SDLC. This role ca…
   Company: State Street
   Location: Norfolk Downs, Norfolk County, Massachusetts
   Salary Range: 142768.88 - 142768.88
   Role Type: full_time
   Date Posted: 2024-06-26T09:45:20Z

Title: Software Developer
   Description: LOCATION:BOSTON, MA Will design, development, and modification of complex systems using Java, Spring Boot, JavaScript, Microservices and React JS. Perform software engineering tasks associated with the analysis, design, and development of cloud-based applications using tools such as Java, Spring and JavaScript. Perform code configurations in Java, Spring Boot and MySQL. Design, code, test, maintenance and debugging of Microservice application using Java 8/J2EE, Java, JSP, Servlet, Spring MVC, R…
   Company: Beacon Hill Staffing Group, LLC
   Location: Boston, Suffolk County, Massachusetts
   Salary Range: 103445.45 - 103445.45
   Role Type: full_time
   Date Posted: 2024-07-04T00:57:15Z

...
```

**CSV:**

```csv
Title,Company,Location,"Salary Min","Salary Max","Role Type","Date Posted"
"Senior Java and DB2 Developer - AVP","State Street","Norfolk Downs, Norfolk County, Massachusetts",142768.88,142768.88,full_time,2024-06-26T09:45:20Z
"Software Developer","Beacon Hill Staffing Group, LLC","Boston, Suffolk County, Massachusetts",103445.45,103445.45,full_time,2024-07-04T00:57:15
...
```

### Loading back saved jobs

Any job added will be stored for future sessions. The next time you open the application your data will be saved!

![alt text](<images/Screenshot 2024-08-06 at 3.07.11 PM.png>)