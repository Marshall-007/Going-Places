package com.example.goingplaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;


import androidx.appcompat.widget.Toolbar;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.security.PrivateKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapsA extends AppCompatActivity implements OnMapReadyCallback ,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

///////
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    SupportMapFragment mapFragment;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;

    ////////////
    private LatLng Origin;
    private LatLng Destination;
    private Polyline mPolyline;
    ArrayList<LatLng> mMarkerPoints;
///////////
    private ImageView Resturant;
    private ImageView Hospital;
    private ImageView Museam;
    SearchView searchView;

    private ImageView Walking;
    private ImageView Train;
    private ImageView Driving;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

 Toolbar toolbar = findViewById(R.id.toolbar);
 setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();

        }
        searchView = findViewById(R.id.sv_location);
        Resturant = findViewById(R.id.imgResturant);
        Hospital = findViewById(R.id.imghospital);
        Museam = findViewById(R.id.imgmeuseam);


        Walking  = findViewById(R.id.imgdrive);
        Driving  = findViewById(R.id.imgwalk);
        Train = findViewById(R.id.imgtrain);

        mMarkerPoints = new ArrayList<>();

        Resturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location myLocation  = mMap.getMyLocation();
                  mMap.clear();
                if(myLocation!=null)
                {

                    mMap.addMarker(new MarkerOptions().position(new LatLng(-26.193198,28.034692))
                        .title("Samias Halaal Foods").icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(-26.192779,28.035987))
                            .title("Nandos ").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(-26.193457, 28.036915))
                            .title(" KFC Bramfontain  ").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));



                    //move map camera by zooming in to selected locations
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-26.193198,28.034692)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
                else
                {
                    Toast.makeText(MapsA.this, "Unable to fetch the current location", Toast.LENGTH_SHORT).show();
                }



            }
        });

        Hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Location myLocation  = mMap.getMyLocation();
                if(myLocation!=null)
                {

                    mMap.addMarker(new MarkerOptions().position(new LatLng(-26.259960,27.944183))
                            .title("Chris Hani Baragwanath Hospital, Johannesburg, South Africa ").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng( -29.192490,27.455423))
                            .title("Mantsopa Hospital, Ladybrand, South Africa").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(-29.789614,30.741924))
                            .title("Hillcrest Private Hospital, Hillcrest, South Africa ").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));



                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-26.193198,28.034692)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(4));
                }
                else
                {
                    Toast.makeText(MapsA.this, "Unable to fetch the current location", Toast.LENGTH_SHORT).show();
                }





            }
        });




        Museam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Location myLocation  = mMap.getMyLocation();
                if(myLocation!=null)
                {

                    mMap.addMarker(new MarkerOptions().position(new LatLng(-26.192547,28.032979))
                            .title("Wits School of Arts").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng( -26.194554,28.034474))
                            .title("CandySA").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(-26.192809, 28.032951))
                            .title("Wits Musiam ").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));



                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-26.193198,28.034692)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                }
                else
                {
                    Toast.makeText(MapsA.this, "Unable to fetch the current location", Toast.LENGTH_SHORT).show();
                }





            }
        });




        Resturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });








        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressesList = null;


                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsA.this);
                    try {

                        addressesList = geocoder.getFromLocationName(location, 1);
                        if (addressesList != null) {
                            for (int i = 0; i < addressesList.size(); i++) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                Address address = addressesList.get(0);
                                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(latLng).title(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));


                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Unable to find locaton Please look for another location", Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                return false;
            }







            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                final ProgressDialog progressDialog = new ProgressDialog(MapsA.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Logging User out ...");
                progressDialog.show();

                new Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call  onLogout Success
                                Toast.makeText(getBaseContext(), "You Have been logged Out ", Toast.LENGTH_SHORT);
                                progressDialog.dismiss();

                                Intent intent = new Intent(MapsA.this, Login.class);
                                startActivity(intent);


                            }
                        }, 3000);
                return true;


            case R.id.profile:
                Intent intent0 = new Intent(MapsA.this, Profile.class);
                startActivity(intent0);
                return true;
            case R.id.List:
                Intent intent1 = new Intent(MapsA.this, FavouriteCities.class);
                startActivity(intent1);
                return true;
            case R.id.settings:
                Intent intent9 = new Intent(MapsA.this, com.example.goingplaces.Settings.class);
                startActivity(intent9);
                return true;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
        }
    }

    View mapView;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);


                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng point) {
                            // Already two locations
                            if(mMarkerPoints.size()>1){
                                mMarkerPoints.clear();
                                mMap.clear();
                            }

                            // Adding new item to the ArrayList
                            mMarkerPoints.add(point);

                            // Creating MarkerOptions
                            MarkerOptions options = new MarkerOptions();

                            // Setting the position of the marker
                            options.position(point);

                            /**
                             * For the start location, the color of marker is GREEN and
                             * for the end location, the color of marker is RED.
                             */
                            if(mMarkerPoints.size()==1){
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            }else if(mMarkerPoints.size()==2){
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            }

                            // Add new marker to the Google Map Android API V2
                            mMap.addMarker(options);

                            // Checks, whether start and end locations are captured
                            if(mMarkerPoints.size() >= 2){
                                Origin = mMarkerPoints.get(0);
                                Destination = mMarkerPoints.get(1);
                                Polyline line = mMap.addPolyline(new PolylineOptions()
                                        .add(Origin,Destination )
                                        .width(5)
                                        .color(Color.RED));
                                CalculationByDistance(Origin, Destination);

                            }

                        }
                    });
                }



            if (mapView != null &&
                    mapView.findViewById(Integer.parseInt("1")) != null) {
                // Get the button view
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                // and next place it, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        locationButton.getLayoutParams();
                // position on right bottom
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                layoutParams.setMargins(0, 0, 30, 30);
            }
        }





    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
         double time =  meter *0.12;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Distance from starting point to end point.")
                .setMessage(kmInDec + "," + meterInDec + " KM"+"\n"+"It will take ")
                .setNegativeButton("Close", null)
                .create();
        dialog.show();
        return Radius * c;
    }




















    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if (currentLocationmMarker != null) {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ", "" + latitude);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    public void onClick(View v) {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

        switch (v.getId()) {
            case R.id.nav_controller_view_tag:
                EditText tf_location = findViewById(R.id.sv_location);
                String location = tf_location.getText().toString();
                List<Address> addressList;


                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {

                                LatLng latLng = new LatLng(addressList.get(i).getLatitude(), addressList.get(i).getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(location);
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.historical:
                mMap.clear();
                String hospital = "historical";
                String url = getUrl(latitude, longitude, hospital);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsA.this, "Showing Nearby Historical Places", Toast.LENGTH_SHORT).show();
                break;


            case R.id.museum:
                mMap.clear();
                String school = "museum";
                url = getUrl(latitude, longitude, school);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsA.this, "Showing Nearby Museums ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.modern:
                mMap.clear();
                String resturant = "mordern";
                url = getUrl(-33.918861, 18.423300, resturant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsA.this, "Showing Nearby Wall Art Locations ", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=" + getString(R.string.google_maps_key));

        Log.d("MapsA", "url = " + googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            GoogleApiClient mGoogleClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            //  LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;

        } else
            return true;
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

}