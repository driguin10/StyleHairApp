package com.stylehair.nerdsolutions.stylehair.telas;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;

import java.io.IOException;
import java.util.List;

public class Mapa extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,LocationListener {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final String TAG ="provider";
    Button btSalva;
    Button pegaMypos;
    String endereco;
    String nome;
    double latitude = 0;
    double longitude = 0;
    String provader;
    Loading loading;
    String Cidade;
    String Cep;
    String Estado;
    String Endereco;
    String Bairro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_EnviarNotificacoes);
        loading = new Loading(Mapa.this);
        loading.abrir("Aguarde...");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            endereco =  bundle.getString("endereco","");
            nome = bundle.getString("nome","");
            latitude = Double.valueOf(bundle.getString("latitude","0"));
            longitude = Double.valueOf(bundle.getString("longitude","0"));
        }

        btSalva = (Button)findViewById(R.id.btSalva);
        btSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent();
                data.putExtra("latitude",String.valueOf(latitude));
                data.putExtra("longitude",String.valueOf(longitude));
                data.putExtra("endereco",String.valueOf(Endereco));
                data.putExtra("cidade",String.valueOf(Cidade));
                data.putExtra("estado",String.valueOf(Estado));
                data.putExtra("bairro",String.valueOf(Bairro));
                setResult(RESULT_OK, data);
                finish();
            }
        });

        pegaMypos = (Button)findViewById(R.id.btpega);
        pegaMypos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.abrir("Aguarde...");
                try {
                    mMap.clear();
                    Location location = locationManager.getLastKnownLocation(provader);
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    mMap.addMarker(new MarkerOptions().position(userLocation).title(nome));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18.0f));
                    loading.fechar();
                }catch (SecurityException s)
                {
                }

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provader = locationManager.getBestProvider(criteria, true);
            mMap = googleMap;
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMinZoomPreference(15);
            mMap.setOnMapClickListener(this);


        }catch (SecurityException e)
        {
            Log.e(TAG,"erro"+ e);
        }

        if(latitude !=0 && longitude !=0)
        {
            LatLng myEndereco = new LatLng(latitude,longitude);
            mMap.addMarker(new MarkerOptions().position(myEndereco).title(nome));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myEndereco, 18.0f));
        }
        else
        {

            if(!endereco.equals(""))
            {
                Geocoder geocoder = new Geocoder(Mapa.this);
                try {
                    List<Address> enderecos = geocoder.getFromLocationName(endereco, 1);

                    if (enderecos.size() > 0) {
                        LatLng myEndereco = new LatLng(enderecos.get(0).getLatitude(),enderecos.get(0).getLongitude());
                        latitude = enderecos.get(0).getLatitude();
                        longitude = enderecos.get(0).getLongitude();
                        mMap.addMarker(new MarkerOptions().position(myEndereco).title(nome));
                       // mMap.moveCamera(CameraUpdateFactory.newLatLng(myEndereco));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myEndereco, 18.0f));
                    }
                }catch (IOException e)
                {

                }
            }



        }
        pegaMypos.callOnClick();
        loading.fechar();
    }

    @Override
    public void onResume(){
        super.onResume();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }catch (SecurityException s)
        {

        }
    }

    @Override
    public void onPause(){
        super.onPause();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title(nome));
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
        Geocoder geocoder = new Geocoder(Mapa.this);
        try {
            List<Address> enderecos = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

            if (enderecos.size() > 0) {
               if(enderecos.get(0).getLocality()!=null)
                   Cidade=enderecos.get(0).getLocality();
               else
                   if(enderecos.get(0).getSubAdminArea()!=null)
                       Cidade = enderecos.get(0).getSubAdminArea();
               Estado = enderecos.get(0).getAdminArea();
               Bairro = enderecos.get(0).getSubLocality();
               Endereco = enderecos.get(0).getThoroughfare();

            }

        }catch (IOException e)
        {

        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
