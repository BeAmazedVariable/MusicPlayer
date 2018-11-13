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
    //Declare the variable we need to use
    private ImageView imgSong1,imgSong2;
    private MediaPlayer song1,song2;
    private SeekBar seekBarSong1,seekBarSong2;
    private ImageButton playSong1,forwardSong1,backwardSong1;
    private ImageButton playSong2,forwardSong2,backwardSong2;
    int lengthSong1,lengthSong2;
    private Handler handler;  //A handler is and thread manager which queue the thread in the background (organize purpose)
    Runnable runnable;        //Create a new thread
    int[] group1 = {R.id.btnPlay1,R.id.btnForward1,R.id.btnBackward1,R.id.txtTitle1,R.id.seekBar1,R.id.imgSong1};
    int[] group2 = {R.id.btnPlay2,R.id.btnForward2,R.id.btnBackward2,R.id.txtTitle2,R.id.seekBar2,R.id.imgSong2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        //Define the variable with appropriate component on the layout for the first song
        imgSong1 = findViewById(R.id.imgSong1);
        seekBarSong1 = findViewById(R.id.seekBar1);
        playSong1 = findViewById(R.id.btnPlay1);
        forwardSong1 = findViewById(R.id.btnForward1);
        backwardSong1 = findViewById(R.id.btnBackward1);

        //Define the variable with appropriate component on the layout for the second song
        imgSong2 = findViewById(R.id.imgSong2);
        seekBarSong2 = findViewById(R.id.seekBar2);
        playSong2 = findViewById(R.id.btnPlay2);
        forwardSong2 = findViewById(R.id.btnForward2);
        backwardSong2 = findViewById(R.id.btnBackward2);

        //Create a new handler to manage the thread
        handler = new Handler();

        //Group the image button for the first and second song into one group
        playSong1.setOnClickListener(this);
        forwardSong1.setOnClickListener(this);
        backwardSong1.setOnClickListener(this);
        playSong2.setOnClickListener(this);
        forwardSong2.setOnClickListener(this);
        backwardSong2.setOnClickListener(this);

        //Define the variable with an appropriate song
        song1 = MediaPlayer.create(getApplicationContext(),R.raw.song1);
        song2 = MediaPlayer.create(getApplicationContext(),R.raw.song2);

        //Get the duration of each song
        lengthSong1 = song1.getDuration();
        lengthSong2 = song2.getDuration();

        //Set the media and seek bar to be ready for playing purpose
        song1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Set the length of the seek bar match the length of the song
                seekBarSong1.setMax(lengthSong1);

                //Change the seek bar follow the current position
                changeSeekBar(seekBarSong1,song1);
            }
        });

        seekBarSong1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Seek to the position follow by follow the user input
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

        //Set the media and seek bar to be ready for playing purpose
        song2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Set the length of the seek bar match the length of the song
                seekBarSong2.setMax(lengthSong2);

                //Change the seek bar follow the current position
                changeSeekBar(seekBarSong2,song2);
            }
        });
        seekBarSong2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Seek to the position follow by follow the user input
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
    //A thread to change the seek bar to the current position
    private void changeSeekBar(final SeekBar seekBar,final MediaPlayer music)
    {
        //Set the seek bar progress to the current position
        seekBar.setProgress(music.getCurrentPosition());

        //Create a loop that call the thread every 1 sec to check for the seek bar
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

    //Determine the action will happen when the user click on these image buttons
    @Override
    public void onClick(View v) {

        Animation rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        switch (v.getId()) {
            case R.id.btnPlay1:
                //Pause the music
                if (song1.isPlaying()) {
                    song1.pause();

                    //Stop the disc from rotating
                    imgSong1.clearAnimation();

                    //Set the play button
                    playSong1.setImageResource(R.drawable.play);

                    //Set the components for song 2 to become visible
                    for (int i =0; i<6;i++)
                    {
                        findViewById(group2[i]).setVisibility(View.VISIBLE);
                    }
                }
                //Start the music
                else {
                    song1.start();
                    //Start to rotate the disc
                    imgSong1.startAnimation(rotate);

                    //Start the thread to update seek bar
                    changeSeekBar(seekBarSong1,song1);

                    //Change to the pause image
                    playSong1.setImageResource(R.drawable.pause);

                    //Set the components for song 2 to become invisible
                    for (int i =0; i<6;i++)
                    {
                        findViewById(group2[i]).setVisibility(View.INVISIBLE);
                    }
                }
                break;
            //Move forward 5 secs from the current position
            case R.id.btnForward1:
                song1.seekTo(song1.getCurrentPosition() + 5000);
                break;
            //Move backward 5 secs from the current position
            case R.id.btnBackward1:
                song1.seekTo(song1.getCurrentPosition() - 5000);
                break;
            case R.id.btnPlay2:
                //Pause the music
                if (song2.isPlaying()) {
                    song2.pause();

                    //Stop the disc from rotating
                    imgSong2.clearAnimation();

                    //Set the play button
                    playSong2.setImageResource(R.drawable.play);

                    //Set the components for song 1 to become visible
                    for (int i =0; i<6;i++)
                    {
                        findViewById(group1[i]).setVisibility(View.VISIBLE);
                    }
                }
                //Start the music
                else {
                    song2.start();

                    //Start to rotate the disc
                    imgSong2.startAnimation(rotate);

                    //Change to the pause image
                    playSong2.setImageResource(R.drawable.pause);

                    //Start the thread to update seek bar
                    changeSeekBar(seekBarSong2,song2);

                    //Set the components for song 1 to become invisible
                    for (int i =0; i<6;i++)
                    {
                        findViewById(group1[i]).setVisibility(View.INVISIBLE);
                    }
                }
                break;
            //Move forward 5 secs from the current position
            case R.id.btnForward2:
                song2.seekTo(song2.getCurrentPosition() + 5000);
                break;
            //Move backward 5 secs from the current position
            case R.id.btnBackward2:
                song2.seekTo(song2.getCurrentPosition() - 5000);
                break;
        }
    }
}
