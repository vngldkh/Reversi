package com.example.reversi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    static ImageButton[][] cells;
    static CellHandler[][] handlers;
    static Game game;
    static String gameMode;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cells = new ImageButton[][] {
                {findViewById(R.id.cell00), findViewById(R.id.cell01), findViewById(R.id.cell02), findViewById(R.id.cell03), findViewById(R.id.cell04), findViewById(R.id.cell05), findViewById(R.id.cell06), findViewById(R.id.cell07)},
                {findViewById(R.id.cell10), findViewById(R.id.cell11), findViewById(R.id.cell12), findViewById(R.id.cell13), findViewById(R.id.cell14), findViewById(R.id.cell15), findViewById(R.id.cell16), findViewById(R.id.cell17)},
                {findViewById(R.id.cell20), findViewById(R.id.cell21), findViewById(R.id.cell22), findViewById(R.id.cell23), findViewById(R.id.cell24), findViewById(R.id.cell25), findViewById(R.id.cell26), findViewById(R.id.cell27)},
                {findViewById(R.id.cell30), findViewById(R.id.cell31), findViewById(R.id.cell32), findViewById(R.id.cell33), findViewById(R.id.cell34), findViewById(R.id.cell35), findViewById(R.id.cell36), findViewById(R.id.cell37)},
                {findViewById(R.id.cell40), findViewById(R.id.cell41), findViewById(R.id.cell42), findViewById(R.id.cell43), findViewById(R.id.cell44), findViewById(R.id.cell45), findViewById(R.id.cell46), findViewById(R.id.cell47)},
                {findViewById(R.id.cell50), findViewById(R.id.cell51), findViewById(R.id.cell52), findViewById(R.id.cell53), findViewById(R.id.cell54), findViewById(R.id.cell55), findViewById(R.id.cell56), findViewById(R.id.cell57)},
                {findViewById(R.id.cell60), findViewById(R.id.cell61), findViewById(R.id.cell62), findViewById(R.id.cell63), findViewById(R.id.cell64), findViewById(R.id.cell65), findViewById(R.id.cell66), findViewById(R.id.cell67)},
                {findViewById(R.id.cell70), findViewById(R.id.cell71), findViewById(R.id.cell72), findViewById(R.id.cell73), findViewById(R.id.cell74), findViewById(R.id.cell75), findViewById(R.id.cell76), findViewById(R.id.cell77)},
        };
        handlers = new CellHandler[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                handlers[i][j] = new CellHandler(cells[i][j]);
            }
        }
        TextView gm = findViewById(R.id.gameMode);
        gm.setText(gameMode);
        game.gameActivity = this;
        game.blackCount = findViewById(R.id.blackCount);
        game.whiteCount = findViewById(R.id.whiteCount);
        ((Button) findViewById(R.id.buttonGiveUp)).setOnClickListener(view -> {
            AlertDialog.Builder dialog = new
                    AlertDialog.Builder(GameActivity.this);
            dialog.setMessage("Are you sure you want to give up?");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Yes", new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            game.endGame((game.turn + 1) % 2);
                        }
                    });
            dialog.setNegativeButton("No", new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        });
        ((Button) findViewById(R.id.buttonUndo)).setOnClickListener(view -> {
            game.undo();
        });
    }

    @Override
    protected void onStart () {
        super.onStart();
        game.turnDisplay = findViewById(R.id.turnNumber);
        game.playerDisplay = findViewById(R.id.playerMove);
        game.startTurn();
    }
}