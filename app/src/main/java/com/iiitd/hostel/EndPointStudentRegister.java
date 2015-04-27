package com.iiitd.hostel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.iiitd.hostel.backend.studentApi.StudentApi;
import com.iiitd.hostel.backend.studentApi.model.Student;

import java.io.IOException;

/**
 * Created by Ashish on 4/26/2015.
 */

public class EndPointStudentRegister  extends AsyncTask<Student,Void,Student>
{
    private  static StudentApi myApiService = null;
    private Context context;

    EndPointStudentRegister(Context context)
    {
        this.context = context;
    }

    @Override
    protected Student doInBackground(Student... student)
    {
        Log.d("Vikas babu","Aa gya background me");
        Student s=student[0];
        Log.d("Student Name",s.getName());

        if(myApiService == null)
        {  // Only do this once
            StudentApi.Builder builder = new StudentApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/").setApplicationName("Hostel");
            // end options for devappserver
            myApiService = builder.build();
        }
        try
        {
            Log.d("Ashish","in try block");

            Student newStudent=new Student();
            newStudent.setName("Ashish");
            newStudent.setContactNumber("7828517798");



            return myApiService.insertStudent(newStudent).execute();

        }
        catch(IOException e)
        {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Student student)
    {
        if(student!=null)
        {
            Toast.makeText(context, student.getId() + " : " + student.getName() + " : " + student.getEmailId() + " Is Registered SuccessFully", Toast.LENGTH_LONG).show();
            Log.d("Id of student",student.getId()+"");
        }
    }
}

