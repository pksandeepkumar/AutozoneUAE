package texus.autozoneuaenew.datamodels;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import texus.autozoneuaenew.db.Databases;
import texus.autozoneuaenew.json.JsonParserBase;
import texus.autozoneuaenew.utility.LOG;
import texus.autozoneuaenew.utility.Utility;


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

    public static final String TABLE_NAME = "ProductSpec";

    public static final String VALUE = "value";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String PRODUCT_ID = "product_id";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE  " + TABLE_NAME
            + " ( " + "_id" + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + VALUE + " TEXT , "
            + NAME + " TEXT , "
            + TYPE + " INTEGER , "
            + PRODUCT_ID + " INTEGER )";


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

    public static void insertObjects(Databases db, ArrayList<SpecData> objects, int product_id ) {
        if( objects == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        for( SpecData object : objects) {
            if( null == object ) continue;
            ContentValues cv = new ContentValues();
            cv.put(PRODUCT_ID, product_id);
            cv.put(NAME, object.name);

            if(object.type == SPEC_TYPE_COLOR) {
                for( String color : object.colors) {
                    object.value = object.value + color + ",";
                }
            }
            cv.put(VALUE, object.value);
            cv.put(TYPE, object.type);
            sqld.insert(TABLE_NAME, null,cv);
        }
        sqld.close();
    }

    public static ArrayList<SpecData> getAllSpecsUnderAProduct( Databases db, int product_id) {
        ArrayList<SpecData> objects = new ArrayList<SpecData>();
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " WHERE " + PRODUCT_ID
                + " = " + product_id  ;
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

    public static SpecData getAnObjectFromCursor(Cursor c ) {
        SpecData instance = null;
        if( c != null) {
            instance = new SpecData();
            instance.type = c.getInt(c.getColumnIndex(TYPE));
            instance.name = c.getString(c.getColumnIndex(NAME));
            instance.value = c.getString(c.getColumnIndex(VALUE));
            if(instance.type == SPEC_TYPE_COLOR) {
                String [] split = instance.value.split(",");
                instance.colors = new ArrayList( Arrays.asList(split));
            }
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

