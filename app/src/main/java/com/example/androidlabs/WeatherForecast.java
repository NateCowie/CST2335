package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;


public class WeatherForecast extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView currentTemp, minTemp, maxTemp, uvRating;
    private ImageView weather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weather =  findViewById(R.id.weather);
        currentTemp = findViewById(R.id.current);
        minTemp =  findViewById(R.id.min_temp);
        maxTemp =  findViewById(R.id.max_temp);
        uvRating =  findViewById(R.id.UV);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery forecast = new ForecastQuery();
        String urlQuery = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        forecast.execute(urlQuery);

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {


        String tempValue, min, max, uv;
        Bitmap bm;
        String iconName, iconFile;

        @Override
        protected String doInBackground(String... strings) {

            String ret = null;
            String queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
            String UVurl = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

            try {

                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");


                int EVENT_TYPE;

                while ((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {

                    switch (EVENT_TYPE) {

                        case START_TAG:
                            String tagName = xpp.getName();
                            if (tagName.equals("temperature")) {
                                tempValue = xpp.getAttributeValue(null, "value");
                                publishProgress(25);

                                min = xpp.getAttributeValue(null, "min");
                                publishProgress(50);


                                max = xpp.getAttributeValue(null, "max");
                                publishProgress(75);

                            }
                            else if (tagName.equals("weather")) {
                                iconName = xpp.getAttributeValue(null, "icon");
                                iconFile = iconName + ".png";
                                String imageURL = "http://openweathermap.org/img/w/" + iconFile;

                                Log.i("AsyncTask", "Looking for icon name:" + iconFile);
                                if (fileExistance(iconFile)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(iconFile);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    bm = BitmapFactory.decodeStream(fis);
                                    Log.e("AsyncTask", "Found icon name \"" + iconFile + "\" in local");
                                }
                                    else {
                                        Log.e("AsyncTask", "Not found \"" + iconFile + "\" in local");
                                        URL url_3 = new URL(imageURL);
                                        HttpURLConnection connection = (HttpURLConnection) url_3.openConnection();
                                        connection.connect();
                                        int responseCode = connection.getResponseCode();
                                        if (responseCode == 200) {
                                            bm = BitmapFactory.decodeStream(connection.getInputStream());



                                        FileOutputStream outputStream = openFileOutput(iconFile, Context.MODE_PRIVATE);
                                        bm.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                        outputStream.flush();
                                        outputStream.close();
                                    }
                                }
                                Log.e("AsyncTask", "File downloaded\"" + iconFile + "\" in local");

                                publishProgress(100);
                            }
                            break;
                        case END_TAG:
                            break;
                        case TEXT:
                            break;

                    }
                xpp.next();
            }



            } catch (XmlPullParserException e) {
                ret = "XML Pull exception. The XML is not properly formed";
            } catch (MalformedURLException e) {
                ret = "Malformed URL exception";
            } catch (IOException e) {
                ret = "IO Exception. Is the Wifi connected?";
            }

            try {
                URL WeatherUrl = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection UVConnection = (HttpURLConnection) WeatherUrl.openConnection();
                InputStream inStream = UVConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                uv = String.valueOf(jObject.getDouble("value"));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return ret;
        }

        public boolean fileExistance(String fileName) {

            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }


        @Override

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String sentFromDoInBackground) {

            currentTemp.setText("Current temperature: " + tempValue + "°C");
            minTemp.setText("Min temperature: " + min + "°C");
            maxTemp.setText("Max temperature: " + max + "°C");
            uvRating.setText("UV Rating: " + uv);
            weather.setImageBitmap(bm);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }




}



