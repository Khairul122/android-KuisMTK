package com.example.mathluv

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FinishActivity : AppCompatActivity() {
    // Komponen UI sebagai properti class untuk efisiensi memori
    private lateinit var tvScore: TextView
    private lateinit var btnHome: Button
    private lateinit var recyclerView: RecyclerView

    // Konstanta untuk meningkatkan maintainability
    companion object {
        private const val EXTRA_SCORE = "score"
        private const val EXTRA_DATASET = "dataSet"
        private const val MAX_SCORE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        initializeViews()
        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        @Suppress("UNCHECKED_CAST", "DEPRECATION")
        val data = intent.getSerializableExtra(EXTRA_DATASET) as? ArrayList<Question>
            ?: ArrayList()
        tvScore.text = String.format("Your Score\n%d/%d", score, MAX_SCORE)
        setAdapterRecyclerView(data)
        setupClickListener()
    }

    private fun initializeViews() {
        tvScore = findViewById(R.id.tvScore)
        btnHome = findViewById(R.id.btnHome)
        recyclerView = findViewById(R.id.rvQuestionList)
    }

    private fun setAdapterRecyclerView(data: ArrayList<Question>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FinishActivity)
            adapter = QuestionAdapter(data)
            setHasFixedSize(true) // Optimasi performa
        }
    }

    private fun setupClickListener() {
        btnHome.setOnClickListener { finish() }
    }
}