package com.example.shivram.notate;
import android.media.AudioRecord;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class FragmentA extends Fragment {
    RecordAudio recordTask;                             // Creates a Record Audio command
    TextView textView1;                                 // Creates a text view for the frequency
    Button startStopButton;                             // Button to control start and stop
    boolean started = false;                            // flag to keep track of playing
    public FragmentA() {
        // Required empty public constructor
    }
    View contentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_, container,false);
        textView1 = (TextView) contentView.findViewById(R.id.textView1);
        startStopButton = (Button) contentView.findViewById(R.id.button1);
        startStopButton.setOnClickListener(new View.OnClickListener() {
        //Starting the Recorder to Record Instruments
            @Override
            public void onClick (View v){
                if (started) {
                    started = false;
                    startStopButton.setText("Record");
                    recordTask.cancel(true);
                    }
                else{
                    started = true;
                    startStopButton.setText("Stop");
                    recordTask = new RecordAudio();
                    recordTask.execute();
                }
            }
        });
        return contentView;
    }
    private class RecordAudio extends AsyncTask<Void, Float, Void> {
        FileOutputStream fOutputStream = null;
        BufferedOutputStream bOutputStream =null;
        DataOutputStream dOutputStream = null;
        AudioRecord audioRecord;
        @Override
        protected Void doInBackground(Void... params) {
            MainActivity.bufferSize = AudioRecord.getMinBufferSize(MainActivity.sampleRate, MainActivity.channelConfig, MainActivity.audioEncoding);                // Gets the minimum buffer needed
            audioRecord = new AudioRecord(MainActivity.audioSource, MainActivity.sampleRate, MainActivity.channelConfig, MainActivity.audioEncoding, MainActivity.bufferSize);   // The RAW PCM sample recording
            File fileToOpen=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Notate/");
            if(!fileToOpen.isDirectory()) {
                File newDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Notate/");
                newDirectory.mkdirs();
            }
            EditText edit=(EditText)(contentView.findViewById(R.id.editText1));
            String nameOfFile=edit.getText().toString();
            if (nameOfFile.length()==0)
            {
                nameOfFile= "Temp";
            }
            String filepath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Notate/"+nameOfFile+".pcm";
            MainActivity.fileToMakeWave=nameOfFile;
            short[] buffer = new short[MainActivity.blockSize];          // Save the raw PCM samples as short bytes
            try{
                fOutputStream=new FileOutputStream(filepath);
            }
            catch(FileNotFoundException e)
            {
            }
            bOutputStream = new BufferedOutputStream(fOutputStream);
            dOutputStream = new DataOutputStream(bOutputStream);
            try {
                audioRecord.startRecording();  //Start
            } catch (Throwable t) {
            }

            while (started) {
                audioRecord.read(buffer, 0, MainActivity.blockSize);
                Yin obtainPitch = new Yin(44100, 1024, 0.2);
                float[] floatValues = new float[buffer.length];
                for (int i = 0; i < buffer.length; i++) {
                    floatValues[i] = buffer[i];
                }
                byte[] byteValues = new byte[buffer.length * 2];
                ByteBuffer.wrap(byteValues).order(ByteOrder.LITTLE_ENDIAN)
                        .asShortBuffer().put(buffer);
                try {
                    fOutputStream.write(byteValues);
                }
                catch(Exception e){
                }
                float result = obtainPitch.getPitch(floatValues);
                publishProgress(result);
            }

            try{
                dOutputStream.close();
                fOutputStream.close();
                bOutputStream.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            //Convert PCM to WAV
            ConvertWAV convertWave = new ConvertWAV();
            try {
                convertWave.rawToWave();
            }
            catch(IOException e)
            {

            }

            return null;
        }
        @Override
        protected void onCancelled() {
            audioRecord.stop();
            audioRecord.release();
        }
        @Override
        protected void onProgressUpdate(Float... values) {
            TextView tView = (TextView) contentView.findViewById(R.id.textView1);
            ConvertPitch note=new ConvertPitch();
            String display=note.convert(values[0]);
            tView.setText(display);
        }
    }
}



