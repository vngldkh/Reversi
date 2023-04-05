package com.example.reversi;

import java.util.ArrayList;

/**
 * Абстрактный наследник класса игрок - компьютер.
 * Имеет два наследника - компьютер уровня "новичок" и уровня "профессионал".
 */
public abstract class Computer extends Player {

    protected Computer(int mark) {
        super(mark);
    }

    /**
     * Оценивает клетку.
     * @param info - информация о клетке.
     * @param board - игровое поле.
     * @return Оценка.
     */
    public abstract double estimate(CellInfo info, Board board);

    /**
     * Оцениваем все возможные ходы, выбираем клетку с самой высокой оценкой.
     * @param turns - возможные ходы.
     * @param board - игровое поле.
     * @return Информация о лучшем ходе.
     */
    @Override
    public CellInfo choose(ArrayList<CellInfo> turns, Board board) {
        double assessment;
        double maxMark = 0;
        boolean flag = true;
        CellInfo maxCell = null;
        for (CellInfo cell : turns) {
            assessment = estimate(cell, board);
            if (flag || assessment > maxMark) {
                maxCell = cell;
                maxMark = assessment;
                flag = false;
            }
        }
        return maxCell;
    }

    @Override
    public int takeTurn() {
        return 1;
    }

    @Override
    public boolean isSure() {
        return true;
    }
}
