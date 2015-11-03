package nl.twente.bornerbroek4.httpcalls;

import android.os.AsyncTask;
import android.util.Log;
import nl.twente.bornerbroek4.model.Model;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

/**
 * Created by Fieldhof on 26-9-2015.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        HttpsURLConnection con = null;
        InputStream input = null;
        try {
            URL url = new URL("https://www.nifnic.nl/appie-api/v1/login");
            con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("POST");

            JSONObject body = new JSONObject();

            body.put("username", params[0]);
            body.put("password", params[1]);

            sendBody(con, body.toString());

            return getResponse(con);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String token) {
        Log.d("Token", token);
        if(token != null) {
            Model.getInstance().setToken(token);
        }
    }

    private void sendBody(HttpsURLConnection con, String attr) throws IOException {
        if(con == null || attr == null) {
            return;
        }
        DataOutputStream wr = new DataOutputStream(con.getOutputStream ());
        wr.writeBytes (attr);
        wr.flush ();
        wr.close ();
    }

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
