package com.example.chatapplication.AllUsers;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private RecyclerView rvUser;
    private UserAdapter userAdapter;

    private DatabaseReference dbref;
    private List<AllUserResponse> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_user);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbref = FirebaseDatabase.getInstance().getReference();
        rvUser = findViewById(R.id.rvUser);
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this, userList);
        rvUser.setAdapter(userAdapter);

        dbref.child("user").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userList.clear(); // Clear the list before adding new data
                for (DataSnapshot data : snapshot.getChildren()) {
                    AllUserResponse currentuser = data.getValue(AllUserResponse.class);
                    if (currentuser != null) {
                        userList.add(currentuser); // Add new user data
                    }
                }
                userAdapter.notifyDataSetChanged(); // Notify adapter of data changes
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserActivity.this, "data cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
