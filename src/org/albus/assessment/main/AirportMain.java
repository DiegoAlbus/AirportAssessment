package org.albus.assessment.main;

import org.albus.assessment.models.Airport;
import org.albus.assessment.models.Country;
import org.albus.assessment.models.Runway;
import org.albus.assessment.readers.ReadAirportFile;
import org.albus.assessment.readers.ReadCountryFile;
import org.albus.assessment.readers.ReadRunwayFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AirportMain {
    private static File airportsFile = null;
    private static File countriesFile = null;
    private static File runwaysFile = null;
    
    private static final Scanner scanner = new Scanner(System.in);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

    public static void main(String[] args) throws Exception {

        /*
            EXECUTE COMMANDS FROM THE SRC FOLDER - MADE IN JAVA 17
            javac org\albus\assessment\main\AirportMain.java
            java org\albus\assessment\main\AirportMain.java

            Example:
            airportAssessment\src> javac org\albus\assessment\main\AirportMain.java
            airportAssessment\src> java org\albus\assessment\main\AirportMain.java
        */

        if (System.console() != null) {
            airportsFile = new File("../resources/airports.csv");
            countriesFile = new File("../resources/countries.csv");
            runwaysFile = new File("../resources/runways.csv");
        } else {
            // When using your IDE use this pathing and comment the other ones
            airportsFile = new File("resources/airports.csv");
            countriesFile = new File("resources/countries.csv");
            runwaysFile = new File("resources/runways.csv");
        }
        // If a file doesn't exist throws error
        if (!airportsFile.exists()) {
            System.err.println("File airports.csv not found at path " + airportsFile.getAbsolutePath());
            throw new FileNotFoundException();
        }
        if (!countriesFile.exists()) {
            System.err.println("File countries.csv not found at path " + countriesFile.getAbsolutePath());
            throw new FileNotFoundException();
        }
        if (!runwaysFile.exists()) {
            System.err.println("File runways.csv not found at path " + runwaysFile.getAbsolutePath());
            throw new FileNotFoundException();
        }

        // All files found
        System.out.println("Files found: " + airportsFile.getName() + " - " + countriesFile.getName() + " - " + runwaysFile.getName());

        // We read the airports csv file
        System.out.println(sdf.format(new Date()) + " - Loading airports data...");
        ReadAirportFile raf = new ReadAirportFile(airportsFile);
        Map<String, List<Airport>> airportsList = raf.retrieveAirportsFromFiles();

        if (airportsList.isEmpty()){
            System.err.println("The airports file is empty or there are no airports. The program will now quit.");
            return;
        }

        // We read the countries csv file
        System.out.println(sdf.format(new Date()) + " - Loading countries data...");
        ReadCountryFile rcf = new ReadCountryFile(countriesFile);
        Map<String, Country> countriesList = rcf.retrieveCountriesFromFiles();

        if (countriesList.isEmpty()){
            System.err.println("The countries file is empty or there are no countries. The program will now quit.");
            return;
        }

        // We read the runways csv file
        System.out.println(sdf.format(new Date()) + " - Loading runways data...");
        ReadRunwayFile rrf = new ReadRunwayFile(runwaysFile);
        Map<Integer, List<Runway>> runwaysList = rrf.retrieveRunwaysFromFiles();
        
        /*if (runwaysList.isEmpty()){
            System.err.println("The runways file is empty or there are no runways. The program will now quit.");
            return;
        }*/

        System.out.println(sdf.format(new Date()) + " - DEBUG: All data required has been read.");

        // With a decent amount of time and planning I would aim for a cache system or Stream
        // iso_country from airports = code in countries
        // airport_ref from runways = id from airports

        // TreeMap reverseOrder containing country key and num of airports
        // Given a country code or name list runways
        // Retrieve country code/name and get result

        // Retrieve number of airports per country
        TreeMap<Integer, String> airportsPerCountry = highestNumberOfAirports(airportsList);

        // Number of airports to retrieve, in this case is 10 as suggested
        int limit = 10;

        int option = -1;

        do {
            System.out.println("\n=#=#=#=#=# AIRPORT ASSESSMENT #=#=#=#=#=");
            System.out.println("Choose your option:");
            System.out.println("1.- Runways for each airport given a country code or country name.");
            System.out.println("2.- Top " + limit + " countries with highest number of airports.");
            System.out.println("3.- Search by partial country code/name...");
            System.out.println("Any other number outside 1, 2 or 3 to quit...\n");

            boolean exceptionJumped = false;
            try{
                option = scanner.nextInt();
            } catch (InputMismatchException e){
                System.err.println("Only numbers... Try again.");
                scanner.next();
                exceptionJumped = true;
            }

            switch (option) {
                case 1 -> runwaysAirportGivenCodeOrName(countriesList, airportsList, runwaysList);
                case 2 -> topAirports(airportsPerCountry, countriesList, limit);
                case 3 -> searchByInput(countriesList, airportsList);
                default -> {
                    if (!exceptionJumped) {
                        System.out.println("Quitting program...");
                        return;
                    }
                }
            }
        } while (option != 0);

        scanner.close();
    }

    private static void searchByInput(Map<String, Country> countriesList, Map<String, List<Airport>> airportsList) {
        System.out.println("\nInsert country code/name: ");
        String country = scanner.next().toUpperCase();

        System.out.println(sdf.format(new Date()) + " - DEBUG: Partial code or name search INIT\n");

        int index = 1;

        List<Country> countriesFound = new ArrayList<>();
        for (Map.Entry<String, Country> m : countriesList.entrySet()) {
            if (m.getKey().toUpperCase().startsWith(country) || m.getValue().getName().toUpperCase().contains(country)) {
                System.out.println(index++ + " - " + m.getValue().toString());
                countriesFound.add(m.getValue());
            }
        }

        if (countriesFound.isEmpty())
            System.out.println("No similarities found.");
        else {
            System.out.println("\nSelect country by number to show all the airports in the given country...");
            int indexSelected = -1;
            try{
                indexSelected = scanner.nextInt();
            } catch (InputMismatchException e){
                System.err.println("Only numbers allowed...");
                scanner.next();
            }

            if (indexSelected <= 0 || indexSelected > countriesFound.size()) {
                System.err.println("Number is not specified in the list...");
            } else {
                List<Airport> airports = airportsList.get(countriesFound.get(indexSelected - 1).getCode());

                if (airportsList.isEmpty()) {
                    System.out.println(countriesFound.get(indexSelected - 1).getName() + " has no airports.");
                } else {
                    System.out.println("Airports in the selected country (" + countriesFound.get(indexSelected - 1).getName() + ")\n");

                    for (Airport a : airports) {
                        System.out.println(a);
                    }
                }
            }

        }

        System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Partial code or name search FIN");
    }

    private static void runwaysAirportGivenCodeOrName(Map<String, Country> countriesList, Map<String, List<Airport>> airportsList, Map<Integer, List<Runway>> runwaysList) {
        System.out.println("\nInsert country code or country name: ");
        String country = scanner.next();

        System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Runways per airports in a country given the code/name of a country listing INIT ");

        Country c = null;

        if (countriesList.containsKey(country)) {
            // If the key of the map is the code
            c = countriesList.get(country);
        } else {
            // TODO: optimize
            for (Map.Entry<String, Country> m : countriesList.entrySet()) {
                if (m.getValue().getCode().equalsIgnoreCase(country) || m.getValue().getName().equalsIgnoreCase(country)) {
                    c = m.getValue();
                    break;
                }
            }
        }

        if (null == c)
            System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Country " + country + " not found.");
        else {
            System.out.println(sdf.format(new Date()) + " - DEBUG: Country " + country + " found.");
            System.out.println(c);
            List<Airport> countryAirports = airportsList.get(c.getCode());

            if (countryAirports.isEmpty()) {
                System.out.println(c.getName() + " has no airports.");
            } else {
                for (Airport a : countryAirports) {
                    if (runwaysList.containsKey(a.getId())) {
                        System.out.println("\n=#=#=#=#=# Runways from " + a.getName() + " #=#=#=#=#=");
                        System.out.println(runwaysList.get(a.getId()));
                    } else {
                        System.out.println("=#=#=#=#=# The airport " + a.getName() + " has no runways. #=#=#=#=#=");
                    }
                }
            }
        }

        System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Runways per airports in a country given the code/name of a country listing FIN");
    }

    private static void topAirports(TreeMap<Integer, String> airportsPerCountry, Map<String, Country> countriesList, int limit) {

        System.out.println(sdf.format(new Date()) + " - DEBUG: Top airports per country listing INIT\n");

        int i = 1;
        for (Map.Entry<Integer, String> m : airportsPerCountry.entrySet()) {
            System.out.println(i + "# - " + countriesList.get(m.getValue()) + " with " + m.getKey() + " airports.");
            i++;

            if (i > limit) {
                break;
            }
        }

        System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Top airports per country listing FIN");
    }

    private static TreeMap<Integer, String> highestNumberOfAirports(Map<String, List<Airport>>  airportsList) {
        System.out.println(sdf.format(new Date()) + " - DEBUG: Calculating number of airports per country INIT");

        // Since TreeMap orders automatically we reverse it
        TreeMap<Integer, String> tMap = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<String, List<Airport>> a : airportsList.entrySet()) {
            // Should only exist one country with N airports
            tMap.put(a.getValue().size(), a.getKey());
        }

        System.out.println(sdf.format(new Date()) + " - DEBUG: Calculating number of airports per country FIN");
        return tMap;
    }

}