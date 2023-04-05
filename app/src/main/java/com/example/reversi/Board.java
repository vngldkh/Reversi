package com.example.reversi;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

/**
 * Класс игрового поля.
 */
public class Board implements Cloneable {
    /**
     * Двумерный массив, содержащий информацию о клетках поля.
     * Обозначения:
     * -1 - первый игрок, 1 - второй игрок, 0 - пустая клетка, '?' - возможный ход.
     */
    private int[][] field;
    /**
     * Размер игрового поля.
     */
    public static final int SIZE = 8;

    /**
     * Беспараметрический онструктор игрового поля.
     * Создаётся пустой массив, задаётся изначальное положение фишек.
     */
    public Board() {
        field = new int[SIZE][SIZE];
        field[3][3] = field[4][4] = 1;
        field[3][4] = field[4][3] = -1;
    }

    /**
     * Конструктор игрового поля с одним параметром - заданным двумерным массивом.
     * @param field - поле.
     */
    private Board(int[][] field) {
        this.field = field;
    }

    /**
     * Возвращает массив с информацией о поле.
     * @return Двумерный массив поля.
     */
    public int[][] getField() {
        return field;
    }

    /**
     * Оценка клетки.
     * @param coords - координаты оцениваемой клетки.
     * @param player - обозначение активного игрока.
     * @return Объект класса CellInfo, который содержит информацию, полученную в результате оценки:
     * 1. Координаты.
     * 2. Число клеток, которые можно замкнуть (крайние клетки считаются за две).
     * 3. Направления, в которых можно двигаться из этой клетки.
     * 4. Обозначение игрока, для которого происходил расчёт.
     */
    public CellInfo estimate(@NotNull Coords coords, int player) {
        // Обозначение противника.
        int opposite = -player;
        // Возможные направления из этой клетки.
        ArrayList<Integer> viableRoutes = new ArrayList<>();
        // Максимальное число шагов из данной клетки.
        int max_steps = Math.max(Math.max(coords.getRow(), coords.getColumn()),
                                 Math.max(SIZE - coords.getRow(), SIZE - coords.getColumn()));

        int routesCount = 8;
        // Массив с кол-вом замыкаемых клеток для каждого из направлений.
        int[] tempSums = new int[routesCount];
        // Для каждого из направлений обозначаем, можно ли дальше туда двигаться.
        boolean[] ruined = new boolean[routesCount];
        // Массив для направлений.
        // up           - 0
        // down         - 1
        // right        - 2
        // left         - 3
        // up-right     - 4
        // up-left      - 5
        // down-right   - 6
        // down-left    - 7
        Direction[] directions = new Direction[routesCount];
        // Инициализируем массивы.
        for (int i = 0; i < routesCount; ++i) {
            directions[i] = new Direction(new Coords(coords.getRow(), coords.getColumn()), i);
            tempSums[i] = 0;
            ruined[i] = false;
        }
        int totalSum = 0;

        // Шагаем в каждом из направлений.
        for (int step = 0; step < max_steps; ++step) {
            for (int direction = 0; direction < routesCount; ++direction) {
                // Проверяем, можно ли двигаться в текущем направлении.
                if (!ruined[direction]) {
                    // Делаем шаг. Проверяем, релеватны ли текущие координаты.
                    if (directions[direction].produce()) {
                        // Пока идут клетки противника, двигаемся.
                        // Когда они перестают идти, смотрим, что на следующей клетке:
                        // если она пустая, в этом направлении замкнуть нельзя, не сохраняем его,
                        // иначе - сохраняем.
                        if (field[directions[direction].getRow()][directions[direction].getColumn()] == opposite) {
                            ++tempSums[direction];
                            if (directions[direction].edge()) {
                                ++tempSums[direction];
                            }
                        } else {
                            if (field[directions[direction].getRow()][directions[direction].getColumn()] == player &&
                                tempSums[direction] != 0) {
                                totalSum += tempSums[direction];
                                viableRoutes.add(direction);
                            }
                            ruined[direction] = true;
                        }
                    }
                }
            }
        }

        return new CellInfo(coords, player, totalSum, viableRoutes);
    }

    /**
     * Замыкает клетки противника.
     * @param info - информация о клетке (возможные направления, кол-во замыкаемых клеток, обозначение игрока).
     */
    public void reverse(@NotNull CellInfo info) {
        ArrayList<Integer> viableRoutes = info.viableRoutes;
        int player = info.player;
        Coords coords = info.coords;
        if (viableRoutes.isEmpty()) {
            return;
        }
        field[coords.getRow()][coords.getColumn()] = player;
        Direction[] directions = new Direction[viableRoutes.size()];
        boolean[] ruined = new boolean[viableRoutes.size()];
        for (int i = 0; i < viableRoutes.size(); ++i) {
            directions[i] = new Direction(new Coords(coords.getRow(), coords.getColumn()), viableRoutes.get(i));
            ruined[i] = false;
        }
        int opposite = -player;
        int max_steps = Math.max(Math.max(coords.getRow(), coords.getColumn()),
                Math.max(SIZE - coords.getRow(), SIZE - coords.getColumn()));
        // Идём в каждом из допустимых направлений, перекрашиваем фишки.
        for (int step = 0; step < max_steps; ++step) {
            for (int direction = 0; direction < viableRoutes.size(); ++direction) {
                if (!ruined[direction]) {
                    if (directions[direction].produce()) {
                        if (field[directions[direction].getRow()][directions[direction].getColumn()] == opposite) {
                            field[directions[direction].getRow()][directions[direction].getColumn()] = player;
                        } else {
                            ruined[direction] = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Определяются возможные ходы активного игрока.
     * @param player - обозначение активного игрока.
     * @return Список допустимых ходов.
     */
    public ArrayList<CellInfo> possibleTurns(int player) {
        ArrayList<CellInfo> turns = new ArrayList<>();
        // Оцениваем каждую клетку.
        // Если она замыкает хотя бы одну клетку противника, сохраняем.
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (field[i][j] == 0) {
                    CellInfo info = estimate(new Coords(i, j), player);
                    if (!info.viableRoutes.isEmpty()) {
                        turns.add(info);
                    }
                }
            }
        }
        return turns;
    }

    /**
     * Клонирование игрового поля.
     * @return Возвращает объект-клон текущего объекта.
     */
    @Override
    public Board clone() {
        int[][] newField = new int[this.SIZE][this.SIZE];
        for (int i = 0; i < this.SIZE; ++i) {
            newField[i] = this.getField()[i].clone();
        }
        return new Board(newField);
    }
}
