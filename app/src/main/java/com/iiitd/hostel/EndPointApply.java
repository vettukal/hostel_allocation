package com.iiitd.hostel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.iiitd.hostel.backend.studentApi.model.Student;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ashish on 5/2/2015.
 */
public class EndPointApply extends AsyncTask<ArrayList<String>,Void,Student>
{
    private Context context;


    EndPointApply(Context context)
    {
        this.context = context;
    }

    @Override
    protected Student doInBackground(ArrayList<String>... params)
    {
        StudentConnector sc=new StudentConnector(0);
        try
        {
            Log.d("arraylist of memebers",params[0].get(0));

            Student s=sc.getStudent(params[0].get(0));
            s.setRoomType(params[0].get(1));
            s.setFloorType(Integer.parseInt(params[0].get(2)));
            s.setIsApplied(true);
            s.setIsClusterOpted(false);

            sc.updateStudent(s);
            Log.d("In apply endpoint",s.getName());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
