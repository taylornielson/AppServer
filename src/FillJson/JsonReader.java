package FillJson;


import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class JsonReader {

       Gson gson = new Gson();
       Names fName;
       Names mName;
       Names sName;
       Locations locs;

    public JsonReader() {
        try {
            String fnames = new String(Files.readAllBytes(Paths.get("./fnames.json")));
            String mnames = new String(Files.readAllBytes(Paths.get("./mnames.json")));
            String snames = new String(Files.readAllBytes(Paths.get("./snames.json")));
            String locations = new String(Files.readAllBytes(Paths.get("./locations.json")));
            fName = gson.fromJson(fnames, Names.class);
            mName = gson.fromJson(mnames, Names.class);
            sName = gson.fromJson(snames, Names.class);
            locs = gson.fromJson(locations, Locations.class);

        } catch (IOException e) {
            System.out.println("Failed to Read Json");
            return;
        }
    }
       public Location getRandomLoc(){
           Random rand = new Random();
           int random = rand.nextInt(locs.data.size());
           Location tempLoc = locs.data.get(random);
           return tempLoc;
       }
    public String getRandomFatherName(){
        Random rand = new Random();
        int random = rand.nextInt(fName.data.size());
        String tempFirstName = fName.data.get(random);
        return tempFirstName;
    }

    public String getRandomMotherName(){
        Random rand = new Random();
        int random = rand.nextInt(mName.data.size());
        String tempMotherName = mName.data.get(random);
        return tempMotherName;
    }

    public String getRandomSurName(){
        Random rand = new Random();
        int random = rand.nextInt(sName.data.size());
        String tempSurName = sName.data.get(random);
        return tempSurName;
    }





}
