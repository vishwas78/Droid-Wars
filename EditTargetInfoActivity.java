package com.example.vishwasmittal.droidwars;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class EditTargetInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editName, editHouse, editReason, editKillPlace, editKillWay;
    private RadioGroup editStatus;
    private ImageView editImage;
    private Button submitButton;
    private String[][] spinnerInfo;
    private int SELECT_PICTURE = 1;     //for editImage.setOnClickListener calling the gallery
    private String selectedImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_target_info_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Update info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.e("Edit class", "toolbar set");

        //TODO: add the imageView for the target

        editName = (EditText) findViewById(R.id.edit_name);
        editHouse = (EditText) findViewById(R.id.edit_house);
        editReason = (EditText) findViewById(R.id.edit_reason);
        editKillPlace = (EditText) findViewById(R.id.edit_kill_place);
        editKillWay = (EditText) findViewById(R.id.edit_kill_way);
        editStatus = (RadioGroup) findViewById(R.id.edit_status);
        submitButton = (Button) findViewById(R.id.submit_button);
        editImage = (ImageView) findViewById(R.id.edit_image);
        Log.e("Edit class", "found views by ids");

        spinnerInfo = new DataBaseClass(getApplicationContext(), null, null, 1).returnSpinnerList();
        /* *spinnerInfo[0] contains the

         */

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, R.id.spinner_textView, spinnerInfo[1]);
        spinner.setAdapter(adapter);

        int position = 0;
        final Bundle getEditablePosition = getIntent().getExtras();
        if (getEditablePosition != null) {
            Log.e("Edit class", "inside if");
            position = getEditablePosition.getInt("position", 0);
            Log.e("Edit class if", "position is: " + position);
        }
        ViewGroup viewGroup = null;
        AdapterView adapterView = null;
        spinner.setOnItemSelectedListener(this);
        //onItemSelected(adapterView, View.inflate(this, R.layout.spinner_list_item, viewGroup), position, 0 );
        spinner.setSelection(position);

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                Log.e("Edit class", "item selected listener called");
                final DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);

                Log.e("EditTargetInfoActivity", "database obj created");
                final TargetLayoutClass targetInfo = db.returnTargetInfo(Integer.valueOf(spinnerInfo[0][position]));

                editName.setText(targetInfo.get_name());
                editHouse.setText(targetInfo.get_house());
                editReason.setText(targetInfo.get_reason());
                editKillPlace.setText(targetInfo.get_killPlace());
                editKillWay.setText(targetInfo.get_killWay());
                String imageAddr = targetInfo.get_imgAddr();
                final String KillPlaceString = targetInfo.get_killPlace();
                final String KillWayString = targetInfo.get_killPlace();

                if (imageAddr != null) {
                    Uri uri = Uri.parse(imageAddr);
                    targetImage.setImageURI(uri);
                }
                if (targetInfo.get_status().matches("Killed")) {
                    editStatus.check(R.id.killed);
                }

                if (editStatus.getCheckedRadioButtonId() == R.id.not_killed) {
                    editKillPlace.setEnabled(false);
                    editKillWay.setEnabled(false);
                }
                editStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.not_killed) {
                            targetInfo.set_status("Not Killed");
                            editKillPlace.setText("Not Yet");
                            editKillWay.setText("Not Yet");
                            editKillPlace.setEnabled(false);
                            editKillWay.setEnabled(false);
                        }
                        if (checkedId == R.id.killed) {
                            targetInfo.set_status("Killed");
                            editKillPlace.setEnabled(true);
                            editKillWay.setEnabled(true);
                            editKillPlace.setText(KillPlaceString);
                            editKillWay.setText(KillWayString);

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
                            i.putExtra("position", position);
                            startActivity(i);
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
    }


    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        Log.e("Edit class", "item selected listener called, position: " + position);
        final DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);

        Log.e("EditTargetInfoActivity", "database obj created");
        final TargetLayoutClass targetInfo = db.returnTargetInfo(Integer.valueOf(spinnerInfo[0][position]));

        editName.setText(targetInfo.get_name());
        editHouse.setText(targetInfo.get_house());
        editReason.setText(targetInfo.get_reason());
        editKillPlace.setText(targetInfo.get_killPlace());
        editKillWay.setText(targetInfo.get_killWay());
        String imageAddr = targetInfo.get_imgAddr();
        final String KillPlaceString = targetInfo.get_killPlace();
        final String KillWayString = targetInfo.get_killPlace();

        if (imageAddr != null) {
            Uri uri = Uri.parse(imageAddr);
            editImage.setImageURI(uri);
            editImage.setBackgroundColor(Color.alpha(0));
        }
        if (targetInfo.get_status().matches("Killed")) {
            editStatus.check(R.id.killed);
        }

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

        if (editStatus.getCheckedRadioButtonId() == R.id.not_killed) {
            editKillPlace.setEnabled(false);
            editKillWay.setEnabled(false);
        }
        editStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.not_killed) {
                    targetInfo.set_status("Not Killed");
                    editKillPlace.setText("Not Yet");
                    editKillWay.setText("Not Yet");
                    editKillPlace.setEnabled(false);
                    editKillWay.setEnabled(false);
                }
                if (checkedId == R.id.killed) {
                    targetInfo.set_status("Killed");
                    editKillPlace.setEnabled(true);
                    editKillWay.setEnabled(true);
                    editKillPlace.setText(KillPlaceString);
                    editKillWay.setText(KillWayString);

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
                    targetInfo.set_imgAddr(selectedImagePath);

                    db.updateTarget(targetInfo);
                    Toast.makeText(getApplicationContext(), "Target Info Successfully Updated", Toast.LENGTH_SHORT).show();
                    db.close();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("position", position);
                    startActivity(i);
                }
            }
        });
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                try {
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = String.valueOf(selectedImageUri);
                    editImage.setImageURI(selectedImageUri);
                    editImage.setBackgroundColor(Color.alpha(0));
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
