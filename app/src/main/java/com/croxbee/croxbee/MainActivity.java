package com.croxbee.croxbee;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity {
    private CallbackManager mCallbackManager;
    private static final String TAG = "MainActivity";
    FirebaseAuth mAuth;
    private Object data;
    TextView email_textview, name_textview, gender_textview;
    ProfileTracker profileTracker;
    ImageView profile_image;
    private ViewPager mpager;
    private int[] layouts = {R.layout.firstslide, R.layout.thirdslide, R.layout.secondslide};
    private MpagerAdapter mpagerAdapter;
    public LinearLayout dot_layout;
    public ImageView[] dots;
    private String firstName, lastName, email, birthday, gender, name;
    String hometown;
    private String profilePicture;
    private String userId;
    Button fbLogin;
    LoginButton loginButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    Context mContext;
    FirebaseMethods firebaseMethods;
    int user_age = 0;
    String wallAlbumID;
    ArrayList<String> profile_image_album = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mContext = MainActivity.this;
        TextView termcondition = (TextView) findViewById(R.id.textViewtermcondition);
        String text = " By connecting , you agree to our <b><u> Term of services</u> </b> and <b><u> Privacy policy</u> </b>";
        termcondition.setText(Html.fromHtml(text));
        firebaseMethods = new FirebaseMethods(MainActivity.this);


//        name_textview=(TextView)findViewById(R.id.name);
//        profile_image=(ImageView)findViewById(R.id.imageView);
        fbLogin = (Button) findViewById(R.id.fbloginbutton);


        welcomeScreenTopScreenAllStuff();
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();

            }
        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(
                "public_profile", "email", "user_birthday", "user_friends", "user_gender", "user_hometown", "user_age_range", "user_photos", "user_posts");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);

                //Profile profile=Profile.getCurrentProfile();
                //displayProfile(profile);
                //getProfileData(loginResult.getAccessToken());


                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //Intent intent=new Intent(MainActivity.this,Main2Activity.class);

                                //User_Facebook_Model model=new User_Facebook_Model();

                                try {
                                    try {
                                        userId = object.getString("id");
                                        if (userId.isEmpty()) {
                                            userId = "empty";
                                        }
                                        //intent.putExtra("fb_user_id",userId);
                                        //model.setFb_user_id(userId);
                                        Log.i(TAG, "onCompleted: " + userId);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?type=large").toString();
                                        if (profilePicture.isEmpty()) {
                                            profilePicture = "empty";
                                        }
                                        //intent.putExtra("profile_picture",profilePicture);

                                        // profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                                        // model.setProfile_picture(profilePicture);

                                        Log.i(TAG, "onCompleted: " + profilePicture);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }

                                    if (object.has("first_name")) {
                                        try {
                                            firstName = object.getString("first_name");
                                            Log.i(TAG, "onCompleted: " + firstName);
                                            if (firstName.isEmpty()) {
                                                firstName = "empty";
                                            }
                                            //intent.putExtra("first_name",firstName);
                                            //model.setFirstName(firstName);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (object.has("name")) {
                                        try {
                                            name = object.getString("name");
                                            Log.i(TAG, "onCompleted: " + name);
                                            if (name.isEmpty()) {
                                                name = "empty";
                                            }
                                            //model.setName(name);
                                            //intent.putExtra("name",name);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (object.has("last_name")) {
                                        try {
                                            lastName = object.getString("last_name");
                                            Log.i(TAG, "onCompleted: " + lastName);
                                            if (lastName.isEmpty()) {
                                                lastName = "empty";
                                            }
                                            //model.setLastName(lastName);
                                            //intent.putExtra("last_name",lastName);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (object.has("email")) {
                                        try {
                                            email = object.getString("email");
                                            Log.i(TAG, "onCompleted: " + email);
                                            if (email.isEmpty()) {
                                                email = "empty";
                                            }
                                            // model.setEmail(email);
                                            //intent.putExtra("email",email);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (object.has("birthday")) {
                                        try {
                                            birthday = object.getString("birthday");
                                            if (birthday.isEmpty()) {
                                                birthday = "empty";
                                            }
                                            //intent.putExtra("birthday",birthday);
                                            // model.setBirthday(birthday);
                                            Log.i(TAG, "onCompleted: " + birthday);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (object.has("gender")) {
                                        try {
                                            gender = object.getString("gender");
                                            Log.i(TAG, "onCompleted: " + gender);
                                            if (gender.isEmpty()) {
                                                gender = "empty";
                                            }
                                            //model.setGender(gender);
                                            //intent.putExtra("gender",gender);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (object.has("hometown")) {
                                        JSONObject home_town_object = null;
                                        try {
                                            home_town_object = object.getJSONObject("hometown");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        for (int i = 0; i < home_town_object.length(); i++) {
                                            try {
                                                Log.i("idddd", home_town_object.getString("name"));
                                                hometown = home_town_object.getString("name");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        // hometown = object.getString("hometown");
                                        //int lenght = hometown.length();
                                        if (hometown.isEmpty()) {
                                            hometown = "empty";
                                        }
//                                            JSONArray data = null;
//                                            try {
//
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
                                        ////intent.putExtra("home_town",hometown);

                                        // hometown=hometow.get(1).toString();
                                        //model.setHometown(hometown);

                                        Log.i(TAG, "onCompleted: " + hometown);
                                    }
                                    if (object.has("albums")) {

                                        JSONObject albums_main = null;
                                        try {
                                            albums_main = object.getJSONObject("albums");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        JSONArray albums = null;
                                        try {
                                            albums = albums_main.getJSONArray("data");
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        for (int i = 0; i < albums.length(); i++) {
                                            JSONObject album = null;
                                            try {
                                                album = albums.getJSONObject(i);
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                            try {
                                                //Here I have selected Profile pic album.
                                                if (album.getString("name").equalsIgnoreCase("Profile Pictures")) {
                                                    //  JSONArray al=null;
                                                    wallAlbumID = album.getString("id");
                                                    // Now you have album id in wallAlbumID(global varible).
                                                    Log.d("JSON", wallAlbumID);
                                                    break;
                                                }
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }

                                        }
                                        if (wallAlbumID.isEmpty()) {
                                            wallAlbumID = null;
                                        }
                                        Log.i(TAG, "onCompleted: " + wallAlbumID);
                                    }
//                                    if (object.has(wallAlbumID+"/photos")) {
//                                        JSONObject photos = null;
//                                        try {
//                                            photos = object.getJSONObject("id");
//                                            Log.d(TAG, "onCompleted: " + photos.length());
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        Log.d(TAG, "onCompleted: " + photos.length());
//
//
//                                    } else {
//                                        Log.d(TAG, "onCompleted: No Object found");
//                                    }


                                    //getFacebookImages(wallAlbumID,loginResult.getAccessToken());


                                    if (object.has("age_range")) {
                                        JSONObject age_range = null;
                                        try {
                                            age_range = object.getJSONObject("age_range");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        for (int i = 0; i < age_range.length(); i++) {
                                            try {
                                                Log.i("idddd", String.valueOf(age_range.getInt("min")));
                                                int min_age = age_range.getInt("min");
                                                int max_age = age_range.getInt("max");
                                                user_age = (min_age + max_age) / 2;
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        // hometown = object.getString("hometown");
                                        //int lenght = hometown.length();
                                        if (user_age == 0) {
                                            user_age = 0;
                                        }
//                                            JSONArray data = null;
//                                            try {
//
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
                                        ////intent.putExtra("home_town",hometown);

                                        // hometown=hometow.get(1).toString();
                                        //model.setHometown(hometown);

                                        Log.i(TAG, "onCompleted: " + age_range);
                                    }
                                  //  getFacebookImages(wallAlbumID,loginResult.getAccessToken());


                                    ///////code from stack overflow for retrive albums/////////////////////////////////////////


                                    //startActivity(intent);
//                                    firebaseMethods.addNewUser(userId,email,name,firstName,lastName,profilePicture,gender,birthday,hometown);
                                } catch (NullPointerException e) {
                                    Log.i(TAG, "onCompleted: " + e);

                                }
                            }
                        });



//                mAuth=FirebaseAuth.getInstance();
//                User_Facebook_Model profilemodel=new User_Facebook_Model();
//                String uid=FirebaseAuth.getInstance().getUid();
//                profilemodel.setFirebase_user_id(uid);


                // firebaseMethods.addNewUser(userId,email,name,firstName,lastName,profilePicture,gender,birthday,hometown);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday,gender,languages,hometown," +
                        "installed,last_name,first_name,age_range,photos{album,id,images},albums");
                request.setParameters(parameters);
                request.executeAsync();
                handleFacebookAccessToken(loginResult.getAccessToken());


            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }


        });
//        profileTracker=new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
//                //displayProfile(currentProfile);
//
//            }
//        };profileTracker.startTracking();


    }

    public void getFacebookImages(final String albumId, AccessToken accessToken) {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/"+albumId,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        if (response!=null){
                            JSONObject photos;
                            photos=response.getJSONObject();
                            JSONArray data;
                            try {
                               data =photos.getJSONArray("data");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,photos{images}");
        request.setParameters(parameters);
        request.executeAsync();


    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "You are logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//            intent.putExtra("fb_user_id",userId);
//            intent.putExtra("profile_picture",profilePicture);
//            intent.putExtra("first_name",firstName);
//            intent.putExtra("name",name);
//            intent.putExtra("last_name",lastName);
//            intent.putExtra("email",email);
//            intent.putExtra("birthday",birthday);
//            intent.putExtra("gender",gender);
//            intent.putExtra("home_town",hometown);
            startActivity(intent);


        } else {
            Toast.makeText(this, "SomethingWrong", Toast.LENGTH_LONG).show();

        }


    }


    //        User user = new User(userID, email, phone_number, username);
//        mReference.child(mContext.getString(R.string.user))
//                .child(userID)
//                .setValue(user);
//
//        User_account_setting setting = new User_account_setting(address, display_name, post, profile_photo, state, email, phone_number, username);
//        mReference.child(mContext.getString(R.string.dataaccountsetting))
//                .child(userID)
//                .setValue(setting);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication Sucees.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);

                            firebaseMethods.addNewUser(userId, email, name, firstName, lastName, profilePicture, gender, birthday, hometown, user.getUid(), user_age);
                            //getFriendsList();


                        }else{
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);


                        }

                        // ...
                    }
                });
    }

    public void welcomeScreenTopScreenAllStuff() {
        mpager = (ViewPager) findViewById(R.id.viewpager);
        mpagerAdapter = new MpagerAdapter(layouts, this);
        mpager.setAdapter(mpagerAdapter);
        dot_layout = findViewById(R.id.dotslayout);
        createdots(0);
        mpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createdots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 2000, 2000);


    }

    public void createdots(int current_position) {
        if (dot_layout != null) {
            dot_layout.removeAllViews();
        }
        dots = new ImageView[layouts.length];
        for (int i = 0; i < layouts.length; i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            dot_layout.addView(dots[i], params);

        }
    }

    public class MyTimerTask extends TimerTask {
        int position = mpager.getCurrentItem();


        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (position == 0) {

                        position++;
                        mpager.setCurrentItem(position);
                    } else if (position == 1) {

                        position++;
                        mpager.setCurrentItem(position);

                    } else if (position == 2) {

                        position = 0;
                        mpager.setCurrentItem(position);


                    }

                }
            });
        }

        ////--------------------------------------------------------///////--------------------------------------------------------------------------

    }


}