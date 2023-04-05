package com.example.reversi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Класс игры.
 */
public class Game {
    /**
     * Игровое поле.
     */
    Board board;
    /**
     * Массив игроков.
     * Хотя их всего два, в массиве обрабатывать удобнее.
     */
    Player[] players;
    /**
     * Стек хранит в себе поля с предыдщуих ходов.
     */
    private Stack<Board> previousTurns;
    TextView turnDisplay;
    TextView playerDisplay;
    TextView blackCount;
    TextView whiteCount;
    int turn = 0;
    GameActivity gameActivity;

    /**
     * Конструктор игры.
     * @param player1 - объект первого игрока.
     * @param player2 - объект второго игрока.
     */
    private Game(Player player1, Player player2) {
        board = new Board();
        players = new Player[]{player1, player2};
        previousTurns = new Stack<>();
    }


    public void startTurn() {
        int[] counter = count();
        blackCount.setText(String.format("Black: %d", counter[0]));
        whiteCount.setText(String.format("White: %d", counter[1]));

        ArrayList<CellInfo> possibleTurns = board.possibleTurns(players[turn % 2].mark);
        turnDisplay.setText(String.format("Turn #%d", turn + 1));
        playerDisplay.setText(String.format("Player #%d makes a move.", turn % 2 + 1));
        if (possibleTurns.isEmpty()) {
            endGame(result());
            return;
        }
        previousTurns.push(board.clone());
        if (players[turn % 2] instanceof Computer) {
            CellInfo chosenCell = players[turn % 2].choose(possibleTurns, board);
            board.reverse(chosenCell);
            ++turn;
            startTurn();
            return;
        }
        Console.displayPossibleTurns(board, possibleTurns);
    }

    int[] count() {
        int[] counter = new int[2];
        for (int[] row : board.getField()) {
            for (int cell : row) {
                if (cell == -1) {
                    ++counter[0];
                } else if (cell == 1) {
                    ++counter[1];
                }
            }
        }
        return counter;
    }

    /**
     * Подсчитывается кол-во фишек каждого цвета, определяется победитель.
     * @return Номер победителя (0 - 1 игрок, 1 - 2 игрок, 2 - ничья).
     */
    private int result() {
        int[] counter = count();
        int counter1 = counter[0], counter2 = counter[1];

        if (counter1 == counter2) {
            return 2;
        }
        if (counter1 > counter2) {
            return 0;
        }
        return 1;
    }

    /**
     * Инициализируется игровой процесс:
     * определяется режим игры, создаётся соответствующий объект класса.
     * @return Объект класса игры.
     */
    public static Game Init(int option) {
        switch (option) {
            case 1:
                return new Game(new Human(-1), new EasyComputer(1));
            case 2:
                return new Game(new Human(-1), new ProfessionalComputer(1));
            case 3:
                return new Game(new Human(-1), new Human(1));
            default:
                return null;
        }
    }

    void undo() {
        if (previousTurns.size() <= 2) {
            return;
        }
        turn -= 2;
        previousTurns.pop();
        previousTurns.pop();
        board = previousTurns.pop();
        startTurn();
    }

    /**
     * Сообщается результат игры.
     * @param outcome - Номер победителя
     */
    void endGame(int outcome) {
        String message;
        switch (outcome) {
            case 0:
                message = "Player #1 won.";
                break;
            case 1:
                message = "Player #2 won.";
                break;
            default:
                message = "Tie.";
                break;
        }
        new AlertDialog.Builder(turnDisplay.getContext()).setMessage(message).setPositiveButton("OK", (dialog, which) -> gameActivity.finish()).show();
    }
}
