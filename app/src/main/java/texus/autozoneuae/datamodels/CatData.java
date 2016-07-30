package texus.autozoneuae.datamodels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import texus.autozoneuae.db.Databases;
import texus.autozoneuae.json.JsonParserBase;
import texus.autozoneuae.preferance.SavedPreferance;
import texus.autozoneuae.utility.LOG;


public class CatData implements Parcelable {

    public static final String FILENAME = "GetAllCategories.json";

    public static final String TABLE_NAME = "TableCategory";

    public static final String CAT_ID = "cat_id";
    public static final String CAT_NAME = "cat_name";


    public int cat_id = 0;
    public String cat_name = "";

    public ArrayList<Product> products = null;

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE  " + TABLE_NAME
            + " ( " + "_id" + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + CAT_ID + " INTEGER , "
            + CAT_NAME + " TEXT )";


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


    public static void insertObjects(Databases db, ArrayList<CatData> categoryDatas ) {
        if( categoryDatas == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        for( CatData categoryData : categoryDatas) {
            if( null == categoryData ) continue;
            ContentValues cv = new ContentValues();
            cv.put(CAT_ID, categoryData.cat_id);
            cv.put(CAT_NAME, categoryData.cat_name);
            sqld.insert(TABLE_NAME, null,cv);
        }
        sqld.close();
    }

    public static ArrayList<CatData> getAllCategories( Databases db) {
        ArrayList<CatData> objects = new ArrayList<CatData>();
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " ";
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

    public static CatData getAnObjectFromCursor( Cursor c ) {
        CatData instance = null;
        if( c != null) {
            instance = new CatData();
            instance.cat_id = c.getInt(c.getColumnIndex(CAT_ID));
            instance.cat_name = c.getString(c.getColumnIndex(CAT_NAME));
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

    public static void insertDatas(ArrayList<CatData> catDatas, Context context) {
        Databases db = new Databases(context);
        CatData.deleteTable(db);
        Product.deleteTable(db);
        CatData.insertObjects(db, catDatas);
        for( CatData catData :  catDatas) {
            Product.insertObjects(db,catData.products, catData.cat_id);
        }
        db.close();
        SavedPreferance.setAlreadyLoaded(context, true);
    }
}
