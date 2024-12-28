package com.example.mathluv

import kotlin.random.Random

object Hard {
    // Konstanta yang bisa diakses di dalam object
    private const val ERROR_VALUE = 77777777
    private const val NUMBER_MIN = 1
    private const val NUMBER_MAX = 100
    private val operators = arrayOf("+", "-", "x", "/")

    private fun makeOperation(num1: Int, num2: Int, op: String): Int {
        return try {
            when (op) {
                "+" -> num1.plus(num2)
                "-" -> num1.minus(num2)
                "x" -> num1.times(num2)
                "/" -> if (num2 != 0) {
                    num1.div(num2)
                } else {
                    ERROR_VALUE
                }
                else -> throw IllegalArgumentException("Operator tidak valid: $op")
            }
        } catch (e: ArithmeticException) {
            ERROR_VALUE
        }
    }

    fun getQuestions(): Pair<String, Int> {
        // Generate angka random
        val numbers = List(4) { Random.nextInt(NUMBER_MIN, NUMBER_MAX) }
        val (num1, num2, num3, num4) = numbers
        val (operator1, operator2, operator3) = List(3) { operators.random() }

        val bracketPosition = Random.nextInt(1, 4)
        val problem = generateProblem(
            num1, num2, num3, num4,
            operator1, operator2, operator3,
            bracketPosition
        )
        val answer = calculateAnswer(
            num1, num2, num3, num4,
            operator1, operator2, operator3,
            bracketPosition
        )

        return Pair(problem, answer)
    }

    private fun generateProblem(
        num1: Int, num2: Int, num3: Int, num4: Int,
        op1: String, op2: String, op3: String,
        bracketPos: Int
    ): String = when (bracketPos) {
        1 -> "(($num1 $op1 $num2) $op2 $num3) $op3 $num4"
        2 -> "($num1 $op1 $num2 ($op2 $num3)) $op3 $num4"
        else -> "($num1 $op1 $num2) $op2 ($num3 $op3 $num4)"
    }

    private fun calculateAnswer(
        num1: Int, num2: Int, num3: Int, num4: Int,
        op1: String, op2: String, op3: String,
        bracketPos: Int
    ): Int {
        val firstResult = when (bracketPos) {
            1 -> makeOperation(num1, num2, op1)
            2 -> makeOperation(num2, num3, op2)
            else -> makeOperation(num1, num2, op1)
        }
        if (firstResult == ERROR_VALUE) return ERROR_VALUE
        val secondResult = when (bracketPos) {
            1 -> makeOperation(firstResult, num3, op2)
            2 -> makeOperation(num1, firstResult, op1)
            else -> makeOperation(num3, num4, op3)
        }
        if (secondResult == ERROR_VALUE) return ERROR_VALUE
        return when (bracketPos) {
            3 -> makeOperation(firstResult, secondResult, op2)
            else -> makeOperation(secondResult, num4, op3)
        }
    }
}