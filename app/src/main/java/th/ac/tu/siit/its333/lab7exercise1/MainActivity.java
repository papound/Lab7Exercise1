package th.ac.tu.siit.its333.lab7exercise1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WeatherTask w = new WeatherTask();
        w.execute("http://ict.siit.tu.ac.th/~cholwich/bangkok.json", "Bangkok Weather");
    }

    //int past_clicked = 0;
    long past_visited = 0;
    //int visit_count = 0;
    int pastClickId  = 0;

//    public void timestamp_check(int past_btnid,long past_visited_time,int now_btnid){
//
//        if(now_btnid == past_btnid){
//            long time_in_min = ((past_visited_time - System.currentTimeMillis())/1000)/60;
//            if(time_in_min > 1){
//                if(now_btnid == R.id.btBangkok){
//                    WeatherTask w = new WeatherTask();
//                    w.execute("http://ict.siit.tu.ac.th/~cholwich/bangkok.json", "Bangkok Weather");
//                }else if(now_btnid == R.id.btNon){
//                    WeatherTask w = new WeatherTask();
//                    w.execute("http://ict.siit.tu.ac.th/~cholwich/nonthaburi.json", "Nonthaburi Weather");
//                }
//                else if(now_btnid == R.id.btPathum){
//                    WeatherTask w = new WeatherTask();
//                    w.execute("http://ict.siit.tu.ac.th/~cholwich/pathumthani.json", "Pathumthani Weather");
//                }
//            }
//
//        }else{
//
//            if(now_btnid == R.id.btBangkok){
//                WeatherTask w = new WeatherTask();
//                w.execute("http://ict.siit.tu.ac.th/~cholwich/bangkok.json", "Bangkok Weather");
//            }else if(now_btnid == R.id.btNon){
//                WeatherTask w = new WeatherTask();
//                w.execute("http://ict.siit.tu.ac.th/~cholwich/nonthaburi.json", "Nonthaburi Weather");
//            }
//            else if(now_btnid == R.id.btPathum){
//                WeatherTask w = new WeatherTask();
//                w.execute("http://ict.siit.tu.ac.th/~cholwich/pathumthani.json", "Pathumthani Weather");
//            }
//
//        }
//
//    }


    public void buttonClicked(View v) {
        int id = v.getId();
        WeatherTask w = new WeatherTask();

        //if (pastClickId != id) {

            switch (id) {
                case R.id.btBangkok:
                    //if(visit_count == 0){
                    // w.execute("http://ict.siit.tu.ac.th/~cholwich/bangkok.json", "Bangkok Weather");
                    //past_clicked = R.id.btBangkok;
                    // past_visited = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
                    //visit_count++;
                    //}else{
//                    timestamp_check(past_clicked,past_visited,nowclick_id);
//                    past_clicked = R.id.btBangkok;
//                    past_visited = System.currentTimeMillis();
                    //visit_count++;
                    //}
                    if (pastClickId != R.id.btBangkok ||System.currentTimeMillis() - past_visited > 60000) {
                        w.execute("http://ict.siit.tu.ac.th/~cholwich/bangkok.json", "Bangkok Weather");
                        past_visited = System.currentTimeMillis();
                    }
                    break;
                case R.id.btNon:
//                if(visit_count == 0){
//                    w.execute("http://ict.siit.tu.ac.th/~cholwich/nonthaburi.json", "Nonthaburi Weather");
//                    past_clicked = R.id.btNon;
//                    past_visited = System.currentTimeMillis();
//                    visit_count++;
//                }else{
//                    timestamp_check(past_clicked,past_visited,nowclick_id);
//                    past_clicked = R.id.btNon;
//                    past_visited = System.currentTimeMillis();
//                    visit_count++;
//                }
                    if (pastClickId != R.id.btNon ||System.currentTimeMillis() - past_visited > 60000) {
                        w.execute("http://ict.siit.tu.ac.th/~cholwich/nonthaburi.json", "Nonthaburi Weather");
                        past_visited = System.currentTimeMillis();
                    }
                    break;
                case R.id.btPathum:
//                if(visit_count == 0){
//                    w.execute("http://ict.siit.tu.ac.th/~cholwich/pathumthani.json", "Pathumthani Weather");
//                    past_clicked = R.id.btPathum;
//                    past_visited = System.currentTimeMillis();
//                    visit_count++;
//                }else{
//                    timestamp_check(past_clicked,past_visited,nowclick_id);
//                    past_clicked = R.id.btPathum;
//                    past_visited = System.currentTimeMillis();
//                    visit_count++;
//                }
                    if (pastClickId != R.id.btPathum ||
                            System.currentTimeMillis() - past_visited > 60000) {
                        w.execute("http://ict.siit.tu.ac.th/~cholwich/pathumthani.json", "Pathumthani Weather");
                        past_visited = System.currentTimeMillis();
                    }
            }
            pastClickId = id;
        //}else{}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class WeatherTask extends AsyncTask<String, Void, Boolean> {
        String errorMsg = "";
        ProgressDialog pDialog;
        String title;
        String temp_all,humidity_str,weather;

        double windSpeed,temp_max,temp_min,cur_temp;
        int humidity;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading weather data ...");
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                title = params[1];
                URL u = new URL(params[0]);
                HttpURLConnection h = (HttpURLConnection)u.openConnection();
                h.setRequestMethod("GET");
                h.setDoInput(true);
                h.connect();

                int response = h.getResponseCode();
                if (response == 200) {
                    reader = new BufferedReader(new InputStreamReader(h.getInputStream()));
                    while((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    //Start parsing JSON
                    JSONObject jWeather = new JSONObject(buffer.toString());
                    JSONArray jArrayWeather = jWeather.getJSONArray("weather");
                    weather = jArrayWeather.getJSONObject(0).getString("main");
                    JSONObject jTemp = jWeather.getJSONObject("main");
                    temp_max = jTemp.getDouble("temp_max");
                    temp_max = temp_max - 273.15;
                    String temp_max_str = String.format("%.1f",temp_max);
                    temp_min = jTemp.getDouble("temp_min");
                    temp_min = temp_min - 273.15;
                    String temp_min_str = String.format("%.1f",temp_min);
                    cur_temp = jTemp.getDouble("temp");
                    cur_temp = cur_temp - 273.15;
                    String cur_temp_str = String.format("%.1f",cur_temp);
                    temp_all = cur_temp_str + " (max = " + temp_max_str+", min = "+temp_min_str+")";
                    humidity = jTemp.getInt("humidity");
                    humidity_str = Integer.toString(humidity)+"%";
                    JSONObject jWind = jWeather.getJSONObject("wind");
                    windSpeed = jWind.getDouble("speed");
                    errorMsg = "";
                    return true;
                }
                else {
                    errorMsg = "HTTP Error";
                }
            } catch (MalformedURLException e) {
                Log.e("WeatherTask", "URL Error");
                errorMsg = "URL Error";
            } catch (IOException e) {
                Log.e("WeatherTask", "I/O Error");
                errorMsg = "I/O Error";
            } catch (JSONException e) {
                Log.e("WeatherTask", "JSON Error");
                errorMsg = "JSON Error";
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            TextView tvTitle, tvWeather, tvWind, tvTemp, tvHumid;
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            tvTitle = (TextView)findViewById(R.id.tvTitle);
            tvWeather = (TextView)findViewById(R.id.tvWeather);
            tvWind = (TextView)findViewById(R.id.tvWind);
            tvTemp = (TextView)findViewById(R.id.tvTemp);
            tvHumid = (TextView)findViewById(R.id.tvHumid);

            if (result) {
                tvTitle.setText(title);
                tvWeather.setText(weather);
                tvWind.setText(String.format("%.1f", windSpeed));
                tvTemp.setText(temp_all);
                tvHumid.setText(humidity_str);

            }
            else {
                tvTitle.setText(errorMsg);
                tvWeather.setText("");
                tvWind.setText("");
                tvTemp.setText("");
                tvHumid.setText("");
            }
        }
    }
}
