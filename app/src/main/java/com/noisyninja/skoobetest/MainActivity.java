package com.noisyninja.skoobetest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.noisyninja.skoobetest.model.Item;
import com.noisyninja.skoobetest.model.SkoobeResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    BookAdapter bookAdapter;
    ListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        listView = (ListView) findViewById(R.id.list);

        if (Utils.isNetworkAvailable(this)) {
            load();
        } else {
            Utils.showInfoDialog(context,
                    Utils.getStringResource(context, R.string.error),
                    Utils.getStringResource(context, R.string.no_network));
        }

    }

    private void load() {
        Utils.showProgress(context, Constants.PROGRESS_STYLE.INDETERMINATE);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_STORE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        SkoobeResponse skoobeResponse = (SkoobeResponse) Utils.getFromJson(response, SkoobeResponse.class);

                        bookAdapter = new BookAdapter(context, skoobeResponse.getItems());
                        listView.setAdapter(bookAdapter);
                        Utils.hideProgress();
                        listView.invalidate();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(stringRequest);

    }
        /*StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_STORE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SkoobeResponse skoobeResponse = (SkoobeResponse) Utils.getFromJson(response, SkoobeResponse.class);

                        bookAdapter = new BookAdapter(context, skoobeResponse.getItems());
                        listView.setAdapter(bookAdapter);
                        Utils.hideProgress();
                        listView.invalidate();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgress();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }*/

}
