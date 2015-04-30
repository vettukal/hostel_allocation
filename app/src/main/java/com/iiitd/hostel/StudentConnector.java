package com.iiitd.hostel;

import android.content.res.Resources;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iiitd.hostel.backend.studentApi.StudentApi;
import com.iiitd.hostel.backend.studentApi.model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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

        // while inserting student first we have to calculate the distance between home and iiitd
        // This can be done using the html code that was there.
        // for this a new function is called with the student address
        calculateDistance(student.getAddress());
        return myApiService.insertStudent(student).execute();
    }

    private void calculateDistance(String address) {
        // First of all the spaces has to converted to the + sign
        // All double space to single space.
        address = address.replace("  "," ");
        address = address.replace("\n"," ");
        address = address.replace(" ","+");
        String iiitd = "IIIT+Delhi+Okhla+Industrial+Estate+New+Delhi+Delhi";
        // now get the html response for this shit

        // example of query
        // https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC&destinations=San+Francisco&mode=bicycling&language=en-US&key=AIzaSyBeWjt37zWHrkjLl8aL8unxOhQeLcnGUH4



        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        String buffer = "";

        try {
            String source = "";
            String destination = iiitd;
            String urlpath = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+source+"&destinations="+destination+"&mode=bicycling&language=en-US&key=AIzaSyBeWjt37zWHrkjLl8aL8unxOhQeLcnGUH4";
            url = new URL("http://stackoverflow.com/");
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                buffer += line;
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

    }

    public Student updateStudent(Student student) throws Exception {
        return myApiService.updateStudent(student).execute();
    }

    public void removeStudent(Long id) throws Exception {
        myApiService.removeStudent(id).execute();
    }


}
