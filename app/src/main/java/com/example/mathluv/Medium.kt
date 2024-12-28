package com.example.mathluv

import kotlin.random.Random

object Medium {
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
        // Inisialisasi komponen soal
        val numbers = generateNumbers()
        val (num1, num2, num3) = numbers
        val operators = generateOperators()
        val (operator1, operator2) = operators
        val bracketPosition = Random.nextInt(1, 3)

        // Konstruksi soal dan jawaban
        val problem = generateProblem(
            num1, num2, num3,
            operator1, operator2,
            bracketPosition
        )

        val answer = calculateAnswer(
            num1, num2, num3,
            operator1, operator2,
            bracketPosition
        )

        return Pair(problem, answer)
    }

    private fun generateNumbers(): Triple<Int, Int, Int> {
        return Triple(
            Random.nextInt(NUMBER_MIN, NUMBER_MAX),
            Random.nextInt(NUMBER_MIN, NUMBER_MAX),
            Random.nextInt(NUMBER_MIN, NUMBER_MAX)
        )
    }

    private fun generateOperators(): Pair<String, String> {
        return Pair(operators.random(), operators.random())
    }

    private fun generateProblem(
        num1: Int, num2: Int, num3: Int,
        op1: String, op2: String,
        bracketPos: Int
    ): String = when (bracketPos) {
        1 -> "($num1 $op1 $num2) $op2 $num3"
        else -> "$num1 $op1 ($num2 $op2 $num3)"
    }

    private fun calculateAnswer(
        num1: Int, num2: Int, num3: Int,
        op1: String, op2: String,
        bracketPos: Int
    ): Int {
        val intermediateResult = when (bracketPos) {
            1 -> makeOperation(num1, num2, op1)
            else -> makeOperation(num2, num3, op2)
        }
        if (intermediateResult == ERROR_VALUE) return ERROR_VALUE
        return when (bracketPos) {
            1 -> makeOperation(intermediateResult, num3, op2)
            else -> makeOperation(num1, intermediateResult, op1)
        }
    }
}