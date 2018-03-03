package com.example.daniel.androidmyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DataActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final String url = "http://localhost:3000/grades";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        mAuth = FirebaseAuth.getInstance();
        requestQueue = Volley.newRequestQueue(this);

    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user){
        if(user != null){
           //User found:
            // Load api data
            loadAPIData();
            // Go ahead and load userData


        } else {
            //If user is not signed in, go back to sign-in
            moveToView();
        }
    }

    private void moveToView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    private void loadAPIData(){
        StringRequest request = new StringRequest(Request.Method.GET, url, onCoursesLoaded, onCoursesError);
        requestQueue.add(request);
    }


    private final Response.Listener<String> onCoursesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
        }
    };

    private final Response.ErrorListener onCoursesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

}
