package com.karatascompany.pys3318.common_function;

import android.os.AsyncTask;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RequestTask extends AsyncTask<String, String, String>{


    @Override
    protected String doInBackground(String... uri) {
        String responseString = null;
        try {
            URL url = new URL("https://vmi474601.contaboserver.net/api/auth/login?username=12313&password=12313sada");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                // Do normal input or output stream reading
            }
            else {
                responseString = "FAILED"; // See documentation for more info on response handling
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
