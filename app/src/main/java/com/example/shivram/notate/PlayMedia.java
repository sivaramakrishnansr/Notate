
package com.example.shivram.notate;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class PlayMedia extends AsyncTask<Void, Float, Void>{

    private MediaPlayer mediaPlayer;
    Runnable mRunnable=null;
    AudioTrack audio;
    FileInputStream fileInput;
    public int position=0;
    @Override
    protected Void doInBackground(Void... params) {
        int minBufferSize = AudioTrack.getMinBufferSize(MainActivity.sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        String recordingFile=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Notate/"+MainActivity.filepath+".wav";
        audio = new AudioTrack(AudioManager.STREAM_MUSIC, MainActivity.sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize, AudioTrack.MODE_STREAM);
        int i = 0;
        byte[] buffer = new byte[2048];
        try {
            fileInput = new FileInputStream(recordingFile);
            audio.play();
            boolean endofFile=false;
            while(true ) {
                if (!MainActivity.pause) {
                    i=fileInput.read(buffer);
                    if(i>-1) {
                        audio.write(buffer, 0, i);
                        position = position + i;
                        short[] shortBuffer = new short[buffer.length / 2];
                        ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shortBuffer);
                        float[] floaters = new float[buffer.length];
                        for (i = 0; i < shortBuffer.length; i++) {
                            floaters[i] = shortBuffer[i];
                        }
                        Yin track = new Yin(44100, 512, 0.2);
                        float result = track.getPitch(floaters);
                        publishProgress(result);
                    }
                    else{
                        endofFile=true;
                    }
                    if(endofFile){
                        break;
                    }
                }
                else{
                    continue;
                }

            }
            audio.stop();
            audio.release();
            fileInput.close();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onProgressUpdate(Float... values) {
        ConvertPitch note=new ConvertPitch();
        String display=note.convert(values[0]);
        TextView texttoChange=(TextView) MainActivity.contentViewC.findViewById(R.id.textView1);
        texttoChange.setText(display);

    }
    boolean flag=false;

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        flag=true;

    }
  public void pause(){
        MainActivity.pause=true;
    }
    public void resume(){
        MainActivity.pause=false;
    }
}
