package com.example.basket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    public void start(){
        Button itemButton = (Button) findViewById(R.id.itemButton);
        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, itemActivity.class);
                    startActivity(intent);
            }
        });
        Button basketButton = (Button) findViewById(R.id.basketButton);
        basketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, basketActivity.class);
                startActivity(intent);
            }
        });
    }
}
