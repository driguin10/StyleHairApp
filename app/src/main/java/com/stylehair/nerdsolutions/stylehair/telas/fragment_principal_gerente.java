package com.stylehair.nerdsolutions.stylehair.telas;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.stylehair.nerdsolutions.stylehair.R;

import java.io.IOException;
import java.util.List;


public class fragment_principal_gerente extends Fragment {



CardView btAbrir;
CardView btFechar;
CardView btAlmoco;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_principal_gerente, container, false);
        getActivity().setTitle("Bem Vindo");


        btAbrir= (CardView) view.findViewById(R.id.card_aberto);
        btFechar= (CardView) view.findViewById(R.id.card_almoco);
        btAlmoco= (CardView) view.findViewById(R.id.card_fechado);


        btAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAbrir.setAlpha(.9f);
                btFechar.setAlpha(.4f);
                btAlmoco.setAlpha(.4f);
            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAbrir.setAlpha(.4f);
                btFechar.setAlpha(.9f);
                btAlmoco.setAlpha(.4f);
            }
        });

        btAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAbrir.setAlpha(.4f);
                btFechar.setAlpha(.4f);
                btAlmoco.setAlpha(.9f);
            }
        });





        return view;
    }

}
