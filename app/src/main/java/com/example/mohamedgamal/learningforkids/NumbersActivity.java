package com.example.mohamedgamal.learningforkids;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    public MediaPlayer m1;
    public AudioManager m2;


    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                m1.pause();
                m1.seekTo(0);
            }
            // Pause playback
         else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            // Resume playback
                m1.start();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
           releaseMediaPlayer();
        }
    }
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        // Request Audio Focus
        m2=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word>words=new ArrayList<word>();

        words.add(new word("one","uno",R.drawable.number_one,R.raw.uno));
        words.add(new word("two","due",R.drawable.number_two,R.raw.due));
        words.add(new word("three","tre",R.drawable.number_three,R.raw.tre));
        words.add(new word("four","quattro",R.drawable.number_four,R.raw.quattro));
        words.add(new word("five","cinque",R.drawable.number_five,R.raw.cinque));
        words.add(new word("six","sei",R.drawable.number_six,R.raw.sei));
        words.add(new word("seven","sette",R.drawable.number_seven,R.raw.sette));
        words.add(new word("eight","otto",R.drawable.number_eight,R.raw.otto));
        words.add(new word("nine","nove",R.drawable.number_nine,R.raw.nove));
        words.add(new word("ten","dieci",R.drawable.number_ten,R.raw.dieci));

        // we created a custom adapter
        wordAdapter itemsAdapter = new wordAdapter(this,words,R.color.numbers);

        // Our list view declaration
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // getting the position of the view clicked word obj
                word Word= words.get(position);

                // realse media player before it starts

                releaseMediaPlayer();

                // Request audio focus for playback
                int result = m2.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We Have Audio Focus Now


                 m1 = MediaPlayer.create(NumbersActivity.this,Word.getAudio_res());
                m1.start();
                m1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {

                        releaseMediaPlayer();
                    }
                });

            }
            }
        });
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (m1 != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            m1.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            m1 = null;
            // abandon audio focus when play back complete
            m2.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
