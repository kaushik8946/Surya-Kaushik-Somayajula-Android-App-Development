package com.kaushik.quizapp.questions

data class Question(
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)

val questionsMap = mutableMapOf<String, MutableMap<String, List<Question>>>()
