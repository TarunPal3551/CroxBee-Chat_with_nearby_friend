package com.croxbee.croxbee;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;


public class NearByUser_Fragment extends Fragment {
    public static Swipe_Adapter adapter;
    private ArrayList<User_Complete_Profile> user_complete_details;
    private SwipeFlingAdapterView flingContainer;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_near_by_user_, container, false);
        flingContainer = (SwipeFlingAdapterView)view.findViewById(R.id.frame);
        adapter = new Swipe_Adapter(user_complete_details, getContext());
        //flingContainer.setAdapter(adapter);
//        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
//            @Override
//            public void removeFirstObjectInAdapter() {
//
//            }
//
//            @Override
//            public void onLeftCardExit(Object dataObject) {
//                user_complete_details.remove(0);
//               adapter.notifyDataSetChanged();
//                //Do something on the left!
//                //You also have access to the original object.
//                //If you want to use it just cast it (String) dataObject
//            }
//
//            @Override
//            public void onRightCardExit(Object dataObject) {
//
//               user_complete_details.remove(0);
//              adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onAdapterAboutToEmpty(int itemsInAdapter) {
//
//            }
//
//            @Override
//            public void onScroll(float scrollProgressPercent) {
//
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.background).setAlpha(0);
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
//            }
//        });
//
//
//        // Optionally add an OnItemClickListener
//        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClicked(int itemPosition, Object dataObject) {
//
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.background).setAlpha(0);
//
//                adapter.notifyDataSetChanged();
//            }
//        });



        return view;
    }


}
