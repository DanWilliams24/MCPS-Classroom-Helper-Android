package com.example.daniel.androidmyp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataActivity extends AppCompatActivity {
    private final String url = "http://10.0.2.2:3000/grades";
    private RequestQueue requestQueue;
    private ListView listView;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        requestQueue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.data_list_view);
        loadAPIData();

        //Initialize our GSON object
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

    }



    private void loadAPIData(){
        StringRequest request = new StringRequest(Request.Method.GET, url, onCoursesLoaded, onCoursesError);

        requestQueue.add(request);

    }


    private final Response.Listener<String> onCoursesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            List<Course> classes = Arrays.asList(gson.fromJson(response, Course[].class));
            Log.i("PostActivity", classes.size() + " courses loaded.");
            for(Course course: classes){
                Log.i("PostActivity", "Name: " + course.name);
            }

            String[] listItems = new String[classes.size()];
            for (int i = 0; i < classes.size(); i++){
                Course course = classes.get(i);
                listItems[i] = course.name;
            }

            ArrayAdapter adapter = createAdaptor(android.R.layout.simple_list_item_1, listItems);
            listView.setAdapter(adapter);

        }
    };

    private final Response.ErrorListener onCoursesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());

        }
    };


    private ArrayAdapter createAdaptor(int id, String[] items){
        return new ArrayAdapter(this,id,items);

    }
}
