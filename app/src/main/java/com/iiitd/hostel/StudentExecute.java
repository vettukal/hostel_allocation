package com.iiitd.hostel;

import android.content.res.Resources;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.iiitd.hostel.backend.quoteApi.model.Quote;
import com.iiitd.hostel.backend.studentApi.StudentApi;
import com.iiitd.hostel.backend.studentApi.model.Student;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by vince on 4/27/2015.
 */
public class StudentExecute {

    private static StudentApi myApiService = null;

    // server tells to use a local server or to use a gae server.
    public StudentExecute(int server) {
        if (myApiService == null) {  // Only do this once
            StudentApi.Builder builder = null;
            //int test =0; // appengine server.
            if (server == 1) {
                builder = new StudentApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://concise-lambda-87311.appspot.com/_ah/api/");
            } else {
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

    public Student updateStudent(Student student) throws Resources.NotFoundException {
        return null;
    }

    public Student insertStudent(Student student) throws Exception {
        return myApiService.insertStudent(student).execute();
    }

    public List<Student> listStudent(@Nullable @Named("cursor") String cursorString,
                                                   @Nullable @Named("count") Integer count) throws IOException {
        return myApiService.listStudent().execute().getItems();
    }

    public List<Student> searchQuoteUsingWho(@Nullable @Named("cursor") String cursorString,
                                                         @Nullable @Named("count") Integer count,@Named("emailid") String emailid) throws IOException {
        return myApiService.listStudentUsingEmail(emailid).execute().getItems();

    }
}