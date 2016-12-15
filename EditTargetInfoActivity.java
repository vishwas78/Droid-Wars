package com.example.vishwasmittal.droidwars;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class EditTargetInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_target_info_fragment);


        //TODO: add the imageView for the target

        final EditText editName = (EditText) findViewById(R.id.edit_name);
        final EditText editHouse = (EditText) findViewById(R.id.edit_house);
        final EditText editReason = (EditText) findViewById(R.id.edit_reason);
        final EditText editKillPlace = (EditText) findViewById(R.id.edit_kill_place);
        final EditText editKillWay = (EditText) findViewById(R.id.edit_kill_way);
        final RadioGroup editStatus = (RadioGroup) findViewById(R.id.edit_status);
        final Button submitButton = (Button) findViewById(R.id.submit_button);
        final ImageView targetImage = (ImageView) findViewById(R.id.target_image);


        final String[][] spinnerInfo = new DataBaseClass(getApplicationContext(), null, null, 1).returnSpinnerList();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinnerInfo[1]);
        spinner.setAdapter(adapter);

        int position = 0;
        position = Integer.valueOf(spinnerInfo[0][position]);
        final Bundle getEditablePosition = getIntent().getExtras();
        if (getEditablePosition != null) {
            position = getEditablePosition.getInt("position", 0);
        }

        spinner.setSelection(position);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                final DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);

                Log.e("EditTargetInfoActivity", "database obj created");
                final TargetLayoutClass targetInfo = db.returnTargetInfo(Integer.valueOf(spinnerInfo[0][position]));

                editName.setText(targetInfo.get_name());
                editHouse.setText(targetInfo.get_house());
                editReason.setText(targetInfo.get_reason());
                editKillPlace.setText(targetInfo.get_killPlace());
                editKillWay.setText(targetInfo.get_killWay());
                String imageAddr = targetInfo.get_imgAddr();
                if (imageAddr != null) {
                    Uri uri = Uri.parse(imageAddr);
                    targetImage.setImageURI(uri);
                }
                if (targetInfo.get_status().matches("Killed")) {
                    editStatus.check(R.id.killed);
                }

                editStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.not_killed) {
                            targetInfo.set_status("Not Killed");
                            editKillPlace.setText("Not Yet");
                            editKillWay.setText("Not Yet");
                            editKillPlace.setFocusable(false);
                            editKillWay.setFocusable(false);
                        }
                        if (checkedId == R.id.killed) {
                            targetInfo.set_status("Killed");
                        }
                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().trim().matches("") || editHouse.getText().toString().trim().matches("")) {
                            Toast.makeText(getApplicationContext(), "Name and House are Mandatory", Toast.LENGTH_LONG).show();
                        } else {
                            targetInfo.set_name(editName.getText().toString());
                            targetInfo.set_house(editHouse.getText().toString());
                            targetInfo.set_reason(editReason.getText().toString());
                            targetInfo.set_killWay(editKillWay.getText().toString());
                            targetInfo.set_killPlace(editKillPlace.getText().toString());

                            db.updateTarget(targetInfo);
                            Toast.makeText(getApplicationContext(), "Target Info Successfully Updated", Toast.LENGTH_SHORT).show();
                            db.close();

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("position", db.getNoOfItem());
                            startActivity(i);
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
