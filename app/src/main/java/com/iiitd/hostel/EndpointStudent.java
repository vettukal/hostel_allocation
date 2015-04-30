package com.iiitd.hostel;

/**
 * Created by vince on 4/27/2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iiitd.hostel.backend.studentApi.StudentApi;
import com.iiitd.hostel.backend.studentApi.model.Student;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class EndpointStudent extends AsyncTask<Student,Void, List<Student>> {
    private static StudentApi myApiService = null;
    private Context context;

    EndpointStudent(Context context) {
        this.context = context;
    }

    @Override
    protected List<Student> doInBackground(Student... params)
    {
        if(myApiService == null) {  // Only do this once
            StudentApi.Builder builder = null;
            int test =0; // appengine server.
            if(test==0){
                 builder = new StudentApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://concise-lambda-87311.appspot.com/_ah/api/");
            }
            else{
                builder = new StudentApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
            }

            // end options for devappserver

            myApiService = builder.build();
        }

        Student student = new Student();
        student.setAddress("rajasthan india");
        StudentConnector sc = new StudentConnector(0);
        sc.calculateDistance("rajasthan india");
        try {
            // Going to insert student in the APPengine
            //Student student = new Student();
            //student.setName("vincent-phone");
            //student.setAddress("kentuky");

            //Student s=params[0];
            //Student retStudent = myApiService.insertStudent(s).execute();
            //Log.d("vince","inserting a student from device: "+retStudent.getName());
            // -----------------------


            return myApiService.listStudent().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Student> result) {
        Log.d("vince","inside the post execute");
        Log.d("vince",result.size()+": size of the result");
        for(Student stud : result){
            Log.d("vince",stud.getName());
        }
    }
}