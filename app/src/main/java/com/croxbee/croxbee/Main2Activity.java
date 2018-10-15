package com.croxbee.croxbee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;
    FirebaseMethods firebaseMethods;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    LocationManager locationManager;
    LocationListener locationListener;
    private static final String TAG = "Main2Activity";
    private FusedLocationProviderClient mFusedLocationClient;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mTextMessage = (TextView) findViewById(R.id.message);
        firebaseMethods = new FirebaseMethods(Main2Activity.this);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();

        } else {
            userID = mAuth.getUid();
        }
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("User_Profile_Prefence").hasChild(mAuth.getCurrentUser().getUid())) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, new MainShow_TimeLine_Fragment());
                    transaction.commit();
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    firebaseMethods = new FirebaseMethods(Main2Activity.this);
                    navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;
                            switch (item.getItemId()) {
                                case R.id.navigation_home:

                                    mTextMessage.setText(R.string.title_home);
                                    selectedFragment = new MainShow_TimeLine_Fragment();
                                    break;
                                case R.id.navigation_messages:
                                    mTextMessage.setText("Message Activity");
                                    selectedFragment = new TimeLineFragement();
                                    break;
                                case R.id.navigation_notifications:
                                    mTextMessage.setText(R.string.title_notifications);
                                    selectedFragment = new TimeLineFragement();
                                    break;
                                case R.id.navigation_myaccount:
                                    mTextMessage.setText("My Account Activity");
                                    selectedFragment = new User_Account_Fragment();
                                    break;

                            }
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, selectedFragment);
                            transaction.commit();
                            return true;

                        }
                    });
                    //navigation.setFocusableInTouchMode(true);
                    disableShiftMode(navigation);
                    // run some code
                } else {
                    Intent intent = getIntent();
                    if (intent.hasExtra("User_Prefence_Activity")) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, new MainShow_TimeLine_Fragment());
                        transaction.commit();
                        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                        firebaseMethods = new FirebaseMethods(Main2Activity.this);
                        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                Fragment selectedFragment = null;
                                switch (item.getItemId()) {
                                    case R.id.navigation_home:

                                        mTextMessage.setText(R.string.title_home);
                                        selectedFragment = new MainShow_TimeLine_Fragment();
                                        break;
                                    case R.id.navigation_messages:
                                        mTextMessage.setText("Message Activity");
                                        selectedFragment = new MainShow_TimeLine_Fragment();
                                        break;
                                    case R.id.navigation_notifications:
                                        mTextMessage.setText(R.string.title_notifications);
                                        selectedFragment = new TimeLineFragement();
                                        break;
                                    case R.id.navigation_myaccount:
                                        mTextMessage.setText("My Account Activity");
                                        selectedFragment = new User_Account_Fragment();
                                        break;

                                }
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, selectedFragment);
                                transaction.commit();
                                return true;

                            }
                        });
                        FragmentTransaction maintransaction = getSupportFragmentManager().beginTransaction();
                        maintransaction.replace(R.id.container, new MainShow_TimeLine_Fragment());
                        maintransaction.commit();
                        //navigation.setFocusableInTouchMode(true);
                        disableShiftMode(navigation);
                    } else {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, new TimeLineFragement());
                        transaction.commit();
                        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                        firebaseMethods = new FirebaseMethods(Main2Activity.this);
                        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                Fragment selectedFragment = null;
                                switch (item.getItemId()) {
                                    case R.id.navigation_home:

                                        mTextMessage.setText(R.string.title_home);
                                        selectedFragment = new MainShow_TimeLine_Fragment();
                                        break;
                                    case R.id.navigation_messages:
                                        mTextMessage.setText("Message Activity");
                                        selectedFragment = new MainShow_TimeLine_Fragment();
                                        break;
                                    case R.id.navigation_notifications:
                                        mTextMessage.setText(R.string.title_notifications);
                                        selectedFragment = new MainShow_TimeLine_Fragment();
                                        break;
                                    case R.id.navigation_myaccount:
                                        mTextMessage.setText("My Account Activity");
                                        selectedFragment = new User_Account_Fragment();
                                        break;

                                }
                              FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, selectedFragment);
                                transaction.commit();
                                return true;

                            }
                        });


                        //navigation.setFocusableInTouchMode(true);
                        disableShiftMode(navigation);

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        if (Build.VERSION.SDK_INT<23){
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){
                        double log= location.getLongitude();
                        double lat= location.getLatitude();
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        mReference = firebaseDatabase.getReference();
                        mReference.child("User_Locations")
                                .child(mAuth.getCurrentUser().getUid())
                                .child("Longitude")
                                .setValue(log);
                        mReference.child("User_Locations")
                                .child(mAuth.getCurrentUser().getUid())
                                .child("Latitude")
                                .setValue(lat);
                        mReference.child("User_Locations")
                                .child(mAuth.getCurrentUser().getUid())
                                .child("Current Location")
                                .setValue(location.toString());
                        mReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                }
            });
        }
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            } else {

                mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location!=null){
                            double log= location.getLongitude();
                            double lat= location.getLatitude();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            mReference = firebaseDatabase.getReference();
                            mReference.child("User_Locations")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .child("Longitude")
                                    .setValue(log);
                            mReference.child("User_Locations")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .child("Latitude")
                                    .setValue(lat);
                            mReference.child("User_Locations")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .child("Current Location")
                                    .setValue(location.toString());
                            mReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                        }
                    }
                });
            }
            // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){
                        double log= location.getLongitude();
                        double lat= location.getLatitude();
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        mReference = firebaseDatabase.getReference();
                        mReference.child("User_Locations")
                                .child(mAuth.getCurrentUser().getUid())
                                .child("Longitude")
                                .setValue(log);
                        mReference.child("User_Locations")
                                .child(mAuth.getCurrentUser().getUid())
                                .child("Latitude")
                                .setValue(lat);
                        mReference.child("User_Locations")
                                .child(mAuth.getCurrentUser().getUid())
                                .child("Current Location")
                                .setValue(location.toString());
                        mReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                }
            });

        }

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
               // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location!=null){
                            double log= location.getLongitude();
                            double lat= location.getLatitude();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            mReference = firebaseDatabase.getReference();
                            mReference.child("User_Locations")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .child("Longitude")
                                    .setValue(log);
                            mReference.child("User_Locations")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .child("Latitude")
                                    .setValue(lat);
//                            firebaseDatabase = FirebaseDatabase.getInstance();
//                            mReference = firebaseDatabase.getReference();
                            mReference.child("User_Locations")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .child("Current Location")
                                    .setValue(location.toString());
                            mReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                        }
                    }
                });



            }

        }
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

}
