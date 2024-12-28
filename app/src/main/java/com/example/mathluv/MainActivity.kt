package com.example.mathluv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathluv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_QUESTION_TYPE = "questionType"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding()
        setupGameModeButtons()
    }

    private fun initializeViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupGameModeButtons() {
        with(binding) {
            btnEasy.setOnClickListener { navigateToGame(GameMode.EASY) }
            btnMedium.setOnClickListener { navigateToGame(GameMode.MEDIUM) }
            btnHard.setOnClickListener { navigateToGame(GameMode.HARD) }
        }
    }

    private fun navigateToGame(gameMode: GameMode) {
        Intent(this, PlayActivity::class.java).apply {
            putExtra(EXTRA_QUESTION_TYPE, gameMode.value)
            startActivity(this)
        }
    }

    private enum class GameMode(val value: String) {
        EASY("Easy"),
        MEDIUM("Medium"),
        HARD("Hard")
    }
}