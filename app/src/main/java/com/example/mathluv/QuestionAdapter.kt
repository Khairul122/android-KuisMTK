package com.example.mathluv

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(private val dataSet: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProblem: TextView = view.findViewById(R.id.rvTvProblem)
        val option1: TextView = view.findViewById(R.id.rvTvOption1)
        val option2: TextView = view.findViewById(R.id.rvTvOption2)
        val option3: TextView = view.findViewById(R.id.rvTvOption3)
        val option4: TextView = view.findViewById(R.id.rvTvOption4)
        val selectedAnswer: TextView = view.findViewById(R.id.selectedAnswer)
        val correctAnswer: TextView = view.findViewById(R.id.correctAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        with(holder) {
            tvProblem.text = currentItem.problem
            option1.text = currentItem.option1
            option2.text = currentItem.option2
            option3.text = currentItem.option3
            option4.text = currentItem.option4
            selectedAnswer.text = StringBuilder()
                .append("Jawaban Anda: ")
                .append(currentItem.selectedOption)
                .toString()

            correctAnswer.text = StringBuilder()
                .append("Jawaban Benar: ")
                .append(currentItem.answer)
                .toString()
            val backgroundColor = if (position % 2 != 0) "#000000" else "#3c3f41"
            itemView.setBackgroundColor(Color.parseColor(backgroundColor))
            val isCorrect = currentItem.selectedOption == currentItem.answer
            selectedAnswer.setTextColor(
                Color.parseColor(if (isCorrect) "#4CAF50" else "#F44336")
            )
        }
    }
}