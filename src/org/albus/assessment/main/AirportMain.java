package org.albus.assessment.main;

import org.albus.assessment.models.Airport;
import org.albus.assessment.models.Country;
import org.albus.assessment.models.Runway;
import org.albus.assessment.readers.ReadAirportFile;
import org.albus.assessment.readers.ReadCountryFile;
import org.albus.assessment.readers.ReadRunwayFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

        if (null != System.console()) {
            airportsFile = new File("../resources/airports.csv");
            countriesFile = new File("../resources/countries.csv");
            runwaysFile = new File("../resources/runways.csv");
        } else {
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

        if (airportsList.isEmpty()) {
            System.err.println("The airports file is empty or there are no airports. The program will now quit.");
            return;
        }

        // We read the countries csv file
        System.out.println(sdf.format(new Date()) + " - Loading countries data...");
        ReadCountryFile rcf = new ReadCountryFile(countriesFile);
        Map<String, Country> countriesList = rcf.retrieveCountriesFromFiles();

        if (countriesList.isEmpty()) {
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

        // Some Testcases //
        /*
            - No countries, airports or runways
            - Top 10 countries with highest num. of airports but there's not enough countries/airports.
            - Number input when not required.
            - No occurrences for given input (options, partial country code/name, runways).
            - Null when countries not found
            - Empty files, few runways / airports / countries.
        */

        // iso_country from airports = code in countries
        // airport_ref from runways = id from airports

        // Retrieve number of airports per country
        TreeMap<Integer, String> airportsPerCountry = highestNumberOfAirports(airportsList);

        // Number of airports to retrieve, in this case is 10 as suggested
        int limit = 10;

        int option = -1;

        // While the option is in the menu range (switch case) it won't quit.
        do {
            System.out.println("\n=#=#=#=#=# AIRPORT ASSESSMENT #=#=#=#=#=");
            System.out.println("Choose your option:");
            System.out.println("1.- Runways for each airport given a country code or country name.");
            System.out.println("2.- Top " + limit + " countries with highest number of airports.");
            System.out.println("3.- Search by partial country code/name...");
            System.out.println("Any other number outside 1, 2 or 3 to quit...\n");

            boolean exceptionJumped = false;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
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
                        scanner.close();
                        return;
                    }
                }
            }
        } while (true);

    }

    private static void searchByInput(Map<String, Country> countriesList, Map<String, List<Airport>> airportsList) {
        System.out.println("\nInsert country code/name: ");
        String country = scanner.next().toUpperCase();

        System.out.println(sdf.format(new Date()) + " - DEBUG: Partial code or name search INIT\n");

        AtomicInteger index = new AtomicInteger(1);

        List<Country> countriesFound = new ArrayList<>();

        // We filter and print the countries by the code or name ignoring the casing (ex: eS = 'ES' / Spain or 'BD' / BangladESh) among other results.
        countriesList.entrySet().stream().filter(p -> p.getKey().toUpperCase().startsWith(country) || p.getValue().getName().toUpperCase().contains(country)).forEach(
                (entry) -> {
                    System.out.println(index.getAndIncrement() + " - " + entry.getValue().toString());
                    countriesFound.add(entry.getValue());
                }
        );

        if (countriesFound.isEmpty())
            System.out.println("No similarities found.");
        else {
            System.out.println("\nSelect country by number to show all the airports in the given country...");
            int indexSelected = -1;
            try {
                indexSelected = scanner.nextInt();


                if (indexSelected <= 0 || indexSelected > countriesFound.size()) {
                    System.err.println("Number is not specified in the list...");
                } else {
                    List<Airport> airports = airportsList.get(countriesFound.get(indexSelected - 1).getCode());

                    if (airportsList.isEmpty()) {
                        System.out.println(countriesFound.get(indexSelected - 1).getName() + " has no airports.");
                    } else {
                        System.out.println("Airports in the selected country (" + countriesFound.get(indexSelected - 1).getName() + ")\n");

                        // Prints all the runways with no pretty printing (FASTER)
                        // System.out.println(airports);

                        // Prints all the runways with pretty printing (SLOWER)
                        airports.stream().forEach(
                                (entry) -> {
                                    System.out.println("\n=#=#=#=#=# " + entry.getName() + " #=#=#=#=#=");
                                    System.out.println(entry);
                                }
                        );

                    }
                }
            } catch (InputMismatchException e) {
                System.err.println("Only numbers allowed...");
                scanner.next();
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

            Optional<Map.Entry<String, Country>> optionalCountry = countriesList.entrySet().stream().filter(p -> p.getValue().getCode().equalsIgnoreCase(country) || p.getValue().getName().equalsIgnoreCase(country)).findFirst();
            try{
                c = optionalCountry.get().getValue();
            } catch (NoSuchElementException nse) {
                System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Country " + country + " not found.");
            }
        }

        if (c != null) {
            System.out.println(sdf.format(new Date()) + " - DEBUG: Country " + country + " found.");
            System.out.println(c);
            List<Airport> countryAirports = airportsList.get(c.getCode());

            if (null == countryAirports) {
                System.out.println(c.getName() + " has no airports.");
            } else {
                // We filter out airports with no runways for optimization
                List<Airport> air = countryAirports.stream().filter(p -> runwaysList.containsKey(p.getId())).collect(Collectors.toList());

                // Prints all the runways with no pretty printing (FASTER)
                // System.out.println(air);

                // Prints all the runways with pretty printing (SLOWER)
                air.stream().forEach((entry) -> {
                    System.out.println("\n=#=#=#=#=# Runways from " + entry.getName() + " #=#=#=#=#=");
                    System.out.println(runwaysList.get(entry.getId()));
                });
            }
        }

        System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Runways per airports in a country given the code/name of a country listing FIN");
    }

    private static void topAirports(TreeMap<Integer, String> airportsPerCountry, Map<String, Country> countriesList, int limit) {

        System.out.println(sdf.format(new Date()) + " - DEBUG: Top airports per country listing INIT\n");

        AtomicInteger i = new AtomicInteger(1);

        airportsPerCountry.entrySet().stream().limit(10).forEach((entry) -> {
            System.out.println(i.getAndIncrement() + "# - " + countriesList.get(entry.getValue()) + " with " + entry.getKey() + " airports.");
        });

        System.out.println("\n" + sdf.format(new Date()) + " - DEBUG: Top airports per country listing FIN");
    }

    private static TreeMap<Integer, String> highestNumberOfAirports(Map<String, List<Airport>> airportsList) {
        System.out.println(sdf.format(new Date()) + " - DEBUG: Calculating number of airports per country INIT");

        // Since TreeMap orders automatically we reverse it
        TreeMap<Integer, String> tMap = new TreeMap<>(Collections.reverseOrder());

        airportsList.entrySet().stream().forEach((entry) -> {
            tMap.put(entry.getValue().size(), entry.getKey());
        });

        System.out.println(sdf.format(new Date()) + " - DEBUG: Calculating number of airports per country FIN");
        return tMap;
    }

}