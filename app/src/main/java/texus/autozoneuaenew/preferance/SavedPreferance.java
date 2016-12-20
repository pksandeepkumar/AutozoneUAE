package texus.autozoneuaenew.preferance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author Sandeep Kumar
 *
 */
public class SavedPreferance {

	private static final String ALREADY_LOADED = "AlreadyLoaded";
	private static final String USER_NAME = "user_name";
	private static final String USER_EMAIL = "user_email";
	private static final String SCREEN_WIDTH = "screen_width";
	private static final String SCREEN_HEIGHT = "screen_height";


	public static void setScreenWidth(Context context, int width) {
		SharedPreferences.Editor edit = getPreferance(context).edit();
		edit.putInt(SCREEN_WIDTH, width);
		edit.commit();
	}
	public static int getScreenWidth(Context context) {
		return getPreferance(context).getInt(SCREEN_WIDTH, 0);
	}

	public static void setScreenHeight(Context context, int height) {
		SharedPreferences.Editor edit = getPreferance(context).edit();
		edit.putInt(SCREEN_HEIGHT, height);
		edit.commit();
	}
	public static int getScreenHeight(Context context) {
		return getPreferance(context).getInt(SCREEN_HEIGHT, 0);
	}

	public static void setAlreadyLoaded(Context context, boolean status) {
		SharedPreferences.Editor edit = getPreferance(context).edit();
		edit.putBoolean(ALREADY_LOADED, status);
		edit.commit();
	}
	public static boolean getAlreadyLoaded(Context context) {
		return getPreferance(context).getBoolean(ALREADY_LOADED, false);
//		return false;
	}

	public static void setUserName(Context context, String userName) {
		SharedPreferences.Editor edit = getPreferance(context).edit();
		edit.putString(USER_NAME, userName);
		edit.commit();
	}
	public static String getUserName(Context context) {
		return getPreferance(context).getString(USER_NAME, "");
	}

	public static void setEmailID(Context context, String userName) {
		SharedPreferences.Editor edit = getPreferance(context).edit();
		edit.putString(USER_EMAIL, userName);
		edit.commit();
	}
	public static String getEmailID(Context context) {
		return getPreferance(context).getString(USER_EMAIL, "");
	}
	
	private static SharedPreferences getPreferance(Context context) {
		SharedPreferences preferences = android.preference.PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences;
	}
}
