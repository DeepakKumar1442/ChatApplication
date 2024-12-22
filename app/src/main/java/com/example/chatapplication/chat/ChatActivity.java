package com.example.chatapplication.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvMessage;
    private ChatAdapter rvchatadapter;

    private DatabaseReference dbref;
    private ArrayList<ChatResponse> msglist;

    private EditText edtmsg;
    private TextView tvNamee;
    private ImageView imgSendMessage;
    private String senderRoom;
    private String receiverRoom;
    private String senderUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Handle edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvMessage = findViewById(R.id.rvMessage);
        edtmsg = findViewById(R.id.edtTypemsg);
        imgSendMessage = findViewById(R.id.imgSendMessage);
        tvNamee = findViewById(R.id.tvNamee);

        // Initialize Firebase Database reference
        dbref = FirebaseDatabase.getInstance().getReference();
        senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Retrieve sender and receiver info from intent

        String receiverUid = getIntent().getStringExtra("uid");
        String name   = getIntent().getStringExtra("name");


        tvNamee.setText(name);
        if (receiverUid == null) {
            Toast.makeText(this, "Receiver UID is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;

        // Initialize message list and adapter
        msglist = new ArrayList<>();
        rvchatadapter = new ChatAdapter(this, msglist);
        rvMessage.setAdapter(rvchatadapter);

        // Listen for new messages
        dbref.child("chats").child(senderRoom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                msglist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ChatResponse message = postSnapshot.getValue(ChatResponse.class);
                    if (message != null) {
                        msglist.add(message);
                    }
                }
                rvchatadapter.notifyDataSetChanged(); // Notify adapter of data changes
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors
                Toast.makeText(ChatActivity.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle send message button click
        imgSendMessage.setOnClickListener(v -> {
            String messageText = edtmsg.getText().toString();
            if (messageText.isEmpty()) {
                Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
                return;
            }

            ChatResponse message = new ChatResponse(messageText, senderUid);

            dbref.child("chats").child(senderRoom).child("messages").push().setValue(message)
                    .addOnSuccessListener(aVoid -> {
                        dbref.child("chats").child(receiverRoom).child("messages").push().setValue(message);
                        edtmsg.setText(""); // Clear the EditText
                    })
                    .addOnFailureListener(e -> Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show());
        });
    }

    public void gotoCall(View view) {
        // Create an Intent to initiate a phone call
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // Set the phone number (this can be a hardcoded number or retrieved dynamically)
        intent.setData(Uri.parse("tel:91 9717726272"));

        // Check if there's an app to handle this intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
