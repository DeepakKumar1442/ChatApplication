package com.example.chatapplication.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapplication.AllUsers.UserActivity;
import com.example.chatapplication.R;
import com.example.chatapplication.util.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button button;
    private FirebaseAuth mAuth;

    private SharedPreference sharedPreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreference = new SharedPreference(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edtemail);
        password = findViewById(R.id.edtpassword);
        button = findViewById(R.id.btnlogin);

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //id byte code convert into string//
              String useremail=email.getText().toString().trim();
              String userpassword=password.getText().toString().trim();
              //validation//
              if (useremail.isEmpty()){
                  Toast.makeText(LoginActivity.this, "please enter useremail", Toast.LENGTH_SHORT).show();
                    return;
              }

              if (userpassword.isEmpty()){
                  Toast.makeText(LoginActivity.this, "please enter userpassword", Toast.LENGTH_SHORT).show();
                    return;
              }
              callfirebase(useremail,userpassword);
      };
    });



}

    private void callfirebase(String useremail, String userpassword) {
        mAuth.signInWithEmailAndPassword(useremail,userpassword )
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ((task.isSuccessful())){
                            sharedPreference.setLogin(true);
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}

