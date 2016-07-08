package texus.autozoneuae.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import texus.autozoneuae.json.JsonParserBase;

/**
 * Created by sandeep on 03/07/16.
 */
public class Product implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product_id);
        dest.writeString(product_name);
        dest.writeList(image_urls);
//        dest.writeByte((byte) (viewed ? 1 : 0));
//        dest.writeByte((byte) (liked ? 1 : 0));
    }

    public Product() {

    }

    private Product(Parcel in){
        this.product_id = in.readInt();
        this.product_name = in.readString();
        this.image_urls = in.readArrayList(null);

//        this.viewed = in.readByte() != 0;
//        this.liked = in.readByte() != 0;

    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {

        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
