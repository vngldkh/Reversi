package com.example.reversi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button easy = findViewById(R.id.buttonEasy);
        easy.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            GameActivity.game = Game.Init(1);
            GameActivity.gameMode = "Game Mode: Player vs Newbie Computer";
            startActivity(intent);
        });
        Button hard = findViewById(R.id.buttonHard);
        hard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            GameActivity.game = Game.Init(2);
            GameActivity.gameMode = "Game Mode: Player vs Professional Computer";
            startActivity(intent);
        });
        Button mult = findViewById(R.id.buttonMultiplayer);
        mult.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            GameActivity.game = Game.Init(3);
            GameActivity.gameMode = "Game Mode: Player vs Player";
            startActivity(intent);
        });
    }
}