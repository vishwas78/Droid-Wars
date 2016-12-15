package com.example.vishwasmittal.droidwars;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class AddNewTargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_target);

        final TargetLayoutClass newTargetInfo = new TargetLayoutClass();

        //TODO: add the imageView for the target

        final EditText editName = (EditText) findViewById(R.id.edit_name);
        final EditText editHouse = (EditText) findViewById(R.id.edit_house);
        final EditText editReason = (EditText) findViewById(R.id.edit_reason);
        final EditText editKillPlace = (EditText) findViewById(R.id.edit_kill_place);
        final EditText editKillWay = (EditText) findViewById(R.id.edit_kill_way);
        RadioGroup editStatus = (RadioGroup) findViewById(R.id.edit_status);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        editStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.not_killed){
                    newTargetInfo.set_status("Not Killed");
                    editKillPlace.setText("Not Yet");
                    editKillWay.setText("Not Yet");
                    editKillPlace.setFocusable(false);
                    editKillWay.setFocusable(false);
                }
                if(checkedId == R.id.killed){
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

}
