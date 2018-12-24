package com.trantan.jsonex;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.trantan.jsonex.adapter.RecyclerAdapter;
import com.trantan.jsonex.model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static String URL = "https://api.github.com/users/google/repos";
    private RecyclerView mRecycler;
    private ProgressBar mProgressBar;
    private List<Repository> mRepositories;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycler = findViewById(R.id.recycler_repositories);
        mProgressBar = findViewById(R.id.progress_bar);
        mRepositories = new ArrayList<>();

        loadData();
    }

    private void loadData() {
        MyTask myTask = new MyTask(URL);
        myTask.execute();
    }

    class MyTask extends AsyncTask<Void, Void, List<Repository>> {
        private String mUrl;

        public MyTask(String url) {
            mUrl = url;
        }

        @Override
        protected List<Repository> doInBackground(Void... voids) {
            String jsonText = getJSONText();
            List<Repository> repositories = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(jsonText);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    int watchers = jsonObject.getInt("watchers");
                    String language = jsonObject.getString("language");
                    String imageOwnerUrl = jsonObject.getJSONObject("owner")
                            .getString("avatar_url");
                    Repository repository = new Repository(name, language, watchers, imageOwnerUrl);
                    Log.d(TAG, "doInBackground: " + name);
                    repositories.add(repository);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return repositories;
        }

        @Override
        protected void onPostExecute(List<Repository> repositories) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRepositories.addAll(repositories);
            mAdapter = new RecyclerAdapter(mRepositories);
            mRecycler.setAdapter(mAdapter);
        }

        private String getJSONText() {
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            StringBuilder jsonText = new StringBuilder();
            try {
                URL url = new URL(mUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("GET");
                connection.connect();

                int resCode = connection.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String tmp = null;
                    while ((tmp = bufferedReader.readLine()) != null) {
                        jsonText.append(tmp);
                        jsonText.append("\n");
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonText.toString();
        }
    }
}
