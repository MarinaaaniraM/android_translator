package marinaaaniram.android_translate;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


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
        translateTask.execute("aaa");
    }


    class TranslateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = "https://translate.yandex.net/api/v1.5/tr.json/translate";
            String key = "trnsl.1.1.20150217T101240Z.94ed0457947c725e.dfd4eac0e7d9b45dad578129dc354024e4ce3a69";
            String text = "Hello";
            String lang = "en-ru";

            HttpURLConnection httpURLConnection;
            URL url;

            String returnMessage;

            try {
                url = new URL(link + "?key=" + key + "&text=" + text + "&lang=" + lang);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                returnMessage = httpURLConnection.getResponseMessage();
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return returnMessage;


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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            EditText translateText = (EditText)findViewById(R.id.translateText);
            translateText.setText("result = " + result);
        }
    }
}
