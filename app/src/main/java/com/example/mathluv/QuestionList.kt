package com.example.mathluv

import kotlin.random.Random

class QuestionList(private val questionType: String?) {
    companion object {
        private const val QUESTION_COUNT = 10
        private const val ERROR_VALUE = 77777777
        private const val NA_ANSWER = "NA"
        private const val MIN_RANDOM_OFFSET = -9
        private const val MAX_RANDOM_OFFSET = 10
        private const val MIN_RANDOM_VALUE = 1
        private const val MAX_RANDOM_VALUE = 4000
        private const val OPTION_COUNT = 4
    }

    private val questionList = ArrayList<Pair<String, Int>>(QUESTION_COUNT)
    private val questionDataList = ArrayList<Question>(QUESTION_COUNT)

    fun getQuestionList(): ArrayList<Question> {
        generateQuestions()
        generateQuestionData()
        return questionDataList
    }

    private fun generateQuestions() {
        repeat(QUESTION_COUNT) {
            val question = when (questionType?.lowercase()) {
                "easy" -> generateEasyQuestion()
                "medium" -> generateMediumQuestion()
                else -> generateHardQuestion()
            }
            questionList.add(question)
        }
    }

    private fun generateEasyQuestion(): Pair<String, Int> {
        val num1 = Random.nextInt(-50, 50)
        val num2 = Random.nextInt(-50, 50)
        val operator = arrayOf("+", "-", "x", "/").random()

        val problem = "$num1 $operator $num2"
        val answer = calculateAnswer(num1, num2, operator)

        return Pair(problem, answer)
    }

    private fun generateMediumQuestion(): Pair<String, Int> {
        // Implementasi logika untuk soal medium
        // Sesuaikan dengan kebutuhan aplikasi Anda
        return generateEasyQuestion() // Sementara menggunakan easy sebagai placeholder
    }

    private fun generateHardQuestion(): Pair<String, Int> {
        // Implementasi logika untuk soal hard
        // Sesuaikan dengan kebutuhan aplikasi Anda
        return generateEasyQuestion() // Sementara menggunakan easy sebagai placeholder
    }

    private fun calculateAnswer(num1: Int, num2: Int, operator: String): Int {
        return try {
            when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "x" -> num1 * num2
                "/" -> if (num2 != 0) num1 / num2 else ERROR_VALUE
                else -> ERROR_VALUE
            }
        } catch (e: ArithmeticException) {
            ERROR_VALUE
        }
    }

    private fun generateQuestionData() {
        for (i in 0 until QUESTION_COUNT) {
            val (problem, answer) = questionList[i]
            val options = generateOptions(answer)
            val correctAnswer = if (answer == ERROR_VALUE) NA_ANSWER else answer.toString()

            Question(
                problem = problem,
                answer = correctAnswer,
                option1 = options[0],
                option2 = options[1],
                option3 = options[2],
                option4 = options[3],
                selectedOption = "none"
            ).also { questionDataList.add(it) }
        }
    }

    private fun generateOptions(answer: Int): List<String> {
        return if (answer != ERROR_VALUE) {
            generateValidOptions(answer)
        } else {
            generateErrorOptions()
        }.shuffled()
    }

    private fun generateValidOptions(answer: Int): MutableList<String> {
        return mutableListOf(
            answer.toString(),
            generateRandomOffset(answer),
            generateRandomOffset(answer),
            NA_ANSWER
        )
    }

    private fun generateErrorOptions(): MutableList<String> {
        val baseValue = Random.nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE)
        return mutableListOf(
            generateRandomOffset(baseValue),
            generateRandomOffset(baseValue),
            generateRandomOffset(baseValue),
            NA_ANSWER
        )
    }

    private fun generateRandomOffset(baseValue: Int): String {
        return (baseValue + Random.nextInt(MIN_RANDOM_OFFSET, MAX_RANDOM_OFFSET)).toString()
    }
}