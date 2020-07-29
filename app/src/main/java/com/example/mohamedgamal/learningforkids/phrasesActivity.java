package com.example.mohamedgamal.learningforkids;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class phrasesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_phrases);
        m2=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words=new ArrayList<word>();

        words.add(new word("where are you going?","Dove stai andato?",R.raw.dove_stai_andato_));
        words.add(new word("How are you feeling?","come stai?",R.raw.come_stai_));
        words.add(new word("I’m feeling good.","sto bene",R.raw.sto_bene));
        words.add(new word("Are you coming?","vieni?",R.raw.vieni_));
        words.add(new word("Yes, I’m coming.","Si,vengo",R.raw.si_vengo));
        words.add(new word("Let's go.","Andiamo.",R.raw.andiamo));
        words.add(new word("Come here.","Vieni qui.",R.raw.vieni_qui));


        // we created a custom adapter
        wordAdapter itemsAdapter = new wordAdapter(this,words,R.color.phrases);

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


                    m1 = MediaPlayer.create(phrasesActivity.this,Word.getAudio_res());
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
