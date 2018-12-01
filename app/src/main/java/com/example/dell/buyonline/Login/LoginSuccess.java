package com.example.dell.buyonline.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import com.example.dell.buyonline.R;

public class LoginSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        
        findID();
    }

    private void findID() {
        final EditText editUserName = (EditText) findViewById(R.id.edt_username_success);
        final EditText editEmail = (EditText) findViewById(R.id.edit_email_success);
        final TextView textWelcome = (TextView) findViewById(R.id.text_welcome);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        String message = name + "welcome to your user area";
        textWelcome.setText(message);
        editUserName.setText(username);
        editEmail.setText(email);
    }
}
