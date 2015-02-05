package com.example.shivram.notate;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
    ActionBar actionBar;
    ViewPager viewPager;
    public static String filepath="Nothing";                          // Filepath of File selected in FragmentB
    public static String fileToMakeWave="Nothing";
    public static SeekBar seekBar;
    public static ArrayList<Float> yinValues;
    public static boolean loaded=false;
    public static TextView textToChange;
    public static Button playButton;
    public static Button pauseButton;
    public static int bufferSize;
    public static int audioSource = MediaRecorder.AudioSource.MIC;    // Audio source is the device MIC
    public static int channelConfig = AudioFormat.CHANNEL_IN_MONO;    // Recording in mono
    public static int audioEncoding = AudioFormat.ENCODING_PCM_16BIT; // Records in 16bit
    public static int blockSize = 1024;                               // deal with this many samples at a time
    public static int sampleRate = 44100;
    public static View contentViewC;
    public static int count2=0;
    public static boolean pause=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        ActionBar aBar = getActionBar();
        aBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
        setContentView(R.layout.activity_main);
        viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);
            }
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //Creaating Tabs
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Record");
        tab1.setTabListener(this);
        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Files");
        tab2.setTabListener(this);
        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText("Notate");
        tab3.setTabListener(this);
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
class MyAdapter extends FragmentPagerAdapter{
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        if(i==0)
        {
          fragment=new FragmentA();
        }
        if(i==1)
        {
            fragment=new FragmentB();
        }
        if(i==2)
        {
            fragment=new FragmentC();
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return 3;
    }
}