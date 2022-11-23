package org.albus.assessment.readers;

import org.albus.assessment.models.Country;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadCountryFile {
    File file;

    public ReadCountryFile(File file) {
        this.file = file;
    }

    public Map<String, Country> retrieveCountriesFromFiles() throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine(); // We skip the header

        Map<String, Country> countriesList = new HashMap<>();
        int counter = 1;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);

            try{
                counter++;
                int id = Integer.parseInt(data[0]);
                String code = data[1].replaceAll("\"", "");
                String name = data[2].replaceAll("\"", "");
                String continent = data[3].replaceAll("\"", "");
                String wikipediaLink = data[4].replaceAll("\"", "");
                String keywords = data[5];

                countriesList.put(code, new Country(id, code, name, continent, wikipediaLink, keywords));
            } catch (Exception e) {
                System.err.println("Row number " + counter + " - " + e.getClass().getName() + " - " + e.getLocalizedMessage());
            }
        }

        br.close();
        fr.close();

        return countriesList;
    }
}
