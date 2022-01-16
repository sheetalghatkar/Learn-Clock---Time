package com.mobiapps360.LearnClockTime;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.media.MediaPlayer;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobiapps360.LearnClockTime.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

//    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ImageView imgViewHomeGif;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imgViewHomeGif = findViewById(R.id.imgViewHomeGif);

        Glide.with(this).load(R.drawable.clock_learn).into(imgViewHomeGif);


        //   setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        int idSoundBg = getApplicationContext().getResources().getIdentifier("com.mobiapps360.LearnClockTime:raw/" + "bgmusic", null, null);
//        player = MediaPlayer.create(getBaseContext(), idSoundBg);
//       // player.start();
    }


    public void soundHomeBtnClicked(View v) {
       // Log.v("soundHomeBtnClicked", "i m inside homeBtnClicked");
//        if (sharedPreferences.contains(soundHomeActivity)) {
//            Boolean getSoundFlag = sharedPreferences.getBoolean(soundHomeActivity, false);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            getSoundFlag = !getSoundFlag;
//            editor.putBoolean(soundHomeActivity, getSoundFlag);
//            editor.commit();
//            if (getSoundFlag == true) {
//                playSound();
//                btnImgHomeSound.setImageResource(R.mipmap.sound_on);
//            } else {
//                btnImgHomeSound.setImageResource(R.mipmap.sound_off);
//                stopPlayerSound();
//            }
//        }
    }
    public void settingClicked(View v){
        // System.out.println("@@@@@@@@@@@@@@@Home clicked@@@@@@@@@@@");
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

//    @Override
//    public boolean onSupportNavigateUp() {
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}