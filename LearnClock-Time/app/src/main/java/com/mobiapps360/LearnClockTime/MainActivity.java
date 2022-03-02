package com.mobiapps360.LearnClockTime;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import com.mobiapps360.LearnClockTime.databinding.ActivityMainBinding;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ImageView imgViewHomeGif;
    private ImageButton btnImgSetting;
    private ImageButton btnImgHomeSound;
    private View viewSettingBg;
    private ImageView imgViewsettingBg;
    private ImageView imgViewBottom;
    private ImageView imgViewWallBg;
    MediaPlayer player;
    private AdView mAdView;
    AdRequest adRequest;
    public static ArrayList<GuessTimeItem>  guessTimeDataArray;
    public static ArrayList<GuessTimeItem>  guessTimeFinalArray;
    public static SharedPreferences sharedPreferences = null;
    public static final String myPreferences = "myPref";
    public static final String soundHomeActivity = "soundHomeActivityKey";
    // for all the FABs
    FloatingActionButton fab_setting, fab_img_rateUs, fab_img_shareApp,fab_img_otherApps, fab_img_privacyPolicy;
    // with FABs
    TextView fab_txt_rateUs, fab_txt_shareApp, fab_txt_otherApps, fab_txt_privacyPolicy;
    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.v("xxx","Inside getSoundFlag loop----");

        imgViewHomeGif = findViewById(R.id.imgViewHomeGif);
        btnImgHomeSound = findViewById(R.id.btnSoundHomeScreen);
        btnImgSetting = findViewById(R.id.btnSetting);
        imgViewWallBg = findViewById(R.id.mainWallImage);
        viewSettingBg = findViewById(R.id.viewSettingBg);
        imgViewsettingBg = findViewById(R.id.imgViewSettingBg);

        Glide.with(this).load(R.drawable.clock_learn).into(imgViewHomeGif);

        MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewBannerMainActivity);
//        adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                // Toast.makeText(MainActivity.this,"ad loaded",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                System.out.println("Show error####"+adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.contains(soundHomeActivity)){
            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false) ;
            if (getSoundFlag == true) {
                playSound();
                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
            } else {
                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
            }
        } else {
            editor.putBoolean(soundHomeActivity, true);
            editor.commit();
            btnImgHomeSound.setImageResource(R.mipmap.sound_on);
            playSound();
        }
        guessTimeDataArray = parseGuessTimeArray("guess_time");

        viewSettingBg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isAllFabsVisible) {
                    fab_img_rateUs.hide();
                    fab_img_shareApp.hide();
                    fab_img_otherApps.hide();
                    fab_img_privacyPolicy.hide();
                    fab_txt_rateUs.setVisibility(View.GONE);
                    fab_txt_shareApp.setVisibility(View.GONE);
                    fab_txt_otherApps.setVisibility(View.GONE);
                    fab_txt_privacyPolicy.setVisibility(View.GONE);
                    isAllFabsVisible = false;
                    imgViewsettingBg.setVisibility(View.INVISIBLE);
                    viewSettingBg.setVisibility(View.INVISIBLE);
                }
            }
        });
        // This FAB button is the Parent
        fab_setting = findViewById(R.id.fab_btn_setting);
        // FAB button
        fab_img_rateUs = findViewById(R.id.fab_img_rateUs);
        fab_img_shareApp = findViewById(R.id.fab_img_shareApp);
        fab_img_otherApps = findViewById(R.id.fab_img_otherApps);
        fab_img_privacyPolicy = findViewById(R.id.fab_img_privacyPolicy);

        // Also register the action name text, of all the FABs.
        fab_txt_rateUs = findViewById(R.id.fab_txt_rateUs);
        fab_txt_shareApp = findViewById(R.id.fab_txt_shareApp);
        fab_txt_otherApps = findViewById(R.id.fab_txt_otherApps);
        fab_txt_privacyPolicy = findViewById(R.id.fab_txt_privacyPolicy);
        // Now set all the FABs and all the action name
        // texts as GONE
        fab_img_rateUs.setVisibility(View.GONE);
        fab_img_shareApp.setVisibility(View.GONE);
        fab_img_otherApps.setVisibility(View.GONE);
        fab_img_privacyPolicy.setVisibility(View.GONE);
        fab_txt_rateUs.setVisibility(View.GONE);
        fab_txt_shareApp.setVisibility(View.GONE);
        fab_txt_otherApps.setVisibility(View.GONE);
        fab_txt_privacyPolicy.setVisibility(View.GONE);
        imgViewsettingBg.setVisibility(View.INVISIBLE);
        viewSettingBg.setVisibility(View.INVISIBLE);
        ViewCompat.setTranslationZ(viewSettingBg, 10);
        ViewCompat.setTranslationZ(imgViewsettingBg, 11);
        ViewCompat.setTranslationZ(fab_txt_rateUs, 15);
        ViewCompat.setTranslationZ(fab_txt_shareApp, 15);
        ViewCompat.setTranslationZ(fab_txt_otherApps, 15);
        ViewCompat.setTranslationZ(fab_txt_privacyPolicy, 15);


        fab_setting.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        ((FloatingActionButton)v).setAlpha((float) 0.5);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        ((FloatingActionButton)v).setAlpha((float) 1.0);
                        if (!isAllFabsVisible) {
                            fab_img_rateUs.show();
                            fab_img_shareApp.show();
                            fab_img_otherApps.show();
                            fab_img_privacyPolicy.show();
                            fab_txt_rateUs.setVisibility(View.VISIBLE);
                            fab_txt_shareApp.setVisibility(View.VISIBLE);
                            fab_txt_otherApps.setVisibility(View.VISIBLE);
                            fab_txt_privacyPolicy.setVisibility(View.VISIBLE);
                            imgViewsettingBg.setVisibility(View.VISIBLE);
                            viewSettingBg.setVisibility(View.VISIBLE);
                            isAllFabsVisible = true;
                        } else {
                            closeFab();
                        }
                    }
                }
                return true;
            }

        });

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        fab_img_shareApp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this, "Person Added", Toast.LENGTH_SHORT).show();
                    }
                });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        fab_img_rateUs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("sddas","aala");
                       // Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart invoked");
        if(sharedPreferences.contains(soundHomeActivity)){
            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false) ;
            if (getSoundFlag == true) {
                playSound();
            } else {
                stopPlayerSound();
            }
        } else {
            stopPlayerSound();
        }
    }

    public void settingClicked(View v){
         System.out.println("@@@@@@@@@@@@@@@Home clicked@@@@@@@@@@@");
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public void shareAppClicked(View v) {
        closeFab();
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, Constant.APP_PNAME);
            String shareMessage= "Best app for kids to learn clock.\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + Constant.BUNDLE_ID;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
    public void rateAppTabClicked(View v) {
        System.out.println("-------rateAppTabClicked---------------"+getPackageName());
        try {
            Uri uri = Uri.parse("market://details?id="+getPackageName()+"");
            Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goMarket);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()+"");
            Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goMarket);
        }
    }

    public void privacyTabClicked(View v) {
        System.out.println("@@@@@@@@@@@@@@@privacyTabClicked clicked@@@@@@@@@@@");
        Log.d("MyApp","I am here");

        if (isOnline()) {
            Intent intent = new Intent(this, PrivacyPolicyActivity.class);
            this.startActivity(intent);
        } else {
            closeFab();
         //   Toast.makeText(MainActivity.this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }
    public void learnClockClicked(View v) {
        System.out.println("@@@@@@@@@@@@@@@learnClockClicked clicked@@@@@@@@@@@");
        Intent intent = new Intent(this, LearnClockActivity.class);
        startActivity(intent);
    }
    public void guessTimeClicked(View v) {
        System.out.println("@@@@@@@@@@@@@@@guessTimeClicked clicked@@@@@@@@@@@");
        guessTimeFinalArray = createRandomTimeArray();
        Intent intent = new Intent(this, GuessTimeActivity.class);
        startActivity(intent);
    }
    public void setTimeClicked(View v) {
       // System.out.println("@@@@@@@@@@@@@@@setTimeClicked clicked@@@@@@@@@@@");
        Intent intent = new Intent(this, SetTimeActivity.class);
        startActivity(intent);
    }
    public void playWithClockClicked(View v) {
       // System.out.println("@@@@@@@@@@@@@@@playWithClockClicked clicked@@@@@@@@@@@");
    }
    public void otherAppsTabClicked(View v) {
      //  System.out.println("@@@@@@@@@@@@@@@Our other apps clicked@@@@@@@@@@@");
    }

    public void closeFab(){
        if (isAllFabsVisible) {
            fab_img_rateUs.hide();
            fab_img_shareApp.hide();
            fab_img_otherApps.hide();
            fab_img_privacyPolicy.hide();
            fab_txt_rateUs.setVisibility(View.GONE);
            fab_txt_shareApp.setVisibility(View.GONE);
            fab_txt_otherApps.setVisibility(View.GONE);
            fab_txt_privacyPolicy.setVisibility(View.GONE);
            isAllFabsVisible = false;
            imgViewsettingBg.setVisibility(View.INVISIBLE);
            viewSettingBg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void stopPlayerSound() {
        if (player != null) {
            player.stop();
        }
    }
    public void soundHomeBtnClicked(View v) {
        Log.v("soundHomeBtnClicked", "i m inside homeBtnClicked");
        if (sharedPreferences.contains(soundHomeActivity)) {
            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            getSoundFlag = !getSoundFlag;
            editor.putBoolean(soundHomeActivity, getSoundFlag);
            editor.commit();
            if (getSoundFlag == true) {
                playSound();
                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
            } else {
                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
                stopPlayerSound();
            }
        }
    }
    public  void playSound() {
        if (MainActivity.sharedPreferences.getBoolean(soundHomeActivity, false)) {
            if (player != null) {
                player.stop();
                //  player.release();
            }
            int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "bgmusic", null, null);
            player = MediaPlayer.create(getBaseContext(), idSoundBg);
            player.start();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // System.out.println("@@@@-----------------On completion-----------------@@@@@@");
                if (MainActivity.sharedPreferences.getBoolean(soundHomeActivity, false)) {
                    player.start();
                }
            }

        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        closeFab();
        if (player != null) {
            player.stop();
        }
        // Log.i("eerrr","onStop########");
    }
    public ArrayList<GuessTimeItem> createRandomTimeArray() {
        int maxCount = guessTimeDataArray.size();
        guessTimeFinalArray =  new ArrayList<GuessTimeItem>();
        ArrayList<Integer> selectedArrayOfIndex = new ArrayList<Integer>();
        for (int i = 0; i < maxCount; i++) {

            Boolean isQuestionSelected = false;
            do {
                Random rand = new Random();
                int random = rand.nextInt(maxCount);
                if (!(selectedArrayOfIndex.contains(random))) {
                    selectedArrayOfIndex.add(random);
                    guessTimeFinalArray.add(guessTimeDataArray.get(random));
                    isQuestionSelected = true;
                }

            } while (!isQuestionSelected);
        }
        return guessTimeFinalArray;
    }

    public ArrayList parseGuessTimeArray(String fileName) {
        //System.out.println("parseGuessTimeArray****");

        String strJson = null;
        ArrayList<GuessTimeItem> guessTimeDataArray;
        guessTimeDataArray = new ArrayList<>();

        try {
            strJson = readFile(fileName+".json");

            String data = "";
            try {

                // Create the root JSONObject from the JSON string.
                JSONObject jsonRootObject = new JSONObject(strJson);
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray(fileName);
              //  System.out.println("jsonArray#####"+jsonArray);

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    try {
                            //  System.out.println("jsonObject#####"+jsonObject);
                            JSONArray jOptions = jsonObject.getJSONArray("options");
                            ArrayList<String> listOptions = new ArrayList<String>();
                            for (int iCnt=0; iCnt<jOptions.length(); iCnt++) {

                                if (jOptions.length() != 0) {
                                    listOptions.add(jOptions.getString(iCnt));
                                }
                            }
                            GuessTimeItem guessTimeItem = new GuessTimeItem(jsonObject.getInt("hour"), jsonObject.getInt("minute"), listOptions,jsonObject.getInt("answer"));
                          //  System.out.println("guessTimeItem!!!!!"+guessTimeItem);
                            //  System.out.println("yyyyyyy"+quizItem.arrayCaption);
                            guessTimeDataArray.add(guessTimeItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
             //   System.out.println("----guessTimeDataArray---"+guessTimeDataArray);
                return guessTimeDataArray;
            } catch (JSONException e) {e.printStackTrace();}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return guessTimeDataArray;
    }

    public String readFile(String fileName) throws IOException
    {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName), "UTF-8"));

        String content = "";
        String line;
        while ((line = reader.readLine()) != null)
        {
            content = content + line;
        }
        return content;
    }
    public void onActivityPaused(Activity activity) {
        System.out.println("onActivityPaused------");
        if (player != null) {
            player.release();
        }
    }

    public void onActivityStopped(Activity activity) {
        System.out.println("onActivityStopped------");
        if (player != null) {
            player.release();
        }
    }
}