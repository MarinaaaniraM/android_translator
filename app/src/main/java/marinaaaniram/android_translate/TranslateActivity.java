package marinaaaniram.android_translate;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;


public class TranslateActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_translate, menu);
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

    public void onClick(View view) {
        TranslateTask translateTask = new TranslateTask();
        translateTask.execute();
    }


    class TranslateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            EditText wordText = (EditText)findViewById(R.id.wordText);

            String link = "https://translate.yandex.net/api/v1.5/tr.json/translate";
            String key = "trnsl.1.1.20150217T101240Z.94ed0457947c725e.dfd4eac0e7d9b45dad578129dc354024e4ce3a69";
            String text = wordText.getText().toString();
            String lang = "en-ru";

            HttpURLConnection httpURLConnection;
            URL url;

            try {
                url = new URL(link + "?key=" + key + "&text=" + text + "&lang=" + lang);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream()));
                    String inputLine;
                    StringBuffer responseJSON = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        responseJSON.append(inputLine);
                    }
                    in.close();
                    return responseJSON.toString();
                }
                return "error!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "error!";
        }

        @Override
        protected void onPostExecute(String resultJSON) {
            super.onPostExecute(resultJSON);
            EditText translateText = (EditText)findViewById(R.id.translateText);
            String translateWord = null;

            try {
                JSONObject jsonObject = new JSONObject(resultJSON);
                JSONArray jsonArray = jsonObject.getJSONArray("text");
                translateWord = jsonArray.get(0).toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            translateText.setText(translateWord);
        }
    }
}
