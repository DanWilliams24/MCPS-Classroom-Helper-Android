package com.example.daniel.androidmyp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        final Button submitButton = findViewById(R.id.submitButton);
         usernameEdit = findViewById(R.id.usernameEdit);
         passwordEdit = findViewById(R.id.passwordEdit);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_submit();
            }
        });



    }




    private void updateUI(FirebaseUser user){
        if(user != null){
            Toast.makeText(this, "Successfully Signed In!",Toast.LENGTH_SHORT).show();
            moveToView();
        } else {
            Toast.makeText(this,"Invalid Username/Password!", Toast.LENGTH_LONG).show();
        }
    }

    private void on_submit() {
        String user = usernameEdit.getText().toString();
        String pass = passwordEdit.getText().toString();

        mAuth.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           FirebaseUser user = mAuth.getCurrentUser();
                           updateUI(user);
                       }else{
                           updateUI(null);
                       }
                    }
                });
    }


    private void moveToView() {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);

    }
}
