package com.iiitd.hostel;

import android.content.res.Resources;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iiitd.hostel.backend.studentApi.StudentApi;
import com.iiitd.hostel.backend.studentApi.model.Student;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by vince on 4/28/2015.
 */
public class StudentConnector {
    private static StudentApi myApiService = null;

    StudentConnector(int server){
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
    }

    public List<Student> listStudent() throws IOException {
        return myApiService.listStudent().execute().getItems();
    }

    public Student insertStudent(Student student) throws Exception {
        return myApiService.insertStudent(student).execute();
    }

    public Student updateStudent(Student student) throws Exception {
        return myApiService.updateStudent(student).execute();
    }

    public void removeStudent(Long id) throws Exception {
        myApiService.removeStudent(id).execute();
    }


}
