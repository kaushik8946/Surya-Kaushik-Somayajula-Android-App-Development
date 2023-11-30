package com.kaushik.quizapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kaushik.quizapp.questions.animalQuestionHard
import com.kaushik.quizapp.questions.animalQuestionMedium
import com.kaushik.quizapp.questions.generalKnowledgeEasy
import com.kaushik.quizapp.questions.generalKnowledgeHard
import com.kaushik.quizapp.questions.generalKnowledgeMedium
import com.kaushik.quizapp.questions.geographyEasy
import com.kaushik.quizapp.questions.geographyHard
import com.kaushik.quizapp.questions.geographyMedium
import com.kaushik.quizapp.questions.historyEasy
import com.kaushik.quizapp.questions.historyHard
import com.kaushik.quizapp.questions.historyMedium
import com.kaushik.quizapp.questions.questionsMap
import com.kaushik.quizapp.questions.vehiclesEasy
import com.kaushik.quizapp.questions.vehiclesHard
import com.kaushik.quizapp.questions.vehiclesMedium

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    init {
        questionsMap["Animals"] = mutableMapOf()
        questionsMap["Animals"]?.set("Medium", animalQuestionMedium)
        questionsMap["Animals"]?.set("Hard", animalQuestionHard)

        questionsMap["GeneralKnowledge"] = mutableMapOf()
        questionsMap["GeneralKnowledge"]?.set("Easy", generalKnowledgeEasy)
        questionsMap["GeneralKnowledge"]?.set("Medium", generalKnowledgeMedium)
        questionsMap["GeneralKnowledge"]?.set("Hard", generalKnowledgeHard)

        questionsMap["Geography"] = mutableMapOf()
        questionsMap["Geography"]?.set("Easy", geographyEasy)
        questionsMap["Geography"]?.set("Medium", geographyMedium)
        questionsMap["Geography"]?.set("Hard", geographyHard)

        questionsMap["History"] = mutableMapOf()
        questionsMap["History"]?.set("Easy", historyEasy)
        questionsMap["History"]?.set("Medium", historyMedium)
        questionsMap["History"]?.set("Hard", historyHard)

        questionsMap["Vehicles"] = mutableMapOf()
        questionsMap["Vehicles"]?.set("Easy", vehiclesEasy)
        questionsMap["Vehicles"]?.set("Medium", vehiclesMedium)
        questionsMap["Vehicles"]?.set("Hard", vehiclesHard)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        for (i in 0 until 10) {
            sharedPreferences.edit().putString("$i", null).commit()
        }
        setContent {
            MyNavigation()
        }
    }

    override fun onStart() {
        super.onStart()
        for (i in 0 until 10) {
            sharedPreferences.edit().putString("$i", null).commit()
        }
    }

    override fun onBackPressed() {
        for (i in 0 until 10) {
            sharedPreferences.edit().putString("$i", null).commit()
        }
        super.onBackPressed()
    }
}
