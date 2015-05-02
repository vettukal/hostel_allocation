package com.iiitd.hostel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by vince on 5/2/2015.
 */
public class ListStudents {
    public String getItems() {
        String buffer = "";
        try {
            URL google = new URL("https://concise-lambda-87311.appspot.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                // Process each line.
                //System.out.println(inputLine);
                buffer += inputLine;
            }
            in.close();

        } catch (MalformedURLException me) {
            System.out.println(me);

        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return buffer;
    }


}
