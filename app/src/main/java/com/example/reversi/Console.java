package com.example.reversi;

import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс консоли.
 * Содержит только статические методы.
 * Реализует взаимодействие пользователя с игрой через консоль.
 */
public class Console {
    /**
     * Отображает игровое поле в консоли.
     * @param board - игровое поле.
     */
    public static void display(Board board) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                switch (board.getField()[i][j]) {
                    case 1:
                        GameActivity.handlers[i][j].draw(1);
                        break;
                    case -1:
                        GameActivity.handlers[i][j].draw(-1);
                        break;
                    case '?':
                        GameActivity.handlers[i][j].makePossible(i, j);
                        break;
                    default:
                        GameActivity.handlers[i][j].draw(0);
                }
            }
        }
    }

    /**
     * Отображает возможные ходы.
     * @param board - игровое поле.
     * @param turns - список возможных ходов.
     */
    public static void displayPossibleTurns(Board board, ArrayList<CellInfo> turns) {
        // Клонируем поле.
        Board newBoard = board.clone();
        // Обозначаем возможные ходы на поле.
        for (CellInfo turn : turns) {
            newBoard.getField()[turn.coords.getRow()][turn.coords.getColumn()] = '?';
        }
        // Отображаем отредактированное поле.
        display(newBoard);
    }

    /**
     * Сообщение об ошибке, если пользователь пытается отменить ход, а предыдщуего хода нет.
     */
    public static void emptyStack() {
        System.out.println("There are no previous turns!\n");
    }

    /**
     * Выводится результат игры.
     * @param outcome - код результата.
     */
    public static void result(int outcome) {

    }
}
