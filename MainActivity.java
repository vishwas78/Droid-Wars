package com.example.vishwasmittal.droidwars;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;

    private int NO_OF_PAGES;
    private int pagerCurrentPosition;
    private int[] _idList;
    private String[][] listData;
    private TargetLayoutClass targetInfo;
    private DataBaseClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity class", "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("MainActivity class", "db object created");
        db = new DataBaseClass(getApplicationContext(), null, null, 1);
        NO_OF_PAGES = db.getNoOfItem() + 1;
     //   _idList = new int[NO_OF_PAGES-1];
        _idList = db.return_idList();
     //   listData = new String[3][NO_OF_PAGES-1];
        listData = db.returnListItems();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapterClass pagerAdapter = new PagerAdapterClass(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        Log.e("MainActivity class", "pager adapter Set");

        Bundle setPage = getIntent().getExtras();
        if (setPage != null) {
            viewPager.setCurrentItem(setPage.getInt("position", 0));
        }

    }//end of onCreate();

    @Override
    public void onBackPressed() {
        Log.e("MainActivity class", "onBackPressed() called");
        if (pagerCurrentPosition == 0) {
            super.onBackPressed();
        } else viewPager.setCurrentItem(0);
    }


    //Adapter Class............................
    private class PagerAdapterClass extends FragmentStatePagerAdapter {

        public PagerAdapterClass(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("MainActivity: Adapter", "getItem() called");
            pagerCurrentPosition = position;

            if (position == 0) {  //calling the list fragment
                Bundle listDataBundle = new Bundle();
                listDataBundle.putStringArray("_name", listData[0]);
                listDataBundle.putStringArray("_status", listData[1]);
                listDataBundle.putStringArray("_imgAddr", listData[2]);
                TargetListFragment listFragment = new TargetListFragment();
                listFragment.setArguments(listDataBundle);
                return listFragment;
            }
            else {       ///calling the info pager
                Bundle targetPagerInfoBundle = new Bundle();
                targetInfo = new TargetLayoutClass();
                targetInfo = db.returnTargetInfo(_idList[position-1]);
                targetPagerInfoBundle.putInt("_id", targetInfo.get_id());
                targetPagerInfoBundle.putString("_name", targetInfo.get_name());
                targetPagerInfoBundle.putString("_house", targetInfo.get_house());
                targetPagerInfoBundle.putString("_reason", targetInfo.get_reason());
                targetPagerInfoBundle.putString("_status", targetInfo.get_status());
                targetPagerInfoBundle.putString("_killPlace", targetInfo.get_killPlace());
                targetPagerInfoBundle.putString("_killWay", targetInfo.get_killWay());
                targetPagerInfoBundle.putString("_imgAddr", targetInfo.get_imgAddr());
                TargetPagerFragment targetPager = new TargetPagerFragment();
                targetPager.setArguments(targetPagerInfoBundle);
                return targetPager;
            }
        }//end of getItem()

        @Override
        public int getCount() {
            return NO_OF_PAGES;
        }
    }//end of adapter class


    //Menu........................
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("MainActivity class", "onCreateOptionsMenu() called");
        if (pagerCurrentPosition == 0) {
            getMenuInflater().inflate(R.menu.menu_activity_main_list_pager, menu);
            return true;
        } else if (pagerCurrentPosition == (NO_OF_PAGES - 1) || pagerCurrentPosition == (NO_OF_PAGES - 2)) {
            return false;
        } else {
            getMenuInflater().inflate(R.menu.menu_activity_main_target_pager, menu);
            return true;
        }//end of if-else ladder
    }//end of onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_menu_option:
                Intent i = new Intent(getApplicationContext(), AddNewTargetActivity.class);
                startActivity(i);
                return true;
            case R.id.edit_menu_option: Intent i2 = new Intent(getApplicationContext(), EditTargetInfoActivity.class);
                i2.putExtra("position", pagerCurrentPosition-1);
                finish();
                startActivity(i2);
                return true;
            case R.id.delete_menu_option: db.deleteTarget(getApplicationContext(), _idList[pagerCurrentPosition-1]);
                //TODO: call database function to delete selected target and restart pager with position-1 page displaying
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//end of onOptionsItemSelected()
}//end of main class

