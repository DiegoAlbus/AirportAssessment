package org.albus.assessment.readers;

import org.albus.assessment.models.Runway;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadRunwayFile {
    File file;

    public ReadRunwayFile(File file) {
        this.file = file;
    }

    public Map<Integer, List<Runway>> retrieveRunwaysFromFiles() throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine(); // We skip the header

        Map<Integer, List<Runway>> runwaysList = new HashMap<>();
        int counter = 1;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);

            try{
                counter++;
                int id = Integer.parseInt(data[0]);
                int airport_ref = Integer.parseInt(data[1]);
                String airport_ident = data[2];
                String length_ft = data[3];
                String width_ft = data[4];
                String surface = data[5];
                String lighted = data[6];
                String closed = data[7];
                String le_ident = data[8];
                String le_latitude_deg = data[9];
                String le_longitude_deg = data[10];
                String le_elevation_ft = data[11];
                String le_heading_degT = data[12];
                String le_displaced_threshold_ft = data[13];
                String he_ident = data[14];
                String he_latitude_deg = data[15];
                String he_longitude_deg = data[16];
                String he_elevation_ft = data[17];
                String he_heading_degT = data[18];
                String he_displaced_threshold_ft = data[19];


                if (!runwaysList.containsKey(airport_ref)) {
                    runwaysList.put(airport_ref, new ArrayList<>(List.of(new Runway(id, airport_ref, airport_ident, length_ft, width_ft, surface, lighted, closed, le_ident, le_latitude_deg, le_longitude_deg, le_elevation_ft, le_heading_degT, le_displaced_threshold_ft, he_ident, he_latitude_deg, he_longitude_deg, he_elevation_ft, he_heading_degT, he_displaced_threshold_ft))));
                } else {
                    List<Runway> list = runwaysList.get(airport_ref);
                    list.add(new Runway(id, airport_ref, airport_ident, length_ft, width_ft, surface, lighted, closed, le_ident, le_latitude_deg, le_longitude_deg, le_elevation_ft, le_heading_degT, le_displaced_threshold_ft, he_ident, he_latitude_deg, he_longitude_deg, he_elevation_ft, he_heading_degT, he_displaced_threshold_ft));
                    runwaysList.put(airport_ref, list);
                }
            } catch (Exception e) {
                System.err.println("Row number " + counter + " - " + e.getClass().getName() + " - " + e.getLocalizedMessage());
            }
        }

        br.close();
        fr.close();

        return runwaysList;
    }
}
