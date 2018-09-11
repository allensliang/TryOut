package com.example.aliang.tryout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public LocationManager service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        switch (status)
        {
            case ConnectionResult.SUCCESS:
                break;

            case ConnectionResult.SERVICE_DISABLED:
                break;

            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:

                System.out.println("sfs");

                break;

            default:

                break;
        }



//        ConnectionResult: SUCCESS, SERVICE_MISSING, SERVICE_UPDATING, SERVICE_VERSION_UPDATE_REQUIRED, SERVICE_DISABLED, SERVICE_INVALID


        service = (LocationManager) getSystemService(LOCATION_SERVICE);



            List<String> allproviders = service.getAllProviders();

            for (String pp : allproviders)
            {
                System.out.println(pp);
            }

            int per = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

            System.out.println(per);
            if (per != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        33);
            }
            Criteria criteria = new Criteria();

            String provider = service.getBestProvider(criteria, true);

            //          String provider="passive";

            System.out.println(provider);
            Location location = service.getLastKnownLocation(provider);

//            service.requestLocationUpdates(provider, 400, 1, this);

            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");


                double latitude =  (location.getLatitude());
                double longitude =  (location.getLongitude());
                System.out.println(String.valueOf(latitude));
                System.out.println(String.valueOf(longitude));

                float acc = location.getAccuracy();

                System.out.println("accu=" + acc);


                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss",
                        Locale.ENGLISH);
                String format = s.format(location.getTime());

                System.out.println("time=" + s);

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 10); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

//                    TextView textView = findViewById(R.id.textView);

  //                  textView.setText(address);

                    System.out.println("address=" + address);
                    System.out.println("city=" + city);
                    System.out.println("state=" + state);
                    System.out.println("country=" + country);
                    System.out.println("postalCode=" + postalCode);
                    System.out.println("knownName=" + knownName);
                }
                catch (Exception e)
                {

                }

            }




    }
}
