package org.diiage.martin.partiel2018martin.utils;

import android.os.AsyncTask;

import org.diiage.martin.partiel2018martin.models.Release;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ReleasesRetriever extends AsyncTask<String, Void, ArrayList<Release>> {

    public interface AsyncResponse{
        void processFinish(ArrayList<Release> result);
    }

    public AsyncResponse delegate = null;

    @Override
    protected ArrayList<Release> doInBackground(String... apiUrl) {

        InputStream inputStream = null;
        ArrayList<Release> result = new ArrayList<>();
        try{
            URL baseUrl = new URL(apiUrl[0]);
            inputStream = baseUrl.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String lineBuffer = null;
            while((lineBuffer = bufferedReader.readLine()) != null){
                stringBuilder.append(lineBuffer);
            }
            String data = stringBuilder.toString();

            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject o = jsonArray.getJSONObject(i);
                Release r = new Release(
                    o.getString("status"),
                    o.getString("thumb"),
                    o.getString("format"),
                    o.getString("title"),
                    o.getString("catno"),
                    0,
                    o.getString("resource_url"),
                    o.getString("artist"),
                    o.getInt("id")
                );
                if(!o.isNull("year")){
                    r.setYear(o.getInt("year"));
                }

                result.add(r);
            }
            inputStream.close();
            return result;
        } catch(Exception e){
            return null;
        } finally{
        }


    }


    @Override
    protected void onPostExecute(ArrayList<Release> releases) {
        delegate.processFinish(releases);
    }
}
