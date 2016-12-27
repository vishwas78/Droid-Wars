package com.example.vishwasmittal.droidwars;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TargetListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_list_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hit List");
        toolbar.inflateMenu(R.menu.menu_activity_main_list_pager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Log.e("TargetListActivity", "rootView set, calling DB");
        DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);
        String[][] listData;
        listData = db.returnListItems();

        Log.e("TargetListActivity", " returned from DBClass referencing listView");
        ListView targetListView = (ListView) findViewById(R.id.target_list_view);
        TargetListViewAdapter listViewAdapter = new TargetListViewAdapter(getApplicationContext(), listData[0], listData[1], listData[2]);
        targetListView.setAdapter(listViewAdapter);
        Log.e("TargetListActivity", "list adapter set");


        targetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("position", (position));
                startActivity(i);
            }
        });
    }//end of onCreate()


    //Adapter class for listView............................
    private class TargetListViewAdapter extends ArrayAdapter<String> {

        private String name[], status[], imgAddr[];
        private Context context;

        public TargetListViewAdapter(Context context, String[] name, String[] status, String[] imgAddr) {
            super(context, R.layout.target_custom_row, name);
            Log.e("ListFrag/adapter class", "constructor called");
            this.name = name;
            this.status = status;
            this.imgAddr = imgAddr;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View adapterView = inflater.inflate(R.layout.target_custom_row, parent, false);

            TextView targetName = (TextView) adapterView.findViewById(R.id.target_name);
            TextView killStatus = (TextView) adapterView.findViewById(R.id.kill_status);
            ImageView targetImage = (ImageView) adapterView.findViewById(R.id.edit_image);
            LinearLayout targetInfoLinearLayout = (LinearLayout) adapterView.findViewById(R.id.target_info_linear_layout);

            targetName.setText(name[position]);
            killStatus.setText(status[position]);
            if (imgAddr[position] != null) {
               /* try {
                    Uri uri = Uri.parse(imgAddr[position]);
                    targetImage.setImageURI(uri);
                    targetImage.setBackgroundColor(Color.alpha(0));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error Loading Image", Toast.LENGTH_SHORT).show();
                    targetImage.setBackgroundColor(Color.alpha(100));
                    targetImage.setImageResource(R.mipmap.ic_launcher);
                }*/

                targetImage.setImageBitmap( decodeSampledbitmapFromFile(imgAddr[position], 55, 55));
            }


            if (status[position].matches("Killed")) {
                targetInfoLinearLayout.setBackgroundColor(Color.rgb(249, 27, 27));
            }

            return adapterView;
        }
    }//end of adapter class


    //Menu..........................
    private int getMenuId() {
        Log.e("MainActivity class", "getMeuId() called");
        DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);
        if (db.getNoOfItem() == 0)
            return R.menu.no_target_menu;
        else
            return R.menu.menu_activity_main_list_pager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("MainActivity class", "onCreateOptionsMenu() called");
           DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);
          if(db.getNoOfItem()==0)
              getMenuInflater().inflate(R.menu.no_target_menu, menu);
          else
              getMenuInflater().inflate(R.menu.menu_activity_main_list_pager, menu);

        return true;
    }//end of onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_menu_option:
                Intent i = new Intent(getApplicationContext(), AddNewTargetActivity.class);
                startActivity(i);
                return true;
            case R.id.edit_menu_option:
                startActivity(new Intent(getApplicationContext(), EditTargetInfoActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
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

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
