# Parking Data Analysis (CIT 594 Project)

This project revolves around using data structures, software design, and parsing tools to analyze large parking data (CSV) files from [OpenDataPhilly](www.opendataphilly.org)
and output interesting answers to:
- Population based on ZIP code
- Total fine per capita 
- Average market value based on ZIP Code
- Average total Livable Area based on ZIP Code
- ZIP Code with the lowest ticket number per capita within the budget specified

# Motivation

The project is aimed at learning how to write software with clean and efficient design, while also developing an understanding of data analysis using proper data structures in Java and analyzing both time and space complexities.

We designed the software based on 5 tier architectural design split into **Data**, **Data Management**, **Logging**, **Processor**, and **UI**.

- The **Data** portion stored all the objects necessary to do computations based on the answers of the questions being asked.
- The **Data Management** portion contained classes to read all the necessary files and pass that information to the **Data** portion.
- The **Logging** portion contained a Singleton design pattern that logs information in a .txt file as to what the program is doing, and if any error occurs.
- The **Processor** portion contains all the classes that relied heavily on computation and processing of information.
- The **UI** portion contains all the print statements, along with the questions being asked, for the user to interact with.

# How to use

After downloading the files, supply the program with 5 run-time arguments: the file format of the parking data, the name of the parking data, and 

# Screenshots

