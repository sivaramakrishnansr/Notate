package com.example.shivram.notate;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
public class FragmentB extends Fragment implements AdapterView.OnItemClickListener{
    ListView list;
    public FragmentB() {
        // Required empty public constructor
    }
    CustomList adapter=null;

    ArrayList<String> days;
    ArrayList<String> dateTime;
    ArrayList<Integer> imageId;
    View contextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView = inflater.inflate(R.layout.fragment_fragment_c,container,false);
        if (isVisible()) {

        }
        return contextView;
    }
    private boolean isViewShown = false;
 @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null)
        {
            String path = Environment.getExternalStorageDirectory().toString()+"/Notate";
            File fileToOpen = new File(path);
            File file[] = fileToOpen.listFiles();
            days=new ArrayList<String>();
            dateTime = new ArrayList<String>();
            imageId = new ArrayList<Integer>();
            for (int i=0; i < file.length; i++)
            {
                String fileWithExtension=file[i].getName();
                String fileWithoutExtension=file[i].getName().substring(0,file[i].getName().length()-4);
                Date lastModDate = new Date(file[i].lastModified());
                if(fileWithExtension.toLowerCase().contains(".wav"))
                {
                    days.add(fileWithoutExtension);
                    dateTime.add(lastModDate.toString().substring(0, lastModDate.toString().length() - 14));
                    imageId.add(R.drawable.note);
                }
            }
            adapter = new CustomList(this.getActivity(), days, imageId,dateTime);
            list=(ListView) contextView.findViewById(R.id.listView);
            list.setAdapter(adapter);
            list.setOnItemClickListener(this);
        }
        else
        {
            isViewShown = false;
        }
    }

}





