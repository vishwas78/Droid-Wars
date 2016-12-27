package com.example.vishwasmittal.droidwars;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private int NO_OF_PAGES;
    private int[] _idList;
    private DataBaseClass db;
    private String[] namesForTabs;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity class", "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   toolbar.inflateMenu(R.menu.menu_activity_main_target_pager);
        toolbar.setTitle("Your Prey");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.e("MainActivity class", "db object created");
        db = new DataBaseClass(getApplicationContext(), null, null, 1);
        _idList = db.return_idList();
        NO_OF_PAGES = db.getNoOfItem();
        namesForTabs = db.getNames();


        viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapterClass pagerAdapter = new PagerAdapterClass(getSupportFragmentManager());
        Log.e("Main Activity Class", "setting the adapter");
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Bundle setPage = getIntent().getExtras();
        if (setPage != null) {
            viewPager.setCurrentItem(setPage.getInt("position", 0));
        }

    }//end of onCreate();


    //Adapter Class............................
    private class PagerAdapterClass extends FragmentStatePagerAdapter {

        public PagerAdapterClass(FragmentManager fm) {
            super(fm);
            Log.e("Pager Adapter class", "inside constructor");
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("MainActivity: Adapter", "getItem() called " + position);

            Bundle targetPagerInfoBundle = new Bundle();
                targetPagerInfoBundle.putInt("_id", _idList[position]);
                TargetPagerFragment targetPager = new TargetPagerFragment();
                targetPager.setArguments(targetPagerInfoBundle);
                return targetPager;
        }//end of getItem()

        @Override
        public int getCount() {
            return NO_OF_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return namesForTabs[position];
        }
    }//end of adapter class.....................


    //Menu........................
    private int getMenuId(){
        DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);
        NO_OF_PAGES = db.getNoOfItem();
        if(NO_OF_PAGES==0)
            return R.menu.no_target_menu;
        else
            return R.menu.menu_activity_main_target_pager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("MainActivity class", "onCreateOptionsMenu() called");
        DataBaseClass db = new DataBaseClass(getApplicationContext(), null, null, 1);
        if(db.getNoOfItem()==0)
            getMenuInflater().inflate(R.menu.no_target_menu, menu);
        else
            getMenuInflater().inflate(R.menu.menu_activity_main_target_pager, menu);

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
            case R.id.edit_menu_option: Intent i2 = new Intent(this, EditTargetInfoActivity.class);
                i2.putExtra("position", viewPager.getCurrentItem());
                finish();
                startActivity(i2);
                return true;
            case R.id.delete_menu_option:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Caution!")
                        .setMessage("This target will be removed...")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteTarget(getApplicationContext(), _idList[viewPager.getCurrentItem()]);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Operation Cancelled", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .create()
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//end of onOptionsItemSelected()

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), TargetListActivity.class));
    }

}//end of main class
