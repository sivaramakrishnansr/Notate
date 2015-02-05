package com.example.shivram.notate;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Shivram on 1/7/2015.
 */ public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> web;
    private final ArrayList<Integer> imageId;
    private final ArrayList<String> datetime;
    public CustomList(Activity context,
                      ArrayList<String> web, ArrayList<Integer> imageId,ArrayList<String> datetime) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.datetime=datetime;
      }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.list_single, null, true);
        final TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.txt1);
        txtTitle.setText(web.get(position));
        imageView.setImageResource(R.drawable.note);
        txtTitle2.setText(datetime.get(position));

        final Button delButton=(Button)rowView.findViewById(R.id.button1);
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String text=txtTitle.getText().toString();
                String fileinPCM= Environment.getExternalStorageDirectory().getAbsolutePath().toString() +"/Notate/"+text+".pcm";
                String fileinWAV= Environment.getExternalStorageDirectory().getAbsolutePath().toString() +"/Notate/"+text+".wav";
                File filetoDelete=new File(fileinPCM);
                filetoDelete.delete();
                File waveFileToDelete=new File(fileinWAV);
                waveFileToDelete.delete();
                delButton.setText("Deleted");
                delButton.setEnabled(false);
                web.remove(position);
                notifyDataSetChanged();


            }
        });
        final Button playButton=(Button)rowView.findViewById(R.id.button2);

        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ActionBar a=context.getActionBar();
                a.setSelectedNavigationItem(2);
                MainActivity.filepath=txtTitle.getText().toString();
                notifyDataSetChanged();

            }
        });
        return rowView;
    }

}


