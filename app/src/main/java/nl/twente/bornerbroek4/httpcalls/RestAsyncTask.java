package nl.twente.bornerbroek4.httpcalls;

import android.os.AsyncTask;
import nl.twente.bornerbroek4.model.Model;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Fieldhof on 26-9-2015.
 */
public abstract class RestAsyncTask extends AsyncTask<String, Void, String>{

    private static String apiUrl = "https://www.nifnic.nl/appie-api/v1";

    @Override
    protected String doInBackground(String... params) {

        String URL = params[0];

        HttpsURLConnection con = null;

        try {
            URL url = new URL(apiUrl + params[0]);
            con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("Authorization", "Bearer " + Model.getInstance().getToken());

            return getResponse(con);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return null;
    }

    @Override
    protected abstract void onPostExecute(String s);

    private String getResponse(HttpsURLConnection con) throws IOException {
        if(con == null) {
            return null;
        }
        InputStream is = con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }
}
