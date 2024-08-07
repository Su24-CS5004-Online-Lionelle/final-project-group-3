# GUI Testing History

## Table of Contents
1. [Functionality](#functionality)
    - [Filtering Jobs](#filtering-jobs)
    - [Viewing Job Descriptions](#viewing-job-description)
    - [Resetting Filters](#resetting-filters)
    - [Saving Jobs](#saving-jobs)
    - [Removing Jobs](#removing-jobs)
    - [Exporting Jobs to File](#exporting-jobs-to-file)
      - [Error Handling](#error-handling)
2. [Design](#design)
   - [Layout and Components](#layout-and-components)

## Functionality

### Filtering Jobs

1. Search bar should filter jobs by matching keywords in title and description.

   <img src="screenshots/test-search-bar.png" alt="Testing search bar" width="600">


2. Country dropdown values should show relevant countries.

   <img src="screenshots/country-dropdown.png" alt="Country dropdown" width="200">


3. Choosing "GB" from the Country dropdown and clicking the "Apply" button afterward should filter jobs available in the specified country.

   <img src="screenshots/test-country-filter.png" alt="Testing country filter" width="600">


3. Category dropdown values should show relevant job categories.

   <img src="screenshots/category-dropdown.png" alt="Category dropdown" width="200">


4. Choosing "Sales jobs" from the Category dropdown and clicking the "Apply" button afterward should filter jobs by the specified category.

   <img src="screenshots/test-category-filter.png" alt="Testing category filter" width="600">


5. Entered keywords in the company filter and clicking the "Apply" button afterward should filter jobs posted by company names containing the specified keywords.
  
   <img src="screenshots/test-company-filter.png" alt="Testing company filter" width="600">


6. Entering a salary range and clicking the "Apply" button afterward should filter jobs by the specified salary range.
   
   <img src="screenshots/test-salary-range.png" alt="Testing salary range filter" width="600">


7. Entering a minimum salary and clicking the "Apply" button afterward should display jobs with salaries equal to or higher than the specified salary.

   <img src="screenshots/test-min-salary.png" alt="Testing minimum salary filter" width="600">


8. Entering a maximum salary and clicking the "Apply" button afterward should display jobs with salaries equal to or lower than the specified salary.

   <img src="screenshots/test-max-salary.png" alt="Testing maximum salary filter" width="600">


9. Entering multiple filters should filter down jobs.

   <img src="screenshots/test-multiple-filters.png" alt="Testing multiple filters" width="600">


### Viewing Job Description
1. If the user double-clicks on a job, a window displaying the job details should pop up.
   
   <img src="screenshots/popup-description.png" alt="Testing job description pop up window" width="600">


### Resetting Filters

1. Clicking the "Reset" button should clear the filters and display the initially loaded jobs.
   
   <img src="screenshots/test-reset-button.png" alt="Testing reset button" width="600">


### Saving Jobs

1. Users should be able to save the jobs by selecting the jobs and clicking "Add to Saved Jobs" button

   <img src="screenshots/test-select-jobs.png" alt="Selecting jobs to save" width="600">


2. If the "Show Saved Jobs" button is clicked, a new window should open up, displaying the saved jobs.

   <img src="screenshots/test-saved-jobs.png" alt="Display saved jobs" width="600">


### Removing Jobs

1. Users should be able to remove specific jobs from the list of saved jobs by selecting the jobs to remove and clicking the "Remove Selected" button.
   
   <img src="screenshots/test-remove-jobs.png" alt="Removing jobs from the list of saved jobs" width="600">



### Exporting Jobs to File

1. Users should be able to choose the directory to save the file in by clicking "Export as CSV" or "Export as TXT" buttons.

   <img src="screenshots/file-chooser.png" alt="Testing file chooser" width="300">


2. Users should be able to see the saved TXT or CSV file in the chosen directory after hitting save.

   <img src="screenshots/show-saved-files.png" alt="Display saved files" width="300">


_Sample of TXT output:_

   <img src="screenshots/sample-output-txt.png" alt="Sample TXT output" width="600">

_Sample of CSV output:_

   <img src="screenshots/sample-output-csv.png" alt="Sample CSV output" width="600">


#### Error Handling

If the user types in an invalid file extension when exporting the file, the view should display an error message.

   <img src="screenshots/error-invalid-file-extension.png" alt="Error message for invalid file extension" width="300">

**Note**: Any file extensions besides ".txt" or ".csv" are considered invalid, including no extension.

## Design

### Layout and Components

1. The UI should be easy to navigate and the elements should be padded to avoid crowding.
2. The buttons should be colored. 
3. The section header fonts should be large enough to separate the different panels. 

   <img src="screenshots/gui-main-window.png" alt="GUI Main Window" width="600">

   <img src="screenshots/gui-saved-jobs-window.png" alt="GUI Saved Jobs Window" width="600">


