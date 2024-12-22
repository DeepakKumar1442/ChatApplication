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

import com.example.chatapplication.R;
import com.example.chatapplication.util.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    EditText name, email, password;
    Button button;

    private FirebaseAuth mAuth;

    //06-08-2024 realtimedb
    private DatabaseReference databaseReference;

    String username, useremail, userpassword;

    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

          mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //this is a byte code value
        name = findViewById(R.id.edtname);
        email = findViewById(R.id.edtemail1);
        password = findViewById(R.id.edtpassword);
        button = findViewById(R.id.btnbutton);

        sharedPreference =new SharedPreference(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //id byte code convert into actual value string//
                username = name.getText().toString().trim();
                useremail = email.getText().toString().trim();
                userpassword = password.getText().toString().trim();
                //validation
                if (username.isEmpty()) {
                    showSnackbar("Please enter username");
                    return;
                }
                if (useremail.isEmpty()) {
                    showSnackbar("Please enter email");
                    return;
                }
                if (userpassword.isEmpty()) {
                    showSnackbar("Please enter password");
                    return;
                }
                callfirebase(useremail, userpassword);
            }
        });
    }

    private void showSnackbar(String pleaseEnterUsername) {

       Snackbar.make(findViewById(android.R.id.content), pleaseEnterUsername, Snackbar.LENGTH_SHORT).show();

      //  Toast.makeText(this, pleaseEnterUsername, Toast.LENGTH_SHORT).show();
    }

    private void callfirebase(String useremail, String userpassword) {
        mAuth.createUserWithEmailAndPassword(useremail, userpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callfirebaseRT(username, useremail, mAuth.getCurrentUser().getUid());
                        } else {
                            showSnackbar("Registration Failed");
                        }
                    }
                });
    }

    private void callfirebaseRT(String username, String useremail, String uid) {
        databaseReference.child("user").child(uid).setValue(new UserResponse(username, useremail, uid));
        showSnackbar("Registration Successful");
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void gotoLoginActivity(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
