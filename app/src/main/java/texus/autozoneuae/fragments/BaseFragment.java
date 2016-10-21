package texus.autozoneuae.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import texus.autozoneuae.R;

/**
 * Created by sandeep on 10/20/2016.
 */

public class BaseFragment extends Fragment {

    public void addFragment( Fragment fragment) {
        Log.e("BaseFragment","addFragment");
        if(fragment == null) return;
        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frFragmentContainer, fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
