package net.jamesempire.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView artistPorstrait = findViewById(R.id.imgArtist);
        ImageView logo = findViewById(R.id.imgLogo);
        ImageView artistName = findViewById(R.id.txtArtist);
        View line = findViewById(R.id.line);

        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        artistPorstrait.setAnimation(fade);

        Animation pushRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_right);
        logo.setAnimation(pushRight);
        line.setAnimation(pushRight);

        Animation pushLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_left);
        artistName.setAnimation(pushLeft);


        TimerTask splash = new TimerTask()
        {
            @Override
            public void run()
            {
                finish();
                startActivity(new Intent(SplashScreen.this, MusicPlayer.class));
            }
        };
        Timer opening =new Timer();
        opening.schedule(splash,5000);
    }
}
