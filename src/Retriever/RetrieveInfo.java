package Retriever;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


//This class executes a call to the API to get the information and returns a JSON object that it gets from the API
public class RetrieveInfo {

    /**
     * This method creates a call to the API given an endpoint
     *
     * @param urls - The endpoint for which to create the call to the API
     * @return a JSONObject which is the data at the endpoint
     */
    protected JSONObject execute(String... urls) { //... parameter means multiple arguments can be accepted
        JSONObject jobj = null; //Initializing a JSONObject

        //Try to make call to API
        try {
            //Create URL object
            URL url = new URL(urls[0]);
            //Open HTTP connection with the URL
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //set request method to 'GET'
            con.setRequestMethod("GET");

            StringBuilder inline = new StringBuilder();

            //Create connection
            con.connect();
            //Get response from server
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                //If the server does not give a response code of 200, there was an error
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }
            else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) { //While there is more information, continue to loop
                    inline.append(sc.nextLine());
                }
                sc.close();
            }
            //Format String to JSONObject
            jobj = new JSONObject(inline.toString());
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jobj; //Return json object
    }
}