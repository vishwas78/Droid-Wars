package com.example.vishwasmittal.droidwars;

//TODO: this fragment is completed... :)

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.URL;


public class TargetPagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.target_pager_fragment, container, false);
        Log.e("Inside pagerFragment", "pagerFragment called");
        TargetLayoutClass targetInfo = new DataBaseClass(getContext(), null, null, 1).returnTargetInfo(getArguments().getInt("_id"));

        //referencing the UI elements
        RelativeLayout PagerRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.target_pager_relative_layout);
        ImageView targetImage = (ImageView) rootView.findViewById(R.id.edit_image);
        TextView nameTextView = (TextView) rootView.findViewById(R.id.target_name_textView);
        TextView houseTextView = (TextView) rootView.findViewById(R.id.edit_house);
        TextView statusTextView = (TextView) rootView.findViewById(R.id.status_textView);
        TextView reasonTextView = (TextView) rootView.findViewById(R.id.reason_textView);
        TextView killPlaceTextView = (TextView) rootView.findViewById(R.id.kill_place_textView);
        TextView killWayTextView = (TextView) rootView.findViewById(R.id.kill_way_textView);

        if (targetInfo.get_status().matches("Killed")) {
            PagerRelativeLayout.setBackgroundResource(R.drawable.cross);
        }

        String imageAddr = targetInfo.get_imgAddr();
        if (imageAddr != null) {
        /*    try {
                Uri uri = Uri.parse(imageAddr);
                targetImage.setImageURI(uri);
                targetImage.setBackgroundColor(Color.alpha(0));
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error Loading Image", Toast.LENGTH_SHORT).show();
                targetImage.setBackgroundColor(Color.alpha(100));
                targetImage.setImageResource(R.mipmap.ic_launcher);
            }*/

            try(InputStream inputStream = new URL(imageAddr).openStream()){
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = Bitmap.createScaledBitmap(bitmap, 55, 55, true);
                targetImage.setImageBitmap(bitmap);
            }catch(Exception e){
                Toast.makeText(getContext(), "Image Not Found", Toast.LENGTH_SHORT).show();
            }

           // File image = new File(imageAddr);
           // BitmapFactory.Options bmOptions = new BitmapFactory.Options();

         //   bitmap = Bitmap.createScaledBitmap(bitmap, 55, 55, true);
            //targetImage.setImageBitmap( decodeSampledbitmapFromFile(imageAddr, 55, 55));
           // targetImage.setImageBitmap(bitmap);
        } else {
            targetImage.setImageResource(R.mipmap.ic_launcher);
            Log.e("the imgaddr", "inside else");
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

    public static Bitmap decodeSampledbitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || reqWidth > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
