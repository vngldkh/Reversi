package com.example.reversi;

import java.util.ArrayList;

/**
 * Наследник класса компьютер.
 */
public class ProfessionalComputer extends Computer {
    /**
     * Объект класса компьютера уровня "новичок" для вычислений.
     */
    private final EasyComputer easyComputer;

    protected ProfessionalComputer(int mark) {
        super(mark);
        easyComputer = new EasyComputer(mark);
    }

    /**
     * Компьюетр гипотетически делает ход, просчитывает возможные ходы оппонента.
     * @param cell - клетка, на которую делается ход.
     * @param board - игровое поле.
     * @return Список возможных ходов оппонента.
     */
    private ArrayList<CellInfo> hypotheticalTurn(CellInfo cell, Board board) {
        Board newBoard = board.clone();
        newBoard.reverse(cell);
        return newBoard.possibleTurns(-this.mark);
    }

    @Override
    public double estimate(CellInfo cell, Board board) {
        ArrayList<CellInfo> possibleEnemyTurns = hypotheticalTurn(cell, board);
        if (possibleEnemyTurns.isEmpty()) {
            return easyComputer.estimate(cell, board);
        }
        return easyComputer.estimate(cell, board) -
               easyComputer.estimate(easyComputer.choose(possibleEnemyTurns, board), board);
    }
}
