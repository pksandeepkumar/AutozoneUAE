package texus.autozoneuae.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import texus.autozoneuae.json.JsonParserBase;


public class CatData implements Parcelable {

    public static final String FILENAME = "GetAllCategories.json";
    public static final String TABLE_NAME = "TableCat";


    public int cat_id = 0;
    public String cat_name = "";

    public ArrayList<Product> products = null;

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cat_id);
        dest.writeString(cat_name);
//        dest.writeByte((byte) (viewed ? 1 : 0));
//        dest.writeByte((byte) (liked ? 1 : 0));
    }

    public CatData() {

    }

    private CatData(Parcel in){
        this.cat_id = in.readInt();
        this.cat_name = in.readString();
//        this.viewed = in.readByte() != 0;
//        this.liked = in.readByte() != 0;

    }

    public static final Creator<CatData> CREATOR = new Creator<CatData>() {

        @Override
        public CatData createFromParcel(Parcel source) {
            return new CatData(source);
        }

        @Override
        public CatData[] newArray(int size) {
            return new CatData[size];
        }
    };

    public static ArrayList<CatData> getParesed( String jsonString) {

        ArrayList<CatData> objects = new ArrayList<CatData>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CatData instance = new CatData();
                instance.cat_id = JsonParserBase.getJsonAttributeValueInt(jsonObject,"cat_id");
                instance.cat_name = JsonParserBase.getJsonAttributeValueString(jsonObject, "cat_name");
                JSONArray productsArray = JsonParserBase.getJsonArra(jsonObject, "products");
                instance.products = Product.getParesed(productsArray);
                objects.add(instance);
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return objects;

    }








}

