# GUI Testing History

## Table of Contents
1. [Functionality](#functionality)
    - [Filtering Jobs](#filtering-jobs)
    - [Resetting Filters](#resetting-filters)
    - [Saving Jobs](#saving-jobs)
    - [Exporting Jobs to File](#exporting-jobs-to-file)
      - [Exporting as TXT](#exporting-as-txt)
      - [Exporting as CSV](#exporting-as-csv)
      - [Error Handling](#error-handling)
2. [Design](#design)
   - [Layout and Components](#layout-and-components)

## Functionality

### Filtering Jobs

1. Search bar should filter jobs by matching keywords in title and description.
2. Company dropdown values should show relevant countries.
3. Choosing "AU" from the Company dropdown and clicking the "Apply" button afterward should filter jobs available in the specified country.
3. Category dropdown values should show relevant job categories.
4. Choosing "IT jobs" from the Category dropdown and clicking the "Apply" button afterward should filter jobs by the specified category.
5. Entered keywords in the company filter and clicking the "Apply" button afterward should filter jobs posted by company names containing the specified keywords.
6. Entering a salary range and clicking the "Apply" button afterward should filter jobs by the specified salary range.
7. Entering a minimum salary and clicking the "Apply" button afterward should display jobs with salaries equal to or higher than the specified salary.
8. Entering a maximum salary and clicking the "Apply" button afterward should display jobs with salaries equal to or lower than the specified salary.
9. Entering multiple filters should filter down jobs.

### Resetting Filters

1. Clicking the "Reset" button should clear the filters and display the initially loaded jobs.

### Saving Jobs

1. Users should be able to mark the jobs by clicking the checkbox. 
2. After selecting jobs and clicking the "Show Saved Jobs" button, a new window called "Saved Jobs" should open up and display the selected jobs. 

### Exporting Jobs to File

Users should be able to choose the directory to save the file in by clicking "Export as CSV" or "Export as TXT" buttons.

#### Exporting as TXT

Users should be able to see the saved TXT file in the chosen directory after hitting save. 

Sample of TXT output: 

#### Exporting as CSV

Users should be able to see the saved CSV file in the chosen directory after hitting save.

Sample of CSV output:

#### Error Handling

If the user types in an invalid file extension when exporting the file, the view should display an error message. 


## Design

### Layout and Components

1. The UI should be easy to navigate and the elements should be padded to avoid crowding.
2. The buttons should be colored. 
3. The section header fonts should be large enough to separate the different panels. 
