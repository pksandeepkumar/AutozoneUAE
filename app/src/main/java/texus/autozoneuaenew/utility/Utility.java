package texus.autozoneuaenew.utility;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import texus.autozoneuaenew.ApplicationClass;


public class Utility {

	
	public static String readFromAssets(String filename, Context context) {
	 	String content = "";
	 	
	 	BufferedReader br = null;
	     try {
	         br = new BufferedReader(new InputStreamReader(context.getAssets().open(filename))); //throwing a FileNotFoundException?
	         String word;
	         while((word=br.readLine()) != null)
	         	content = content  + word;
	     }
	         catch(IOException e) {
	             e.printStackTrace();
	         }
	         finally {
	             try {
	                 br.close(); //stop reading
	             }
	             catch(IOException ex) {
	                 ex.printStackTrace();
	             }
	         }
	 	return content;
    }

	public static int parseInt(String number) {
		int num = 0;
		try {
			num = Integer.parseInt(number);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return num;
	}
	
	public static float parseFloat(String number) {
		float num = 0;
		try {
			num = Float.parseFloat(number);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return num;
	}
	
	public static double parseDouble(String number) {
		double num = 0;
		try {
			num = Double.parseDouble(number);
		} catch (Exception e) {
		}
		return num;
	}
	
	public static long parseLong(String number) {
		long num = 0;
		try {
			num = Long.parseLong(number);
		} catch (Exception e) {
		}
		return num;
	}
	
	public static boolean parseBoolean(String value) {
		boolean boolValue = false;
		try {
			boolValue = Boolean.parseBoolean(value.toLowerCase());
		} catch (Exception e) {
		}
		return boolValue;
	}


    /**
     * Format as rounded value, example- 2.321 => 2.32
     * @param value
     * @return
     */
    public static String formatValue( float  value) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String output = formatter.format(value);
        return  output;
    }
	
	

	
	public static int dpToPx(int dp){
	    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public static int pxToDp(int px){
	    return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

    public static String getRound( float value) {
        return String.format("%.2f", value);
    }

	public static void write(String fileName, String data) {
		try {
			File file = new File(ApplicationClass.INTERNAL_RESPONSE_FOLDER, fileName);
			FileOutputStream fileOutput = new FileOutputStream(file);
			fileOutput.write(data.getBytes());
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getData(String filename) {
		StringBuilder data = new StringBuilder();
		try {
			File file = new File(ApplicationClass.INTERNAL_RESPONSE_FOLDER,filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				data.append(line);
				data.append('\n');
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		Log.d("DATA", "" + data);
		return "" + data;
	}
	
	
	
}
