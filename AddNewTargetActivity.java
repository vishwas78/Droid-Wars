package com.example.vishwasmittal.droidwars;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;


public class AddNewTargetActivity extends AppCompatActivity {

    private int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private ImageView editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_target);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Target?");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TargetLayoutClass newTargetInfo = new TargetLayoutClass();
        editImage = (ImageView) findViewById(R.id.edit_image);
        final EditText editName = (EditText) findViewById(R.id.edit_name);
        final EditText editHouse = (EditText) findViewById(R.id.edit_house);
        final EditText editReason = (EditText) findViewById(R.id.edit_reason);
        final EditText editKillPlace = (EditText) findViewById(R.id.edit_kill_place);
        final EditText editKillWay = (EditText) findViewById(R.id.edit_kill_way);
        RadioGroup editStatus = (RadioGroup) findViewById(R.id.edit_status);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });

        editKillPlace.setText("Not Yet");
        editKillWay.setText("Not Yet");
        editKillPlace.setEnabled(false);
        editKillWay.setEnabled(false);

        editStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.not_killed){
                    newTargetInfo.set_status("Not Killed");
                    editKillPlace.setText("Not Yet");
                    editKillWay.setText("Not Yet");
                    editKillPlace.setEnabled(false);
                    editKillWay.setEnabled(false);
                }
                else{
                    editKillPlace.setEnabled(true);
                    editKillWay.setEnabled(true);
                    editKillPlace.setText("");
                    editKillWay.setText("");
                    newTargetInfo.set_status("Killed");
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editName.getText().toString().trim().matches("") || editHouse.getText().toString().trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Name and House are Mandatory", Toast.LENGTH_LONG).show();
                }
                else {
                    newTargetInfo.set_name(editName.getText().toString());
                    newTargetInfo.set_house(editHouse.getText().toString());
                    newTargetInfo.set_reason(editReason.getText().toString());
                    newTargetInfo.set_killWay(editKillWay.getText().toString());
                    newTargetInfo.set_killPlace(editKillPlace.getText().toString());
                    newTargetInfo.set_imgAddr(selectedImagePath);

                    Log.e("AddNewTargetActivity","database obj created");
                    DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);
                    db.addNewTarget(newTargetInfo);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Target Successfully Added", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("position", db.getNoOfItem());
                    finish();
                    startActivity(i);
                }
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Log.e("onActivityResult called", "error in this method");
                try {
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = String.valueOf(selectedImageUri);
                    File img = new File(selectedImagePath);
                    long length = img.length();
                    length /= 1024;
                    if(length > 30){
                        Log.e("length constraint", "error");
                        Toast.makeText(getApplicationContext(), "Error! Image too large", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.e("length constraint", "in else");
                        editImage.setImageURI(selectedImageUri);
                        editImage.setBackgroundColor(Color.alpha(0));
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error Loading Image", Toast.LENGTH_SHORT).show();
                    editImage.setBackgroundColor(Color.alpha(100));
                    editImage.setImageResource(R.mipmap.ic_launcher);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
