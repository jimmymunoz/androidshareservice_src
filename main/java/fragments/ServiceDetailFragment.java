package fragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ikbal_jimmy.shareservices.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceDetailFragment extends android.app.Fragment {


    public ServiceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_service_detail, container, false);
        rootView.findViewById(R.id.button_tmp_navigation).
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.Fragment fragment = new PaymentFragment();

                    Bundle args = new Bundle();
                    fragment.setArguments(args);
                    //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
            });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_detail, container, false);
    }

}
