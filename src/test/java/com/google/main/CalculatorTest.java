package com.google.main;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.google.main.Calculator.calculate;
import static com.google.main.Workload.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CalculatorTest {

    @ParameterizedTest(name = "Расчет доставки: distance is {0}, isLarge is {1}, isFragile is {2}, workload is {3}")
    @MethodSource("values")
    public void calculationCheck(int distance, boolean isLarge, boolean isFragile, Workload workload, int expectedSum) throws BusinessException {
        assertThat(calculate(distance, isLarge, isFragile, workload)).isEqualTo(expectedSum);
    }

    private static Stream<Arguments> values() {
        return Stream.of(
                Arguments.of(1, false, false, DEFAULT, 400),
                Arguments.of(1, true, true, DEFAULT, 550),
                Arguments.of(2, true, true, DEFAULT, 550),
                Arguments.of(3, true, true, DEFAULT, 600),
                Arguments.of(10, true, true, DEFAULT, 600),
                Arguments.of(11, true, true, DEFAULT, 700),
                Arguments.of(30, true, true, DEFAULT, 700),
                Arguments.of(31, true, false, DEFAULT, 500),
                Arguments.of(30, false, true, DEFAULT, 600),
                Arguments.of(30, true, false, DEFAULT, 400),
                Arguments.of(30, true, true, MAXIMUM, 1120),
                Arguments.of(30, true, true, HIGH, 980),
                Arguments.of(30, true, true, MEDIUM, 840)
        );
    }

    @Test
    @Tag("Negative")
    public void fragileIsnotApplicableWithLongDistance() {
        assertThatThrownBy(() -> calculate(31, true, true, MAXIMUM)).isInstanceOf(BusinessException.class);
    }

    @Test
    @Tag("Negative")
    public void distanceShouldNotBeZero() {
        assertThatThrownBy(() -> calculate(0, true, true, MAXIMUM)).isInstanceOf(BusinessException.class);
    }

    @ParameterizedTest(name = "Проверка null: distance is {0}, isLarge is {1}, isFragile is {2}, workload is {3}")
    @MethodSource("nullChecks")
    @Tag("Negative")
    public void checkNullValues(Integer distance, Boolean isLarge, Boolean isFragile, Workload workload) {
        assertThatThrownBy(() -> calculate(distance, isLarge, isFragile, workload)).isInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> nullChecks() {
        return Stream.of(
                Arguments.of(null, false, false, DEFAULT),
                Arguments.of(2, null, false, DEFAULT),
                Arguments.of(2, true, null, DEFAULT),
                Arguments.of(2, true, true, null)
        );
    }
}
