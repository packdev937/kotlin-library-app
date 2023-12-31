package com.group.libraryapp.calculator

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculatorTest {

    private lateinit var calculator: Calculator

    @BeforeEach
    fun setUp() {
        calculator = Calculator(10)
    }

    @Test
    fun 덧셈() {
        // when
        calculator.add(3)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(13)
    }

    @Test
    fun 뺄셈() {
        // when
        calculator.minus(5)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(5)
    }

    @Test
    fun 곱셈() {
        // when
        calculator.multiply(3)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(30)
    }

    @Test
    fun 나눗셈() {
        // when
        calculator.divide(2)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(5)
    }

    @Test
    fun 나눗셈은_0으로_나눌_수_없다() {
        //given
        val calculator = Calculator(10)

        // then
        Assertions.assertThatThrownBy {
            calculator.divide(0)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}