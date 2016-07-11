package texus.autozoneuae.datamodels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import texus.autozoneuae.json.JsonParserBase;
import texus.autozoneuae.utility.Utility;


public class SpecData  {

    public static final int SPEC_TYPE_NORMAL = 1;
    public static final int SPEC_TYPE_COLOR = 2;
    public static final String FILE_NAME_START = "Spec";

//    "name": "colors",
//            "value": "2",
//            "colors": [
//    {
//        "color": "#757575"
//    },
//    {
//        "color": "#dd3333"
//    }
//    ]


    public String value = "";
    public String name = "";
    public int type = SPEC_TYPE_NORMAL;
    public ArrayList<String> colors = null;




    public static ArrayList<SpecData> getParesed(String jsonString) {

        ArrayList<SpecData> objects = new ArrayList<SpecData>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject object = jsonArray.getJSONObject(0);
            JSONArray specArray = object.getJSONArray("specs");
            for (int i = 0; i < specArray.length(); i++) {
                JSONObject specObject = specArray.getJSONObject(i);
                SpecData instance = new SpecData();
                objects.add(instance);
                instance.name = JsonParserBase.getJsonAttributeValueString(specObject,"name");
                instance.value = JsonParserBase.getJsonAttributeValueString(specObject, "value");
                if(instance.name.equals("colors")) {
                    int noOfColors = Utility.parseInt(instance.value);
                    JSONArray colorArray = specObject.getJSONArray("colors");
                    if(colorArray != null) {
                        if(colorArray.length() > 0) {
                            instance.colors = new ArrayList<String>();
                            instance.type = SPEC_TYPE_COLOR;
                        }
                        for (int j = 0; j < colorArray.length(); j++) {
                            JSONObject colorObject = colorArray.getJSONObject(j);
                            String color = JsonParserBase.getJsonAttributeValueString(colorObject, "color");
                            instance.colors.add(color);
                        }
                    }
                }
                instance.name = makeFirstLetterCapital(instance.name);
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return objects;

    }

    private static String makeFirstLetterCapital( String input) {
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }








}

