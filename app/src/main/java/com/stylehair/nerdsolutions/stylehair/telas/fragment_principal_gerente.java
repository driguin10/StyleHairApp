package com.stylehair.nerdsolutions.stylehair.telas;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.stylehair.nerdsolutions.stylehair.R;

import java.io.IOException;
import java.util.List;


public class fragment_principal_gerente extends Fragment {





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_principal_gerente, container, false);
        getActivity().setTitle("Bem Vindo");


        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> enderecos = geocoder.getFromLocationName("Rua Voluntarios Adriano Cintra, Vila São Sebastião, Franca, SP", 1);
            if (enderecos.size() > 0) {
                Log.v("tag", "coordenadas " + enderecos.get(0).getLatitude() + ", " + enderecos.get(0).getLongitude());
            }
        }catch (IOException e)
        {

        }




        return view;
    }

}
