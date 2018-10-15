package com.croxbee.croxbee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TimeLineFragement extends Fragment {
    FirebaseMethods firebaseMethods;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String profile_picture;
    ImageView profile_image;
    Button main_set_prefences;



      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view=inflater.inflate(R.layout.fragment_time_line_fragement, container, false);
          profile_image=(ImageView)view.findViewById(R.id.timeline_page_profile_image);
          main_set_prefences=(Button)view.findViewById(R.id.set_prefences_button);
          main_set_prefences.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  Intent intent=new Intent(getContext(),User_Prefence_Activity.class);
                  intent.putExtra("Time_line_fragment","timelinefragment");
                  startActivity(intent);
              }
          });
          mAuth = FirebaseAuth.getInstance();
          firebaseDatabase = FirebaseDatabase.getInstance();
          databaseReference = firebaseDatabase.getReference();
          firebaseMethods =new FirebaseMethods(getContext());

          databaseReference.addValueEventListener(new ValueEventListener() {
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

                      }

                  }
                  profile_picture=model.getProfile_picture();
                  Picasso.get().load(profile_picture).into(profile_image);


//                  Toast.makeText(getActivity(),""+profile_picture,Toast.LENGTH_SHORT).show();
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });

        return view;
    }



}
