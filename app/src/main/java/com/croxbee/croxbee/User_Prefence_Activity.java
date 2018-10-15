package com.croxbee.croxbee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

public class User_Prefence_Activity extends AppCompatActivity {
    RangeBar rangeBar;
    TextView min_range, max_range;
    Switch selectWoman, selectMan, selectBoth;
    int gender_selection = 0;   ///if 0 for women and 1 for men and 2 for both
    RangeBar location_range_bar;
    TextView location_max, location_min;
    CheckBox dating_check_box, chat_check_box, friendship_check_box;
    FirebaseMethods firebaseMethods;
    String interest = "Dating";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    int min_distances = 10;
    int max_distances = 100;
    int min_range_add_to_dat;
    int max_range_add_to_dat;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__prefence_);
        rangeBar = (RangeBar) findViewById(R.id.age_range_selector);
        min_range = (TextView) findViewById(R.id.minimumagetextview);
        max_range = (TextView) findViewById(R.id.maximumagetextview);
        selectWoman = (Switch) findViewById(R.id.select_women_button);
        location_range_bar = (RangeBar) findViewById(R.id.distance_range_selector);
        selectMan = (Switch) findViewById(R.id.select_men_button);
        selectBoth = (Switch) findViewById(R.id.select_both_button);
        location_min = (TextView) findViewById(R.id.minimundistance);
        location_max = (TextView) findViewById(R.id.maximundistances);
        chat_check_box = (CheckBox) findViewById(R.id.checkBox_chat);
        dating_check_box = (CheckBox) findViewById(R.id.checkBox_dating);
        friendship_check_box = (CheckBox) findViewById(R.id.checkbox_friendship);
        firebaseMethods = new FirebaseMethods(User_Prefence_Activity.this);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();

        } else {
            userID = mAuth.getUid();
        }

        mReference = firebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();

        } else {
            userID = mAuth.getUid();
        }
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Profile_Prefences profile_prefences = new User_Profile_Prefences();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals("User_Profile_Prefence")) {
                        profile_prefences.setPrefence_gender(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Profile_Prefences.class)
                                        .getPrefence_gender()
                        );
                        profile_prefences.setPrefence_age_max(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Profile_Prefences.class)
                                        .getPrefence_age_max()
                        );
                        profile_prefences.setPrefence_age_min(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Profile_Prefences.class)
                                        .getPrefence_age_min()
                        );
                        profile_prefences.setInterest(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Profile_Prefences.class)
                                        .getInterest()
                        );
                        profile_prefences.setMin_distance(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Profile_Prefences.class)
                                .getMin_distance()
                        );

                        profile_prefences.setMax_distance(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Profile_Prefences.class)
                                .getMax_distance()
                        );

                    }

                }
                if (dataSnapshot.hasChild("User_Profile_Prefence")) {
                    if (profile_prefences.getPrefence_gender().equals("women")) {

                        gender_selection = 0;
                        selectMan.setChecked(false);
                        selectWoman.setChecked(true);
                        selectBoth.setChecked(false);
                    } else if (profile_prefences.getPrefence_gender().equals("both")) {
                        gender_selection = 1;
                        selectBoth.setChecked(true);
                        selectMan.setChecked(false);
                        selectWoman.setChecked(false);


                    } else {
                        gender_selection = 2;
                        selectMan.setChecked(true);
                        selectBoth.setChecked(false);
                        selectWoman.setChecked(false);


                    }
                    if (profile_prefences.getPrefence_age_min() > 18 && profile_prefences.getPrefence_age_max() < 70) {
                        rangeBar.setRangePinsByValue(profile_prefences.getPrefence_age_min(), profile_prefences.getPrefence_age_max());

                    } else {
                        rangeBar.setRangePinsByValue(19, 22);
                    }
                    if (profile_prefences.getMin_distance() > 10 && profile_prefences.getMax_distance() < 100) {
                        location_range_bar.setRangePinsByValue(profile_prefences.getMin_distance(), profile_prefences.getMax_distance());

                    } else {
                        location_range_bar.setRangePinsByValue(10, 100);
                    }


                    if (profile_prefences.getInterest().equals("Dating")) {
                        dating_check_box.setChecked(true);
                        chat_check_box.setChecked(false);
                        friendship_check_box.setChecked(false);
                        interest = "Dating";


                    } else if (profile_prefences.getInterest().equals("Chat")) {
                        dating_check_box.setChecked(false);
                        chat_check_box.setChecked(true);
                        friendship_check_box.setChecked(false);
                        interest = "Chat";

                    } else {
                        dating_check_box.setChecked(false);
                        chat_check_box.setChecked(false);
                        friendship_check_box.setChecked(true);
                        interest = "FriendShip";


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        selectWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectMan.setChecked(false);
                    selectWoman.setChecked(true);
                    selectBoth.setChecked(false);

                    gender_selection = 0;
                }
            }
        });

        selectMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectBoth.setChecked(false);
                    selectMan.setChecked(true);
                    selectWoman.setChecked(false);
                    gender_selection = 1;

                }


            }
        });
        selectBoth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectMan.setChecked(false);
                    selectBoth.setChecked(true);
                    selectWoman.setChecked(false);
                    gender_selection = 2;
                }
            }
        });


        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                min_range.setText(leftPinValue);
                min_range_add_to_dat = Integer.valueOf(leftPinValue);
                if (rightPinIndex == 70) {
                    max_range.setText(rightPinValue + "+");
                    max_range_add_to_dat = Integer.valueOf(rightPinValue);
                } else {
                    max_range.setText(rightPinValue);
                    max_range_add_to_dat = Integer.valueOf(rightPinValue);
                }


            }

        });
        rangeBar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                return s;
            }
        });
        location_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                location_min.setText(leftPinValue);
                min_distances = Integer.valueOf(leftPinValue);
                if (rightPinIndex == 100) {
                    location_max.setText(rightPinValue + "+");
                    max_distances = Integer.valueOf(rightPinValue);
                } else {
                    location_max.setText(rightPinValue);
                    max_distances = Integer.valueOf(rightPinValue);
                }


            }

        });
        location_range_bar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                return s;
            }
        });
        chat_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dating_check_box.setChecked(false);
                    chat_check_box.setChecked(true);
                    friendship_check_box.setChecked(false);
                    interest = "Chat";
                }
            }
        });
        dating_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dating_check_box.setChecked(true);
                    chat_check_box.setChecked(false);
                    friendship_check_box.setChecked(false);
                    interest = "Dating";
                }
            }
        });
        friendship_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dating_check_box.setChecked(false);
                    chat_check_box.setChecked(false);
                    friendship_check_box.setChecked(true);
                    interest = "FriendShip";
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        String gender = "";
        if (gender_selection == 0) {
            gender = "women";

        } else if (gender_selection == 1) {
            gender = "men";
        } else {
            gender = "both";
        }

        firebaseMethods.addUser_Prefence(gender, min_range_add_to_dat, max_range_add_to_dat, min_distances, max_distances

                , interest, userID);
        int count = 0;
        Bundle extra=getIntent().getExtras();
        if (extra.get("Time_line_fragment")=="timelinefragment") {
        Intent prefence = new Intent(User_Prefence_Activity.this, Main2Activity.class);
        count = 1;
        prefence.putExtra("User_Prefence_Activity", "User_Activity_Prefence");
        prefence.putExtra("count", count);
        startActivity(prefence);
        super.onBackPressed();
    }
    else {
            super.onBackPressed();


        }
    }
}
