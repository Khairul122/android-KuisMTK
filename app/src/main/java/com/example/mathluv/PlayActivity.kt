package com.example.mathluv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.mathluv.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {
    companion object {
        private const val MAX_QUESTIONS = 10
        private const val COUNTDOWN_INTERVAL = 1000L
        private const val EXTRA_QUESTION_TYPE = "questionType"
        private const val EXTRA_SCORE = "score"
        private const val EXTRA_DATASET = "dataSet"
    }

    private var binding: ActivityPlayBinding? = null
    private var timer: CountDownTimer? = null

    private var position = 0
    private var timeGiven = 0
    private var score = 0
    private var questionDataList = ArrayList<Question>(MAX_QUESTIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        setupGame()
        setupClickListeners()
    }

    private fun initializeBinding() {
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun setupGame() {
        val questionType = intent.getStringExtra(EXTRA_QUESTION_TYPE)
        questionDataList = QuestionList(questionType).getQuestionList()
        setGivenTime(questionType)
        initializeGameState()
    }

    private fun initializeGameState() {
        updateQuestion()
        updateOption()
        updateHorizontalProgressBar()
        startTimer()
    }

    private fun setupClickListeners() {
        binding?.apply {
            btnOption1.setOnClickListener { onSelectOption(btnOption1.text.toString()) }
            btnOption2.setOnClickListener { onSelectOption(btnOption2.text.toString()) }
            btnOption3.setOnClickListener { onSelectOption(btnOption3.text.toString()) }
            btnOption4.setOnClickListener { onSelectOption(btnOption4.text.toString()) }
        }
    }

    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
        endGame()
    }

    private fun updateQuestion() {
        binding?.tvQuestion?.text = questionDataList.getOrNull(position)?.problem.orEmpty()
    }

    private fun updateOption() {
        binding?.apply {
            questionDataList.getOrNull(position)?.let { question ->
                btnOption1.text = question.option1
                btnOption2.text = question.option2
                btnOption3.text = question.option3
                btnOption4.text = question.option4
            }
        }
    }

    private fun updateHorizontalProgressBar() {
        binding?.horizontalProgressBar?.incrementProgressBy(1)
    }

    private fun setGivenTime(level: String?) {
        timeGiven = when (level?.lowercase()) {
            "easy" -> 10000
            "medium" -> 12000
            else -> 15000
        }
    }

    private fun startTimer() {
        timer?.cancel()

        var count = timeGiven / COUNTDOWN_INTERVAL.toInt()
        binding?.apply {
            circularProgressBar.progress = count
            circularProgressBar.max = count
        }

        timer = object : CountDownTimer(timeGiven.toLong(), COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.apply {
                    circularProgressBar.incrementProgressBy(-1)
                    count--
                    tvCountDown.text = count.toString()
                }
            }

            override fun onFinish() {
                setNextRound()
            }
        }.start()
    }

    private fun onSelectOption(option: String) {
        questionDataList.getOrNull(position)?.let { question ->
            if (option == question.answer) score++
            question.selectedOption = option
        }
        setNextRound()
    }

    private fun setNextRound() {
        if (position < MAX_QUESTIONS - 1) {
            position++
            initializeGameState()
        } else {
            endGame()
        }
    }

    private fun endGame() {
        Intent(this, FinishActivity::class.java).apply {
            putExtra(EXTRA_SCORE, score)
            putExtra(EXTRA_DATASET, questionDataList)
            startActivity(this)
        }
        cleanup()
    }

    private fun cleanup() {
        timer?.cancel()
        timer = null
        binding = null
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanup()
    }
}