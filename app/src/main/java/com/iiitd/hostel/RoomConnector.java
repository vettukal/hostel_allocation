package com.iiitd.hostel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by vince on 5/2/2015.
 */
public class RoomConnector {
    public String getRooms(){
        URL url;
        String buffer = "";

        try {
            // get URL content
            url = new URL("https://concise-lambda-87311.appspot.com/_ah/api/roomApi/v1/room");
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;

            //save to this filename
            //String fileName = "/users/mkyong/test.html";
            //File file = new File(fileName);



            //use FileWriter to write file



            while ((inputLine = br.readLine()) != null) {
                buffer += inputLine;
            }


            br.close();

            System.out.println("Done");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
