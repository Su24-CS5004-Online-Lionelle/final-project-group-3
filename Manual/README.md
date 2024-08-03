# Job Planner User Manual

## Table of Contents
1. [Introduction](#introduction)
2. [Using the Application](#using-the-application)
    - [Command Line Interface](#command-line-mode)
    - [Graphical User Interface](#gui-mode)
3. [Features](#features)
    - [Filtering Jobs](#filtering-jobs)
    - [Resetting to default values](#resetting-to-default-values)
    - [Loading back saved jobs](#loading-back-saved-jobs)

## Introduction
Welcome to Job Planner! [Add description]

## Using the Application

### Command Line Mode
To use application in command line mode, follow these steps:
Run the application with the required arguments:
   ```sh
   ```
Example:
   ```sh
   ```

### GUI Mode
To use the application in GUI mode, run the application with the following argument: `-g`
   ```sh
   ```
The GUI window will appear.

[Add screenshot of GUI window]


## Features

### Filtering Jobs
- Enter the domain name and click on the "Search" button.
- The application will fetch and display information about the domain.
- If the domain record information is not present in the current database, it will be added to the database.

![Screenshot of Domain Information Display](./screenshots/3-singleresult.png)

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
