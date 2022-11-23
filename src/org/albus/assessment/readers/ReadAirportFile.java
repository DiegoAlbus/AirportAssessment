package org.albus.assessment.readers;

import org.albus.assessment.models.Airport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadAirportFile {
    File file;

    public ReadAirportFile(File file) {
        this.file = file;
    }

    public Map<String, List<Airport>> retrieveAirportsFromFiles() throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine(); // We skip the header

        Map<String, List<Airport>> airportsList = new HashMap<>();

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);

            int id = Integer.parseInt(data[0]);
            String ident = data[1].replaceAll("\"", "");
            String type = data[2].replaceAll("\"", "");
            String name = data[3].replaceAll("\"", "");
            String latitude_deg = data[4].replaceAll("\"", "");
            String longitude_deg = data[5].replaceAll("\"", "");
            String elevation_ft = data[6].replaceAll("\"", "");
            String continent = data[7].replaceAll("\"", "");
            String iso_country = data[8].replaceAll("\"", "");
            String iso_region = data[9].replaceAll("\"", "");
            String municipality = data[10].replaceAll("\"", "");
            String scheduled_service = data[11].replaceAll("\"", "");
            String gps_code = data[12].replaceAll("\"", "");
            String iata_code = data[13].replaceAll("\"", "");
            String local_code = data[14].replaceAll("\"", "");
            String home_link = data[15].replaceAll("\"", "");
            String wikipedia_link = data[16].replaceAll("\"", "");
            String keywords = data[17].replaceAll("\"", "");

            if (!airportsList.containsKey(iso_country)) {
                airportsList.put(iso_country, new ArrayList<>(List.of(new Airport(id, ident, type, name, latitude_deg, longitude_deg, elevation_ft, continent, iso_country, iso_region, municipality, scheduled_service, gps_code, iata_code, local_code, home_link, wikipedia_link, keywords))));
            } else {
                List<Airport> list = airportsList.get(iso_country);
                list.add(new Airport(id, ident, type, name, latitude_deg, longitude_deg, elevation_ft, continent, iso_country, iso_region, municipality, scheduled_service,gps_code,iata_code,local_code,home_link,wikipedia_link,keywords));
                airportsList.put(iso_country, list);
            }
        }

        br.close();
        fr.close();

        return airportsList;
    }
}
