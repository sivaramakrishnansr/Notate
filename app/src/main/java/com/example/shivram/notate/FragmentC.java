package com.example.shivram.notate;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
public class FragmentC extends Fragment {
    public FragmentC() {
        }
    boolean playing=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity.contentViewC = inflater.inflate(R.layout.fragment_fragment_b, container, false);
        MainActivity.playButton = (Button) MainActivity.contentViewC.findViewById(R.id.button1);
        MainActivity.pauseButton = (Button) MainActivity.contentViewC.findViewById(R.id.button2);
        MainActivity.playButton.setEnabled(true);
        MainActivity.pauseButton.setEnabled(true);
        MainActivity.textToChange=(TextView)MainActivity.contentViewC.findViewById(R.id.textView1);
        MainActivity.pauseButton.setVisibility(View.GONE);
        MainActivity.playButton.setVisibility(View.VISIBLE);
        final PlayMedia[] play = {null};
        final boolean[] firstTime = {true};
        MainActivity.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play[0] =new PlayMedia();
                if (MainActivity.filepath != "Nothing") {
                    playing=true;
                    if(firstTime[0]) {
                        if (play[0].getStatus() == AsyncTask.Status.PENDING || play[0].getStatus() == AsyncTask.Status.FINISHED) {
                            play[0].execute();
                            firstTime[0] =false;
                            MainActivity.playButton.setVisibility(View.GONE);
                            MainActivity.pauseButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        play[0].resume();
                        MainActivity.playButton.setVisibility(View.GONE);
                        MainActivity.pauseButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        MainActivity.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.filepath != "Nothing") {
                    MainActivity.pauseButton.setVisibility(View.GONE);
                    MainActivity.playButton.setVisibility(View.VISIBLE);
                    play[0].pause();
                    playing=false;
          }
            }
        });
        return MainActivity.contentViewC;
    }
}





