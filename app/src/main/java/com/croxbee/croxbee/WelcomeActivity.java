package com.croxbee.croxbee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager mpager;
    private int[] layouts={R.layout.firstslide,R.layout.thirdslide,R.layout.secondslide};
    private MpagerAdapter mpagerAdapter;
    public LinearLayout dot_layout;
    public ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeScreenTopScreenAllStuff();








    }
    ////-------------------------------------Login Page top screen All stuff---------------------------------------------------
    public void welcomeScreenTopScreenAllStuff(){
        mpager=(ViewPager) findViewById(R.id.viewpager);
        mpagerAdapter=new MpagerAdapter(layouts,this);
        mpager.setAdapter(mpagerAdapter);
        dot_layout=findViewById(R.id.dotslayout);
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



    }
    public void createdots(int current_position)
    {
        if(dot_layout!=null)
        {
            dot_layout.removeAllViews();
        }
        dots =new ImageView[layouts.length];
        for(int i=0;i<layouts.length;i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dots));
            }

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dot_layout.addView(dots[i],params);
        }
    }

    private void loadhome()
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void loadnextslide(){
        int next_slide=mpager.getCurrentItem()+1;
        if(next_slide<layouts.length)
        {
            mpager.setCurrentItem(next_slide);
        }
        else
        {
            loadhome();

        }
    }
    ////--------------------------------------------------------///////--------------------------------------------------------------------------

  public void welcomeScreenBottomLoginAllstuff(){




  }

}



