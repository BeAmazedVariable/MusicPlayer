package net.jamesempire.musicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgSong1,imgSong2;
    private MediaPlayer song1,song2;
    private SeekBar seekBarSong1,seekBarSong2;
    private ImageButton playSong1,forwardSong1,backwardSong1;
    private ImageButton playSong2,forwardSong2,backwardSong2;
    int lengthSong1,lengthSong2;
    private Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        imgSong1 = findViewById(R.id.imgSong1);
        seekBarSong1 = findViewById(R.id.seekBar1);
        playSong1 = findViewById(R.id.btnPlay1);
        forwardSong1 = findViewById(R.id.btnForward1);
        backwardSong1 = findViewById(R.id.btnBackward1);

        imgSong2 = findViewById(R.id.imgSong2);
        seekBarSong2 = findViewById(R.id.seekBar2);
        playSong2 = findViewById(R.id.btnPlay2);
        forwardSong2 = findViewById(R.id.btnForward2);
        backwardSong2 = findViewById(R.id.btnBackward2);

        handler = new Handler();

        playSong1.setOnClickListener(this);
        forwardSong1.setOnClickListener(this);
        backwardSong1.setOnClickListener(this);

        playSong2.setOnClickListener(this);
        forwardSong2.setOnClickListener(this);
        backwardSong2.setOnClickListener(this);

        song1 = MediaPlayer.create(getApplicationContext(),R.raw.song1);
        lengthSong1 = song1.getDuration();
        song1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBarSong1.setMax(lengthSong1);
                changeSeekBar(seekBarSong1,song1);
            }
        });
        seekBarSong1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    song1.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        song2 = MediaPlayer.create(getApplicationContext(),R.raw.song2);
        lengthSong2 = song2.getDuration();
        song2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBarSong2.setMax(lengthSong2);
                changeSeekBar(seekBarSong2,song2);
            }
        });
        seekBarSong2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    song2.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }

    private void changeSeekBar(final SeekBar seekBar,final MediaPlayer music)
    {
        seekBar.setProgress(music.getCurrentPosition());
        if (music.isPlaying())
        {
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeSeekBar(seekBar,music);
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }

    @Override
    public void onClick(View v) {
        Animation rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        switch (v.getId()) {
            case R.id.btnPlay1:
                if (song1.isPlaying()) {
                    song1.pause();
                    imgSong1.clearAnimation();
                }
                else {
                    song1.start();
                    imgSong1.startAnimation(rotate);
                    changeSeekBar(seekBarSong1,song1);
                }
                break;
            case R.id.btnForward1:
                song1.seekTo(song1.getCurrentPosition() + 5000);
                break;
            case R.id.btnBackward1:
                song1.seekTo(song1.getCurrentPosition() - 5000);
                break;
            case R.id.btnPlay2:
                if (song2.isPlaying()) {
                    song2.pause();
                    imgSong2.clearAnimation();
                }
                else {
                    song2.start();
                    changeSeekBar(seekBarSong2,song2);
                    imgSong2.startAnimation(rotate);
                }
                break;
            case R.id.btnForward2:
                song2.seekTo(song2.getCurrentPosition() + 5000);
                break;
            case R.id.btnBackward2:
                song2.seekTo(song2.getCurrentPosition() - 5000);
                break;
        }
    }
}
