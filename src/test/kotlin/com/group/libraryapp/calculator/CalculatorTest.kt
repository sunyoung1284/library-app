package com.group.libraryapp.calculator

fun main(){
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
    calculatorTest.minusTest()
    calculatorTest.multiplyTest()
    calculatorTest.divideTest()
    calculatorTest.divideExceptionTest()
}
class CalculatorTest {

    fun addTest(){
        //given
        val calulator = Calculator(5)

        //when
        calulator.add(3)

        //then
        if(calulator.number != 8){
            throw IllegalStateException()
        }
    }

    fun minusTest(){
        //given
        val calulator = Calculator(5)

        //when
        calulator.minus(3)

        //then
        if(calulator.number != 2){
            throw IllegalStateException()
        }
    }

    fun multiplyTest(){
        //given
        val calulator = Calculator(5)

        //when
        calulator.multiply(3)

        //then
        if(calulator.number != 15){
            throw IllegalStateException()
        }
    }

    fun divideTest(){
        //given
        val calulator = Calculator(5)

        //when
        calulator.divide(0)

        //then
        if(calulator.number != 2){
            throw IllegalStateException()
        }
    }

    fun divideExceptionTest(){
        //given
        val calulator = Calculator(5)

        //when
        try {
            calulator.divide(0)
        } catch (e: IllegalArgumentException){
            if(e.message != "0으로 나눌 수 없습니다."){
                throw IllegalStateException("메세지가 다릅니다.")
            }
            //테스트 성공!
            return
        } catch (e:Exception){
            throw IllegalStateException()
        }

        throw IllegalStateException("기대하는 예외가 발생하지 않았습니다.")
    }
}