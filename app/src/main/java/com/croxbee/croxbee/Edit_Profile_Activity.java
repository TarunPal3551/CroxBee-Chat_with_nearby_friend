
package com.croxbee.croxbee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Profile_Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    FirebaseMethods firebaseMethods;
    TextView username, age_textview, gender_textview;
    EditText job_edittext, employer_edittext, school_college_edittext, about_me;
    String name;
    String firstName;
    String lastName;
    String gender;
    String birthday;
    String hometown;
    String fb_user_id;
    String email;
    String profile_picture;
    String firebase_user_id;
    int age;
    String job;
    String employer;
    String school_college;
    String aboutme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_);
        username = (TextView) findViewById(R.id.edit_profile_username_);
        gender_textview = (TextView) findViewById(R.id.edit_profile_gender_text_view);
        age_textview = (TextView) findViewById(R.id.edit_profile_age_textview);
        job_edittext = (EditText) findViewById(R.id.job_edit_text);
        employer_edittext = (EditText) findViewById(R.id.employer_edittext);
        school_college_edittext = (EditText) findViewById(R.id.scholl_edittext);
        about_me=(EditText) findViewById(R.id.editText_about_me);


        firebaseMethods = new FirebaseMethods(Edit_Profile_Activity.this);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();

        } else {
            userID = mAuth.getUid();
        }
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Facebook_Model model = new User_Facebook_Model();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals("User_Facebook_Details")) {
                        model.setProfile_picture(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Facebook_Model.class)
                                        .getProfile_picture()
                        );

                        model.setName(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Facebook_Model.class)
                                        .getName()
                        );
                        model.setGender(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getGender());
                        model.setAge(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getAge());
                        model.setFirstName(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getFirstName());
                        model.setLastName(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getLastName());
                        model.setBirthday(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getBirthday());
                        model.setEmail(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getEmail());
                        model.setFb_user_id(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getFb_user_id());
                        model.setFirebase_user_id(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getFirebase_user_id());
                        model.setHometown(ds.child(mAuth.getCurrentUser().getUid())
                                .getValue(User_Facebook_Model.class)
                                .getHometown());


                    }

                }
                username.setText(model.getName()+",");
                age_textview.setText(String.valueOf(model.getAge()));
                gender_textview.setText((model.getGender().replace((model.getGender()).charAt(0),(model.getGender()).toUpperCase().charAt(0))));
                name = model.getName();
                firstName = model.getFirstName();
                lastName = model.getLastName();
                gender = model.getGender();
                birthday = model.getBirthday();
                hometown = model.getHometown();
                fb_user_id = model.getFb_user_id();
                email = model.getEmail();
                profile_picture = model.getProfile_picture();
                firebase_user_id = model.getFirebase_user_id();
                age = model.getAge();


                User_Complete_Profile user_complete_profile = new User_Complete_Profile();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals("User_Complete_Profile")) {
                        user_complete_profile.setJob(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Complete_Profile.class)
                                        .getJob()
                        );
                        user_complete_profile.setEmployer(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Complete_Profile.class)
                                        .getEmployer()
                        );
                        user_complete_profile.setSchool_college(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Complete_Profile.class)
                                        .getSchool_college()
                        );
                        user_complete_profile.setAboutme(
                                ds.child(mAuth.getCurrentUser().getUid())
                                        .getValue(User_Complete_Profile.class)
                                        .getAboutme()
                        );


                    }

                }
                job=user_complete_profile.getJob();
                employer=user_complete_profile.getEmployer();
                school_college=user_complete_profile.getSchool_college();
               aboutme= user_complete_profile.getAboutme();


                if (job==null){
                    job_edittext.setText("");

                }
                else {
                   job_edittext.setText(job);


                }
                if (employer==null){
                    employer_edittext.setText("");

                }
                else {
                   employer_edittext.setText(employer);
                }
                if (school_college==null){
                    school_college_edittext.setText("");

                }
                else {
                   school_college_edittext.setText(school_college);
                }
                if (user_complete_profile.getAboutme()==null){
                   about_me.setText("");

                }
                else {
                     about_me.setText(aboutme);
                     }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {

        User_Complete_Profile user_complete_profile = new User_Complete_Profile(name,firstName,lastName,gender,birthday,hometown,fb_user_id,email,profile_picture,firebase_user_id,age, String.valueOf(job_edittext.getText()), String.valueOf(employer_edittext.getText()), String.valueOf(school_college_edittext.getText())
                , String.valueOf(about_me.getText()));
        mReference.child("User_Complete_Profile")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(user_complete_profile);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onBackPressed();
    }
}

