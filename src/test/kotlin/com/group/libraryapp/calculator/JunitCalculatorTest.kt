package com.group.libraryapp.calculator

import org.junit.jupiter.api.Test
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.assertThrows

class JunitCalculatorTest {
    @Test
    fun addTest(){
        //given
        val calulator = Calculator(5)
        //when
        calulator.add(3)
        //then
        assertThat(calulator.number).isEqualTo(8)
    }

    @Test
    fun minusTest(){
        //given
        val calulator = Calculator(5)
        //when
        calulator.minus(3)
        //then
        assertThat(calulator.number).isEqualTo(2)
    }

    @Test
    fun mupltiplyTest(){
        //given
        val calulator = Calculator(5)
        //when
        calulator.multiply(3)
        //then
        assertThat(calulator.number).isEqualTo(15)
    }

    @Test
    fun devideTest(){
        //given
        val calulator = Calculator(5)
        //when
        calulator.divide(2)
        //then
        assertThat(calulator.number).isEqualTo(2)
    }

    fun devideExceptionTest(){
        //given
        val calulator = Calculator(5)
        //when & then
        assertThrows<IllegalArgumentException> {
            calulator.divide(0)
        }.apply{
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        }
    }
}