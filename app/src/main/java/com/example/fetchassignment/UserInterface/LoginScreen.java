package com.example.fetchassignment.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.fetchassignment.Database.Repository;
import com.example.fetchassignment.R;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fetchassignment.R;

public class LoginScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Repository repository = new Repository(getApplication());
        //The database can be cleared on login so that we can test the downloading of the file.
        //repository.clearAll();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ImageButton lBttn = findViewById(R.id.loginBttn);
        EditText username = findViewById(R.id.usernameTxt);
        EditText password = findViewById(R.id.passwordTxt);
        TextView wolfTxt = findViewById(R.id.wolfTxt);
        //This ClickListener is for the login button. If the username and password are correct the user enters the ItemList screen
        //If the username or password is incorrect and animation occurs next to the running dog gif that says "Try Again!"
        lBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("Fetch") & password.getText().toString().equals("Rewards")) {
                    Intent intent = new Intent(LoginScreen.this, ItemListScreen.class);
                    startActivity(intent);
                } else {

                        wolfTxt.setText("TRY AGAIN!");
                    Animation anim = new AlphaAnimation(0.0f, 1.0f);
                    anim.setDuration(200); //You can manage the blinking time with this parameter
                    anim.setStartOffset(20);
                    anim.setRepeatMode(Animation.REVERSE);
                    anim.setRepeatCount(Animation.INFINITE);
                    wolfTxt.startAnimation(anim);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                wolfTxt.setText("");
                            }
                        }, 2500);
                }
            }
        });
    }
}
