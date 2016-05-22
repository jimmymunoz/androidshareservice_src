package fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ikbal_jimmy.shareservices.R;


public class PaymentFragment extends android.app.Fragment {


    public PaymentFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        rootView.findViewById(R.id.button_tmp_navigation).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        android.app.Fragment fragment = new ServicesFragment();

                        Bundle args = new Bundle();
                        fragment.setArguments(args);
                        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    }
                });
        Toast.makeText(getActivity(), "Votre Paiment  a été  pris  en compre ", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }


}
