package com.example.reversi;

/**
 * Класс для направления.
 */
public class Direction {
    /**
     * Текущая координата.
     */
    private Coords current;
    /**
     * Обозначение направления.
     * up           - 0
     * down         - 1
     * right        - 2
     * left         - 3
     * up-right     - 4
     * up-left      - 5
     * down-right   - 6
     * down-left    - 7
     */
    private int direction;

    public Direction(Coords current, int direction) {
        this.current = current;
        this.direction = direction;
    }

    /**
     * Ходим в нужном направлении.
     * @return true - если полученные координаты валидны, false - в другом случае.
     */
    public boolean produce() {
        switch (direction) {
            case 0:
                return current.decRow();
            case 1:
                return current.incRow();
            case 2:
                return current.incColumn();
            case 3:
                return current.decColumn();
            case 4:
                return current.decRow() && current.incColumn();
            case 5:
                return current.decRow() && current.decColumn();
            case 6:
                return current.incRow() && current.incColumn();
            case 7:
                return current.incRow() && current.decColumn();
            default:
                return false;
        }
    }

    public int getRow() {
        return current.getRow();
    }

    public int getColumn() {
        return current.getColumn();
    }

    public boolean edge() {
        return current.edge();
    }
}
