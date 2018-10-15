package com.croxbee.croxbee;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private Context mContext;
    private static String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;

    public FirebaseMethods(Context context) {

        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();

        } else {
            userID = mAuth.getUid();
        }

    }

    public void addNewUser(String fb_user_id, String email, String name, String firstName, String lastName, String profile_photo, String gender, String bithday, String hometown, String firebase_user_id,int age) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
//        User_Facebook_Model model=new User_Facebook_Model();
//        userID=model.getFirebase_user_id();
        // userID=mAuth.getCurrentUser().getUid();
        User_Facebook_Model user_facebook_model = new User_Facebook_Model(name, firstName, lastName, gender, bithday, hometown, fb_user_id, email, profile_photo, firebase_user_id,age);
        mReference.child("User_Facebook_Details")
                .child(firebase_user_id)
                .setValue(user_facebook_model);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void addUser_Prefence(String prefence_gender, int min_age_prefences, int max_age_prefence,int min_prefer_distance,int max_prefer_distance, String interest,String firebase_user_id) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
//        User_Facebook_Model model=new User_Facebook_Model();
//        userID=model.getFirebase_user_id();
        // userID=mAuth.getCurrentUser().getUid();
       User_Profile_Prefences user_profile_prefences = new User_Profile_Prefences(prefence_gender,min_age_prefences,max_age_prefence,min_prefer_distance,max_prefer_distance,interest);
        mReference.child("User_Profile_Prefence")
                .child(firebase_user_id)
                .setValue(user_profile_prefences);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public User_Facebook_Model getUserProfile(DataSnapshot dataSnapshot) {


        Log.d(TAG, "getUser_account_settings: retrive user account setting from firebase");
        User_Facebook_Model model = new User_Facebook_Model();
        User_Complete_Profile user_complete_profile=new User_Complete_Profile();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.getKey().equals("User_Facebook_Details")) {
                try {


                    Log.d(TAG, "getUser_account_settings: datasnapshot" + ds);

                    model.setProfile_picture(
                            ds.child(userID)
                                    .getValue(User_Facebook_Model.class)
                                    .getProfile_picture()
                    );



                    Log.d(TAG, "getUser_account_settings:  retrived information useraccountsetting" + model.toString());


                } catch (NullPointerException e) {
                    Log.d(TAG, "getUser_account: datasnapshot" + e.getCause());
                }
            }

        }
        return new User_Facebook_Model();


    }

}
