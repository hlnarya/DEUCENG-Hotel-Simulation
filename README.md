# DEUCENG-Hotel-Simulation
Project:  DEUCENG Hotel Simulation
The aim of the project is to develop a simple Hotel Management System. 


General Information
The software helps the staff to manage all activities in hotel DEUCENG. It is used to manage the records of employees, customers, the states of the rooms, and reservation operations.


Information on Hotel Management Structures:

Hotel
The information about the hotel, such as name, foundation date, address, phone number, and the number of stars will be managed by the software. 

 
Room
Hotel DEUCENG is small with few rooms (max. 30). It has various types of rooms; including regular, deluxe, and suite. Rooms may or may not have some properties such as air-conditioning and a balcony. Each room type has different prices for each day. 

 
Staff
The hotel staff consists of maximum 50 employees to fulfill different tasks. There are 3 types of staff; administrator, receptionist and housekeeper. There must be at least one employee for each type to run the hotel. The information about the staff, such as name, birth date, gender, address, phone number, job, and salary will be managed by the software.

 
Customer
The software is used to manage the records of customers, including name, contact address, and phone number.

 
Reservation 
It keeps the reservation details of the customers such as customer-id, room-id, date of arrival, and date of departure. It is expected to make a reservation between 01.01.2024 and 31.12.2024. 

In addition to the aforementioned data; address (text, district, city), phone (country code, city code, number), and date (day, month, year) information will be stored in a structured format.    


System Operations
The software will take the commands from "commands.txt" file. All ID attributes (i.e., room-id, employee-id, customer-id) must be automatically assigned by starting from 1.

When a reservation request is given, if the request is met, the reservation is approved; otherwise, it will be ignored. 

In the search operation, the program will find and print the customer names that correspond to a given pattern. The pattern can contain only one star (*) symbol, or one-or-more question mark (?) symbol, but not both of them. For example: Zey* or Zey???. 

While the symbol (?)  corresponds to only one letter, the symbol (*) corresponds to zero or more letters. 


Statistics
 
The software will display the following statistics:
•	The most reserved room 
•	The best customer in terms of the duration of stay
•	The net profit over the year   
Profit = Income  -  12 * SalaryExpenses  -  12 * ConstantExpenses
Constant Expense: 10,000 TL per month  
•	Monthly occupancy rate of the hotel


Simulation of Customer Satisfaction
 
For each day; a housekeeper can fully clean only 3 rooms. 

DailyCustomerSatisfaction = 3 / NumberOfCustomers 

Daily customer satisfaction must be equal to or less than 100%.


Commands
 
addRoom;number-of-rooms;type;aircondition;balcony;price
addEmployee;name;surname;gender;birthdate;addresstext;district;city;phone;job;salary
addCustomer;name;surname;gender;birthdate;addresstext;district;city;phone
addReservation;customer-id;room-id;start-date;end-date

deleteEmployee;employee-id

listRooms
listEmployees
listCustomers
listReservations

searchCustomer;pattern
searchRoom;start-date;end-date

statistics
simulation;start-date;end-date


Sample Screens
 
addRoom;2;regular;true;false;1000
addRoom;1;deluxe;true;true;2000
addRoom;1;suite;true;true;4000

listRooms
   Room #1  regular  aircondition  no-balcony  1000TL
   Room #2  regular  aircondition  no-balcony  1000TL
   Room #3  deluxe   aircondition  balcony     2000TL
   Room #4  suite    aircondition  balcony     4000TL

addEmployee;Ali;Arslan;male;5.5.1977;120. Sokak No:12;Bornova;Izmir;+90234445566;admin;16000
addEmployee;Adem;Fidan;male;29.5.1988;Ada Cad. No:10;Buca;Izmir;+902326667788;receptionist;8000
addEmployee;Burcu;Kaya;female;15.6.1980;Yaka Cad. No:7;Urla;Izmir;+902321112233;housekeeper;6000

listEmployees
   Employee #1  Ali Arslan    male      5.5.1977     admin  
   Employee #2  Adem Fidan    male      29.5.1988    receptionist   
   Employee #3  Burcu Kaya    female    15.6.1980    housekeeper  

addCustomer;Zeynep;Ak;female;15.6.1980;Ata Cad. No:2;Urla;Izmir;+902321112233
addCustomer;Cem;Kara;male;5.12.2000;150. Sokak No:7 D:3;Taksim;Istanbul;+902125556677
addCustomer;Zerrin;Dal;female;12.7.1995;Demir Cad. No:3;Mamak;Ankara;+903123332211

listCustomers
   Customer #1  Zeynep Ak    female     15.6.1980    Izmir     +90 (232) 1112233
   Customer #2  Cem Kara   	male       5.12.2000    Istanbul  +90 (212) 5556677  
   Customer #3  Zerrin Dal   female     12.7.1995    Ankara    +90 (312) 3332211

searchCustomer;Ze*
   Customer #1  Zeynep Ak    female     15.6.1980    Izmir     +90 (232) 1112233
   Customer #3  Zerrin Dal   female     12.7.1995    Ankara    +90 (312) 3332211

searchRoom;25.4.2024;30.4.2024
   Room #1  regular  aircondition  no-balcony  1000TL
   Room #2  regular  aircondition  no-balcony  1000TL
   Room #3  deluxe   aircondition  balcony     2000TL
   Room #4  suite    aircondition  balcony     4000TL

addReservation;3;1;25.4.2024;30.4.2024
addReservation;1;2;20.4.2024;30.4.2024
addReservation;2;3;28.4.2024;5.5.2024

addCustomer;Hakan;Yazar;male;20.8.1968;Bulut Cad. No:1;Alsancak;Izmir;+902321234321

searchRoom;10.4.2024;10.9.2024
   Room #4  suite    aircondition  balcony     4000TL

addReservation;4;4;10.4.2024;10.9.2024

listReservations
   Room #1   Zerrin Dal    25.4.2024      30.4.2024 
   Room #2   Zeynep Ak     20.4.2024      30.4.2024
   Room #3   Cem Kara      28.4.2024       5.5.2024
   Room #4   Hakan Yazar   10.4.2024      10.9.2024


statistics

   1.The most reserved room = Room #4
 
   2.The best customer = Hakan Yazar    153 days

   3.Income = 5,000 + 10,000 + 14,000 + 612,000 = 641,000 
     Salary = 360,000   
     Constant expenses = 120,000   
     Profit = 641,000 - 360,000 - 120,000 = 161,000
 
   4.Monhtly occupancy rate 
     1      2      3       4       5       6      7      8       9      10      11      12 
     0%     0%     0%     33%     28%     25%    25%    25%     8%      0%      0%      0%


simulation;25.4.2024;2.5.2024

Day         :      25       26       27       28       29       30        1        2
Customer    :       3        3        3        4        4        2        2        2  
Satisfaction:    100%     100%     100%      75%      75%     100%     100%     100%
Average Satisfaction = 93.75%


simulation;10.10.2024;12.10.2024

Day         :      10       11       12
Customer    :       0        0        0  
Satisfaction:    100%     100%     100%
Average Satisfaction = 100%
 
