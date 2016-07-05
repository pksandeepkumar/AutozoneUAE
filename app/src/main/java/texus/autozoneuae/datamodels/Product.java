package texus.autozoneuae.datamodels;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import texus.autozoneuae.json.JsonParserBase;

/**
 * Created by sandeep on 03/07/16.
 */
public class Product {

    public int product_id = 0;
    public String product_name = "";
    public ArrayList<String> image_urls = null;


    public static ArrayList<Product> getParesed( JSONArray jsonArray) {

        ArrayList<Product> objects = new ArrayList<Product>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Product instance = new Product();
                instance.product_id = JsonParserBase.getJsonAttributeValueInt(jsonObject,"product_id");
                instance.product_name = JsonParserBase.getJsonAttributeValueString(jsonObject, "product_name");
                JSONArray imagesArray = JsonParserBase.getJsonArra(jsonObject, "images");
                instance.image_urls = new ArrayList<String>();
                for( int j = 0 ; j < imagesArray.length(); j++) {
                    JSONObject imageObject = imagesArray.getJSONObject(j);
                    instance.image_urls.add(JsonParserBase.getJsonAttributeValueString(imageObject, "image_url"));
                }
                objects.add(instance);
            }

        } catch ( Exception e) {
            e.printStackTrace();
        }
        return objects;

    }
}
