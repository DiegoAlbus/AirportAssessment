# AirportAssessment
List runways for each airport based on country code or name, top 10 countries with the highest number of airports and retrieving partial input information from CSV files.  

**Option 1**, retrieves the runways of all airports based on a given country name or code.  
**Option 2**, retrieves the top 10 countries with the highest number of airports.  
**Option 3**, retrieves countries if the country code/name provided is similar, then select the country and list all the airports.  

# How to execute
The program was written in Java 17, if you're gonna execute from the command line, execute the following commands from the **src** folder:  
`javac org\albus\assessment\main\AirportMain.java`  
`java org\albus\assessment\main\AirportMain.java`  
  
You can import it to your IDE and execute it from there too.  

Example of execution:  
**1.- Compiling and executing the program**  
![image](https://i.imgur.com/ee4vj8j.png)  
  
**2.- Executing option 1, list all the runways for each airport of Barbados (code BB)**  
![image](https://i.imgur.com/Igr0j59.png)  
  
**3.- Executing option 2, list the top 10 countries with the highest number of airports.**
![image](https://i.imgur.com/HXyuDEr.png)  

**4.- Executing option 3 part 1, insert a partial/fuzzy country code or name as input.**
![image](https://i.imgur.com/lWiI475.png)  
  
**5.- Executing option 3 part 2, sometimes there's multiple occurrences, in this case there's only one. Selecting by number, we list all the airports of the given country.**  
![image](https://i.imgur.com/nzjnQom.png)  
  
**6.- Quitting the program, we put a number outside the range specified.**
![image](https://i.imgur.com/spks2dl.png)  
