package com.google.main;

public class Calculator {
    final static int MIN_AMOUNT = 400;

    public static int calculate(Integer distance, Boolean isLarge, Boolean isFragile, Workload workload) throws BusinessException {
        int currentAmount = 0;
        if (distance > 30 && isFragile)
            throw new BusinessException("Хрупкие грузы нельзя возить на расстояние более 30 км");

        if (distance == 0)
            throw new BusinessException("Расстояние должно быть больше 0");

        if (distance > 30) currentAmount += 300;
        else if (distance > 10) currentAmount += 200;
        else if (distance > 2) currentAmount += 100;
        else currentAmount += 50;

        if (isLarge) currentAmount += 200;
        else currentAmount += 100;

        if (isFragile) currentAmount += 300;

        currentAmount = (int) (currentAmount * workload.getRatio());

        return Math.max(currentAmount, MIN_AMOUNT);
    }
}
