package com.example.reversi;

import java.util.ArrayList;

/**
 * Класс для оценочной информации о клетке.
 */
public class CellInfo {
    /**
     * Координаты оцениваемой клетки.
     */
    public final Coords coords;
    /**
     * Обозначение игрока, относительно которого происходит оценка.
     */
    public final int player;
    /**
     * Кол-во замыкаемых клеток.
     */
    public final int lockableCount;
    /**
     * Список доступных направлений
     */
    public final ArrayList<Integer> viableRoutes;

    public CellInfo(Coords coords, int player, int lockableCount, ArrayList<Integer> viableRoutes) {
        this.coords = coords;
        this.player = player;
        this.lockableCount = lockableCount;
        this.viableRoutes = viableRoutes;
    }

    /**
     * Проверяем, находится ли эта клетка на краю поля.
     * @return true - на краю, false - в ином случае.
     */
    public boolean edge() {
        return coords.edge();
    }

    /**
     * Проверяем, находится ли эта клетка в углу поля.
     * @return true - в углу, false - в ином случае.
     */
    public boolean corner() {
        return coords.corner();
    }
}
