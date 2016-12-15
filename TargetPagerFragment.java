package com.example.vishwasmittal.droidwars;

//TODO: this fragment is completed... :)

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TargetPagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.target_pager_fragment, container, false);

        TargetLayoutClass targetInfo = new TargetLayoutClass();                     //handling the information received from parent class
        Bundle targetPagerInfoBundle = getArguments();
        targetInfo.set_id(targetPagerInfoBundle.getInt("_id"));
        targetInfo.set_name(targetPagerInfoBundle.getString("_name"));
        targetInfo.set_house(targetPagerInfoBundle.getString("_house"));
        targetInfo.set_reason(targetPagerInfoBundle.getString("_reason"));
        targetInfo.set_status(targetPagerInfoBundle.getString("_status"));
        targetInfo.set_killPlace(targetPagerInfoBundle.getString("_killPlace"));
        targetInfo.set_killWay(targetPagerInfoBundle.getString("_killWay"));
        targetInfo.set_imgAddr(targetPagerInfoBundle.getString("_imgAddr"));

        //referencing the UI elements
        RelativeLayout targetPagerRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.target_pager_relative_layout);
        ImageView targetImage = (ImageView) rootView.findViewById(R.id.target_image);
        TextView nameTextView = (TextView) rootView.findViewById(R.id.target_name_textView);
        TextView houseTextView = (TextView) rootView.findViewById(R.id.edit_house);
        TextView statusTextView = (TextView) rootView.findViewById(R.id.status_textView);
        TextView reasonTextView = (TextView) rootView.findViewById(R.id.reason_textView);
        TextView killPlaceTextView = (TextView) rootView.findViewById(R.id.kill_place_textView);
        TextView killWayTextView = (TextView) rootView.findViewById(R.id.kill_way_textView);

        if(targetInfo.get_status().matches("Killed")){
            targetPagerRelativeLayout.setBackground(Drawable.createFromPath("@android:drawable/ic_delete"));
        }

        String imageAddr = targetInfo.get_imgAddr();
        if(imageAddr != null) {
            Uri uri = Uri.parse(imageAddr);
            targetImage.setImageURI(uri);
        }

        //setting received info on the screen
        nameTextView.setText(targetInfo.get_name());
        houseTextView.setText(targetInfo.get_house());
        statusTextView.setText(targetInfo.get_status());
        reasonTextView.setText(targetInfo.get_reason());
        killPlaceTextView.setText(targetInfo.get_killPlace());
        killWayTextView.setText(targetInfo.get_killWay());

        return rootView;
    }
}
