package texus.autozoneuae.datamodels;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import texus.autozoneuae.db.Databases;
import texus.autozoneuae.json.JsonParserBase;
import texus.autozoneuae.utility.LOG;

/**
 * Created by sandeep on 03/07/16.
 */
public class Product implements Parcelable{

    public static final String TABLE_NAME = "TableProduct";

    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String CAT_ID = "cat_id";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE  " + TABLE_NAME
            + " ( " + "_id" + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + PRODUCT_ID + " INTEGER , "
            + CAT_ID + " INTEGER , "
            + PRODUCT_NAME + " TEXT )";

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

    public static void insertObjects(Databases db, ArrayList<Product> objects, int cat_id ) {
        if( objects == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        for( Product product : objects) {
            if( null == product ) continue;
            ContentValues cv = new ContentValues();
            cv.put(CAT_ID, cat_id);
            cv.put(PRODUCT_ID, product.product_id);
            cv.put(PRODUCT_NAME, product.product_name);
            sqld.insert(TABLE_NAME, null,cv);
        }
        sqld.close();
    }

    public static ArrayList<Product> getAllProductsUnderACategory( Databases db, int cat_id) {
        ArrayList<Product> objects = new ArrayList<Product>();
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " WHERE " + CAT_ID
                + " = " + cat_id  ;
        LOG.log("Query:", "Query:" + query);
        Cursor c = dbRead.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                objects.add(getAnObjectFromCursor(c));
            } while ( c.moveToNext()) ;
        }
        c.close();
        dbRead.close();
        return objects;
    }

    public static ArrayList<Product> searchProduct( Databases db, String keyword) {
        ArrayList<Product> objects = new ArrayList<Product>();
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " WHERE " + PRODUCT_NAME
                + " LIKE '%" + keyword + "%'"  ;
        LOG.log("Query:", "Query:" + query);
        Cursor c = dbRead.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                objects.add(getAnObjectFromCursor(c));
            } while ( c.moveToNext()) ;
        }
        c.close();
        dbRead.close();
        return objects;
    }

    public static Product getAnObjectFromCursor( Cursor c ) {
        Product instance = null;
        if( c != null) {
            instance = new Product();
            instance.product_id = c.getInt(c.getColumnIndex(PRODUCT_ID));
            instance.product_name = c.getString(c.getColumnIndex(PRODUCT_NAME));
        } else {
            LOG.log("getAnObjectFromCursor:", "getAnObjectFromCursor Cursor is null");
        }
        return instance;
    }

    public static boolean deleteTable(Databases db) {
        try {
            SQLiteDatabase sql = db.getWritableDatabase();
            String query = "DELETE from " + TABLE_NAME;
            LOG.log("Query:", "Query:" + query);
            sql.execSQL(query);
            sql.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
