# Covid_Health_Center

Two different implementations of an application for the management of a health clinic specialized in Covid-19.
The services provided by the clinic are tests (antibodies, antigens and PCR) and vaccinations (Pfizer, Moderna and J&J). 
The clinic must keep records of all these procedures and those involved: staff (lab technicians, nurses, admin) and patients. 

## Table of contents
* [Simulation specifications](#simulation-specifications)
* [Technologies](#technologies)
* [Setup](#setup)

## Simulation specifications

### Staff specifications:

The administrator of the clinic is the one in charge of the users, appointments and stock management. The admin menu can be accessed without a login password to facilitate the testing of the simulation.

The nurses are the ones to carry out the tests, with a maximum of 5 weekly tests for safety reasons. The lab technicians analyze the tests carried out  by the nurses, with a maximum of 4 weekly analyzed tests for safety too.

### Tests specifications:

The tests can be of any of the types specified in the description. Between two PCR tests there has to be a minimum gap of 15 days and between two antibodies tests, a minimum gap of 6 months.
Antigens and PCR tests can result negative or positive for Covid-19, in which case the patient will have to self isolate at home for 10 days. Antibodies tests show an antibodies level between 0 and 10 and the patient is not required to self isolate.

### Vaccination specification 

When an appointment for a jab is booked, a random vaccine is assigned to the patient from the existing stock. Pfizer and Moderna vaccines require two doses to achieve full immunity, whilst J&J only needs one. 
For those vaccines that need a second dose, there is a minimum gap of 21 days between doses and the nurse carrying out the first jab for a certain patient, will have to carry out the second one too.  

The patients will be divided in two groups based on their priority: group 1 for patients over the age of 65 and group 2 for those under. The system will not allow the user to book a jab appointment for a patient in group 2 if there are any patients in group 1 that haven’t been vaccinated yet.

## Technologies
Project is created with:
* Eclipse IDE version: 2021-03

	
## Setup
To run this project in Eclipse IDE:

```
Download .zip
Run Eclipse IDE
File > import > Existing projects into Workspace
select root directory 
Browse to find the location of the Project
Make sure the Project is checked, then hit Finish.

```
