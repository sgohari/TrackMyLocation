package com.example.nasir.trackmylocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;

    private Marker markham;
    private Marker donMills;
    private Marker lowrence;
    private static final LatLng DONMILLS = new LatLng(43.739338, -79.343922);
    private static final LatLng MARKHAM = new LatLng(43.793670, -79.238540);
    private static final LatLng LOWRENCE = new LatLng(43.711209, -79.467598);


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //check weather the network provider is avialable
        if(locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)){

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, new LocationListener() {
                @Override
                public void onLocationChanged (Location location) {
                    //get latitude
                    double latitude= location.getLatitude();
                    //get longtidue
                    double longitude=location.getLongitude();
                    //inistantiate the class, LatLng
                    LatLng latLng = new LatLng(latitude,longitude);

                    //inistantiate the class, GeoCoder

                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList= geocoder.getFromLocation(latitude,longitude,3);
                        String str = addressList.get(0).getLocality();
                        str+=addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged (String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled (String provider) {

                }

                @Override
                public void onProviderDisabled (String provider) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, new LocationListener() {
                @Override
                public void onLocationChanged (Location location) {

                    //get latitude
                    double latitude= location.getLatitude();
                    //get longtidue
                    double longitude=location.getLongitude();
                    //inistantiate the class, LatLng
                    LatLng latLng = new LatLng(latitude,longitude);

                    //inistantiate the class, GeoCoder

                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList= geocoder.getFromLocation(latitude,longitude,3);
                        String str = addressList.get(0).getLocality();
                        str+=addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged (String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled (String provider) {

                }

                @Override
                public void onProviderDisabled (String provider) {

                }
            });
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady (GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //
        // Add some markers to the map, and add a data object to each marker.
        markham =mMap.addMarker(new MarkerOptions().position(MARKHAM).title("Markham Road"));
        markham.setTag(0);

        donMills =mMap.addMarker(new MarkerOptions().position(DONMILLS).title("Don Mills Road"));
        donMills.setTag(1);

        lowrence =mMap.addMarker(new MarkerOptions().position(LOWRENCE).title("Lawrence Avenue West")
                .snippet("Available For Booking").icon(BitmapDescriptorFactory.fromResource(R.drawable.imag)));
        // Set a listener for marker click.

       mMap.setOnMarkerClickListener(this);
       mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public boolean onMarkerClick (Marker marker) {

        if (marker.getTitle().equals("Don Mills Road") )
        {
            startActivity(new Intent(MapsActivity.this, ViewActivity.class));
        }
            return false;
    }

    @Override
    public void onInfoWindowClick (Marker marker) {
        donMills.showInfoWindow();
        markham.showInfoWindow();
        lowrence.showInfoWindow();
    }
}
