# Final Project for CS 5004 - Job Panner App
## Group 3 Members
Abegail Santos
- GitHub username: @asnts18
- [Link to GitHub profile](https://github.com/asnts18)

Scott Carvalho
- GitHub username: @Scarvy
- [Link to GitHub profile](https://github.com/scarvy)

## The Application: Job Planner

This application is a comprehensive job search and tracking tool designed to streamline your job hunting process. With this application, users can:

- **View Job Details**: Browse through detailed job listings, including job title, company, location, salary, category, and a detailed description. Double-click on a job to read the job description on a new window.

- **Filter Job Listings**: Apply various filters to narrow down job search results based on criteria like country, category, company, salary range, role type, and date posted.

- **Save Jobs**: Easily select jobs from the listings and save them for future reference.

- **Modify List of Saved Jobs**: Modify saved jobs by adding new jobs or removing existings jobs from the list.

- **Export to File**: Export saved list of jobs to a formatted CSV or TXT file for future reference.

- **Track Job Applications**: Keep track of jobs you have applied to by marking them in your saved job lists.

## Design Documents and Manual

- [Manual](Manual/README.md)
- [UML and GUI Design](DesignDocuments/README.md)
- [Proposal](https://docs.google.com/document/d/1YjlAw12svy9zmpYXrEizCwlTQeFE_9PsbCtp4eFsQtQ/edit?usp=sharing)
- [GUI History Testing](GUITestingHistory/README.md)
- [Code Testing](src/main/test/java)

## Instructions to Run the Application

### GUI Mode

To use the application in GUI mode, run the application with the following command: `gui`

```
./gradlew run --args="gui"
```

The GUI window will appear.


   <img src="GUITestingHistory/screenshots/gui-main-window.png" alt="Main GUI Window" width="400">


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
