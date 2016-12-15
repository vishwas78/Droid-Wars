package com.example.vishwasmittal.droidwars;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TargetListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TargetListFragment","onCreateView()");
        View rootView = inflater.inflate(R.layout.target_list_fragment, container, false);

        String[][] listData = new String[3][];                               //handling the received data from parent class
        Bundle listDataBundle = getArguments();
        listData[0] = listDataBundle.getStringArray("_name");
        listData[1] = listDataBundle.getStringArray("_status");
        listData[2] = listDataBundle.getStringArray("_imgAddr");

        ListView targetListView = (ListView) rootView.findViewById(R.id.target_list_view);
        TargetListViewAdapter listViewAdapter = new TargetListViewAdapter(getContext(), listData[0], listData[1], listData[2]);
        targetListView.setAdapter(listViewAdapter);

        targetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getContext(), MainActivity.class);
                i.putExtra("position", (position+1));
                startActivity(i);
            }
        });


        return rootView;
    }//end of onCreateView()



    //Adapter class for listView
    private class TargetListViewAdapter extends ArrayAdapter<String>{

        private String name[], status[], imgAddr[];

        public TargetListViewAdapter(Context context, String[] name, String[] status, String[] imgAddr) {
            super(context, R.layout.target_custom_row, name);
            this.name = name;
            this.status = status;
            this.imgAddr = imgAddr;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getContext(), R.layout.target_custom_row, parent);

            TextView targetName = (TextView) convertView.findViewById(R.id.target_name);
            TextView killStatus = (TextView) convertView.findViewById(R.id.kill_status);
            ImageView targetImage = (ImageView) convertView.findViewById(R.id.target_image);
            LinearLayout targetInfoLinearLayout = (LinearLayout) convertView.findViewById(R.id.target_info_linear_layout);

            targetName.setText(name[position]);
            killStatus.setText(status[position]);
            if (imgAddr[position] != null) {
                Uri uri = Uri.parse(imgAddr[position]);
                targetImage.setImageURI(uri);
            }

            if(status[position] == "Killed"){
                targetInfoLinearLayout.setBackgroundColor(Color.rgb(200, 100, 100));
            }

            return convertView;
        }
    }//end of adapter class

}//end of outer class
