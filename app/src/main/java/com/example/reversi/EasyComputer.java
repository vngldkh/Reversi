package com.example.reversi;

/**
 * Класс компьютера уровня "новичок".
 */
public class EasyComputer extends Computer {

    protected EasyComputer(int mark) {
        super(mark);
    }

    @Override
    public double estimate(CellInfo info, Board board) {
        double res = info.lockableCount;
        if (info.edge()) {
            res += 0.4;
        }
        if (info.corner()) {
            res += 0.4;
        }
        return res;
    }
}
