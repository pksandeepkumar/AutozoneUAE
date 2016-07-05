package texus.autozoneuae.preferance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author Sandeep Kumar
 *
 */
public class SavedPreferance {

	private static final String ALREADY_LOADED = "AlreadyLoaded";
	
	private static final String SETINGS_SAVED = "dbCreated";
	private static final String OFFLINE = "offline";
	private static final String DEVICE_ID = "device_id";
    private static final String TABLE_ID = "table_id";
    private static final String TABLE_NAME = "table_name";
	private static final String TABLE_TYPE = "table_type";
	private static final String SERVICE_URL = "service_url";
	private static final String LANAGUAGE_ID = "language_id";
	
	private static final String UPDATE_COUNT = "update_count";
	

	public static void setAlreadyLoaded(Context context, boolean status) {
		SharedPreferences.Editor edit = getPreferance(context).edit();
		edit.putBoolean(ALREADY_LOADED, status);
		edit.commit();
	}
	public static boolean getAlreadyLoaded(Context context) {
		return getPreferance(context).getBoolean(ALREADY_LOADED, false);
	}

	
	private static SharedPreferences getPreferance(Context context) {
		SharedPreferences preferences = android.preference.PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences;
	}
}
