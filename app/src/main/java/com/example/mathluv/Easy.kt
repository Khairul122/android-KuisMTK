package com.example.mathluv

import kotlin.random.Random

object Easy {
    private var answer: Int = 0
    private const val DIVISION_ERROR = 77777777
    private val VALID_OPERATORS = arrayOf("+", "-", "x", "/")

    fun getQuestions(): Pair<String, Int> {
        // Batasi rentang angka untuk mencegah integer overflow
        val num1 = Random.nextInt(-50, 50)
        val num2 = Random.nextInt(-50, 50)
        val operator = VALID_OPERATORS.random()

        val problem = "$num1 $operator $num2"

        answer = try {
            when (operator) {
                "+" -> num1.plus(num2)
                "-" -> num1.minus(num2)
                "x" -> num1.times(num2)
                "/" -> {
                    if (num2 != 0) {
                        num1.div(num2)
                    } else {
                        DIVISION_ERROR
                    }
                }
                else -> throw IllegalArgumentException("Operator tidak valid: $operator")
            }
        } catch (e: ArithmeticException) {
            DIVISION_ERROR
        }

        return Pair(problem, answer)
    }
}