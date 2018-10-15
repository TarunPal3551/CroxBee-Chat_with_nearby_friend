package com.croxbee.croxbee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.appyvet.materialrangebar.IRangeBarFormatter;
import com.appyvet.materialrangebar.RangeBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class User_Account_Fragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    FirebaseMethods firebaseMethods;
    RelativeLayout rel_prefences;
    ImageView edit_profile,profile_image;
    TextView nameage;
    String profile_picture;
    String name_age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_user__account_, container, false);
         rel_prefences=(RelativeLayout)view.findViewById(R.id.rel2);
         edit_profile=(ImageView)view.findViewById(R.id.edit_profile);
         profile_image=(ImageView)view.findViewById(R.id.profile_photo);
         nameage=(TextView)view.findViewById(R.id.name_age_account);
         rel_prefences.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getContext(),User_Prefence_Activity.class);
                 startActivity(intent);
             }
         });
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
        firebaseMethods =new FirebaseMethods(getContext());

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Facebook_Model model=new User_Facebook_Model();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    if (ds.getKey().equals("User_Facebook_Details")){
                        model.setProfile_picture(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Facebook_Model.class)
                                        .getProfile_picture()
                        );
                        model.setFirstName(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Facebook_Model.class)
                                        .getFirstName()
                        );
                        model.setAge(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Facebook_Model.class)
                                        .getAge()
                        );

                    }

                }
                profile_picture=model.getProfile_picture();
                Picasso.get().load(profile_picture).into(profile_image);
                name_age=model.getFirstName()+", "+" "+model.getAge();
                nameage.setText(name_age);


//                  Toast.makeText(getActivity(),""+profile_picture,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Edit_Profile_Activity.class);
                startActivity(intent);

            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Edit_Profile_Activity.class);
                startActivity(intent);

            }
        });








        return view;
    }


}
