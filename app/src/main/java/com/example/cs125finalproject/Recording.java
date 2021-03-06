package com.example.cs125finalproject;

import android.Manifest;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.ToggleButton;
import android.widget.Button;
import android.media.SoundPool;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Recording extends AppCompatActivity {
    private static final String LOG_TAG = "Music";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 200;
    private static String fileName = null;
    private static String fileName2 = null;
    private static String fileName3 = null;
    private static String fileName4 = null;
    boolean recording1 = true;
    boolean recording2 = true;
    boolean recording3 = true;
    boolean recording4 = true;
    boolean playing1 = true;
    boolean playing2 = true;
    boolean playing3 = true;
    boolean playing4 = true;
    private boolean mixing = true;
    private boolean mixingPlaying = true;
    private int first = 0;
    private int second = 0;
    private int third = 0;
    private int fourth = 0;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private MediaPlayer player1 = null;
    private MediaPlayer player2 = null;
    private MediaPlayer player3 = null;
    private SoundPool pool = null;

    //Requesting permission to Record Audio and Write to External Storage
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start, int whatToDo) {
        if (start) {
            startPlaying(whatToDo);
        } else {
            stopPlaying(whatToDo);
        }
    }

    private void startPlaying(int whatToStart) {
        if (whatToStart == 1) {
            this.player = new MediaPlayer();
            try {
                this.player.setDataSource(fileName);
                this.player.prepare();
                this.player.start();
                this.player.setLooping(true);
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        } else if (whatToStart == 2) {
            this.player1 = new MediaPlayer();
            try {
                this.player1.setDataSource(fileName);
                this.player1.prepare();
                this.player1.start();
                this.player1.setLooping(true);
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        } else if (whatToStart == 3) {
            this.player2 = new MediaPlayer();
            try {
                this.player2.setDataSource(fileName);
                this.player2.prepare();
                this.player2.start();
                this.player2.setLooping(true);
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        } else {
            this.player3 = new MediaPlayer();
            try {
                this.player3.setDataSource(fileName);
                this.player3.prepare();
                this.player3.start();
                this.player3.setLooping(true);
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        }
    }
    private void stopPlaying(int whatToStop) {
        if (whatToStop == 1 && player != null ) {
            player.stop();
            player.release();
            player = null;
        } else if (whatToStop == 2 && player1 != null) {
            player1.stop();
            player1.release();
            player1 = null;
        } else if (whatToStop == 3 && player2 != null) {
            player2.stop();
            player2.release();
            player2 = null;
        } else if (whatToStop == 4 && player3 != null) {
            player3.stop();
            player3.release();
            player3 = null;
        }
    }
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public File getPublicMusictorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/saved_Audio");
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    private void save(String first1, String second2, String third3, String fourth4) {
        String dir = getPublicMusictorageDir().toString();
        copyFile(first1, dir);
        copyFile(second2, dir);
        copyFile(third3, dir);
        copyFile(fourth4, dir);
    }

    private void copyFile(String src, String dst) {
        FileInputStream inputStream;
        FileOutputStream outputStream;
        try {
            inputStream = new FileInputStream(src);
            outputStream = openFileOutput(dst, Context.MODE_WORLD_READABLE);
            int bufferSize;
            byte[] bufffer = new byte[512];
            while ((bufferSize = inputStream.read(bufffer)) > 0) {
                outputStream.write(bufffer, 0, bufferSize);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        if (player != null) {
            player.release();
            player = null;
        }
        if (player1 != null) {
            player1.release();
            player1 = null;
        }
        if (player2 != null) {
            player2.release();
            player2 = null;
        }
        if (player3 != null) {
            player3.release();
            player3 = null;
        }
        if (pool != null) {
            pool.release();
            pool = null;
        }
    }
    private void mixing() {
        if (mixing) {
            if (pool != null) {
                pool.release();
            }
            pool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
            fileName = getExternalCacheDir().getAbsolutePath();
            fileName += "/music.3gp";
            fileName2 = getExternalCacheDir().getAbsolutePath();
            fileName2 += "/music1.3gp";
            fileName3 = getExternalCacheDir().getAbsolutePath();
            fileName3 += "/music2.3gp";
            fileName4 = getExternalCacheDir().getAbsolutePath();
            fileName4 += "/music3.3gp";
            first = pool.load(fileName, 1);
            second = pool.load(fileName2, 1);
            third = pool.load(fileName3, 1);
            fourth = pool.load(fileName4, 1);
        }
    }
    private void onMix(boolean toMix) {
        if (toMix) {
            startMixing();
        } else {
            stopMixing();
        }
    }
    private void startMixing() {
        pool.play(first, 1f, 1f, 0, -1, 1);
        pool.play(second, 1f, 1f, 1, -1, 1);
        pool.play(third, 1f, 1f, 0, -1, 1);
        pool.play(fourth, 1f, 1f, 1, -1, 1);
    }
    private void stopMixing() {
        pool.stop(first);
        pool.stop(second);
        pool.stop(third);
        pool.stop(fourth);
    }
    private void changePitch(int track, Button input) {
        if (track == first) {
            pool.pause(first);
            if (input.getText().toString().equals(getString(R.string.track1Pitch))) {
                pool.setRate(first, 2);
                pool.resume(first);
                String toSet = getString(R.string.highPitch);
                input.setText(toSet);
            } else if (input.getText().toString().equals(getString(R.string.highPitch))) {
                pool.setRate(first, 0.5f);
                pool.resume(first);
                input.setText(getString(R.string.lowPitch));
            } else {
                pool.setRate(first, 1f);
                pool.resume(first);
                input.setText(getString(R.string.track1Pitch));
            }
        }
        if (track == second) {
            pool.pause(second);
            if (input.getText().toString().equals(getString(R.string.track2Pitch))) {
                pool.setRate(second, 2);
                pool.resume(second);
                input.setText(getString(R.string.highPitch));
            } else if (input.getText().toString().equals(getString(R.string.highPitch))) {
                pool.setRate(second, 0.5f);
                pool.resume(second);
                input.setText(getString(R.string.lowPitch));
            } else {
                pool.setRate(second, 1f);
                pool.resume(second);
                input.setText(getString(R.string.track2Pitch));
            }
        }
        if (track == third) {
            pool.pause(third);
            if (input.getText().toString().equals(getString(R.string.track3Pitch))) {
                pool.setRate(third, 2);
                pool.resume(third);
                input.setText(getString(R.string.highPitch));
            } else if (input.getText().toString().equals(getString(R.string.highPitch))) {
                pool.setRate(third, 0.5f);
                pool.resume(third);
                input.setText(getString(R.string.lowPitch));
            } else {
                pool.setRate(third, 1f);
                pool.resume(third);
                input.setText(getString(R.string.track3Pitch));
            }
        }
        if (track == fourth) {
            pool.pause(fourth);
            if (input.getText().toString().equals(getString(R.string.track4Pitch))) {
                pool.setRate(fourth, 2);
                pool.resume(fourth);
                input.setText(getString(R.string.highPitch));
            } else if (input.getText().toString().equals(getString(R.string.highPitch))) {
                pool.setRate(fourth, 0.5f);
                pool.resume(fourth);
                input.setText(getString(R.string.lowPitch));
            } else {
                pool.setRate(fourth, 1f);
                pool.resume(fourth);
                input.setText(getString(R.string.track4Pitch));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //record file to external cache
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);


        setContentView(R.layout.activity_recording);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button mixAllTogether = findViewById(R.id.loopAllTracks);
        mixAllTogether.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mixing();
            }
        });

        final Button playAllTracks = findViewById(R.id.playSoundpool);
        playAllTracks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onMix(mixingPlaying);
                mixingPlaying = !mixingPlaying;
            }
        });

        final ToggleButton record1 = findViewById(R.id.recordButton1);
        record1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording2 && recording3 && recording4) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music.3gp";
                    onRecord(recording1);
                    recording1 = !recording1;
                } else {
                    record1.setSelected(false);
                }
            }
        });

        final ToggleButton record2 = findViewById(R.id.recordButton2);
        record2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording1 && recording3 && recording4) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music1.3gp";
                    onRecord(recording2);
                    recording2 = !recording2;
                } else {
                    record2.setSelected(false);
                }
            }
        });

        final ToggleButton record3 = findViewById(R.id.recordButton3);
        record3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording1 && recording2 && recording4) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music2.3gp";
                    onRecord(recording3);
                    recording3 = !recording3;
                } else {
                    record3.setSelected(false);
                }
            }
        });

        final ToggleButton record4 = findViewById(R.id.recordButton4);
        record4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording1 && recording2 && recording3) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music3.3gp";
                    onRecord(recording4);
                    recording4 = !recording4;
                } else {
                    record4.setSelected(false);
                }
            }
        });

        final Switch play1 = findViewById(R.id.playSwitch1);
        play1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music.3gp";
                onPlay(playing1, 1);
                playing1 = !playing1;
            }
        });

        final Switch play2 = findViewById(R.id.playSwitch2);
        play2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music1.3gp";
                onPlay(playing2, 2);
                playing2 = !playing2;
            }
        });

        final Switch play3 = findViewById(R.id.playSwitch3);
        play3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music2.3gp";
                onPlay(playing3, 3);
                playing3 = !playing3;
            }
        });

        final Switch play4 = findViewById(R.id.playSwitch4);
        play4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music3.3gp";
                onPlay(playing4, 4);
                playing4 = !playing4;
            }
        });

        final Button download = findViewById(R.id.downloadButton);
        download.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String file1 = getExternalCacheDir().getAbsolutePath();
                file1 += "/music.3gp";
                String file2 = getExternalCacheDir().getAbsolutePath();
                file2 += "/music1.3gp";
                String file3 = getExternalCacheDir().getAbsolutePath();
                file3 += "/music2.3gp";
                String file4 = getExternalCacheDir().getAbsolutePath();
                file4 += "/music3.3gp";
                save(file1, file2, file3, file4);
            }
        });

        final Button track1Pitch = findViewById(R.id.track1Pitch);
        track1Pitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePitch(first, track1Pitch);
            }
        });

        final Button track2Pitch = findViewById(R.id.track2Pitch);
        track2Pitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePitch(second, track2Pitch);
            }
        });

        final Button track3Pitch = findViewById(R.id.track3Pitch);
        track3Pitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePitch(third, track3Pitch);
            }
        });

        final Button track4Pitch = findViewById(R.id.track4Pitch);
        track4Pitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePitch(fourth, track4Pitch);
            }
        });
    }
}
