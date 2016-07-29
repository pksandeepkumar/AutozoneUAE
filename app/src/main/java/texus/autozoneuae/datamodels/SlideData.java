package texus.autozoneuae.datamodels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SlideData {

    public static final String FILENAME = "GetSlideData.json";

    public String value = "";

    public static ArrayList<String> getParesed(String jsonString) {

        ArrayList<String> objects = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject urlObject = jsonArray.getJSONObject(i);
                String imageURL = urlObject.getString("image");
                objects.add(imageURL);
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return objects;

    }

}

