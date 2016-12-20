package info.androidhive.materialtabs.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by stas on 15/12/2016.
 */

public class AsyncBook extends AsyncTask<String, String, String> {

    ProgressDialog nDialog;
    protected void onPreExecute() {

        super.onPreExecute();
//          nDialog = new ProgressDialog(getActivity());
//            nDialog.setMessage("Loading..");
//            nDialog.setTitle("Checking Network");
//            nDialog.setIndeterminate(false);
//            nDialog.setCancelable(true);
//            nDialog.show();



    }


    protected String doInBackground(String... params) {


        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");


            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override

    protected void onPostExecute(String result) {
        super.onPostExecute(result);



    }
}


