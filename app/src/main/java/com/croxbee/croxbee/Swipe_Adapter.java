package com.croxbee.croxbee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Swipe_Adapter extends BaseAdapter {
    public List<User_Complete_Profile> user_card_details;
    public Context context;

    public Swipe_Adapter(List<User_Complete_Profile> user_card_details, Context context) {
        this.user_card_details = user_card_details;
        this.context = context;
    }

    @Override
    public int getCount() {
        return user_card_details.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View cardView=view;
        ViewHolder viewHolder;
        viewHolder=new ViewHolder();
        if (cardView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cardView = inflater.inflate(R.layout.swipe_card_item, viewGroup, false);


           // viewHolder.DataText = (TextView)cardView.findViewById(R.id.bookText);
            viewHolder.background = (FrameLayout) cardView.findViewById(R.id.background);
            viewHolder.cardImage = (ImageView)cardView.findViewById(R.id.cardImage);
            cardView.setTag(viewHolder);

        }
        else {
            viewHolder= (ViewHolder) view.getTag();
        }
       // viewHolder.DataText.setText(user_card_details.get(i).getAboutme() + "");

       //Glide.with(context).load(user_card_details.get(i).getProfile_picture()).into(viewHolder.cardImage);



        return cardView;
    }
    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }
}
