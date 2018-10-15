package com.croxbee.croxbee;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainShow_TimeLine_Fragment extends Fragment {
    FirebaseMethods firebaseMethods;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_show__time_line_, container, false);

        setUpViewPager(view);





        return view;
    }
    public void setUpViewPager(final View view){
        final PagerAdapter pagerAdapter=new PagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addFragment(new NearByUser_Fragment());
        pagerAdapter.addFragment(new All_User_shows_Fragment());
        ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Nearby ");
        tabLayout.getTabAt(1).setText("All");
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.colorPrimary));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        }

}
