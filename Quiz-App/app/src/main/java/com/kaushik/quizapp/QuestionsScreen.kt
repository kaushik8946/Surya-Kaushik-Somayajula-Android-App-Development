package com.kaushik.quizapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaushik.quizapp.questions.Question
import com.kaushik.quizapp.questions.questionsMap

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuestionScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    val difficulty = sharedPreferences.getString("difficulty", "").toString()
    val topic = sharedPreferences.getString("topic", "").toString()
    val questionsList: List<Question> = questionsMap[topic]?.get(difficulty)!!
    val options = mutableListOf<List<String>>()
    questionsList.forEach { questionItem ->
        val optionsList = questionItem.incorrectAnswers.toMutableList()
        optionsList.add(questionItem.correctAnswer)
        options.add(optionsList)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome ",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                actions = {
                    Button(
                        onClick = {
                            var score = 0
                            for (i in 0 until 10) {
                                val response = sharedPreferences.getString("$i", "")
                                if (response == questionsList[i].correctAnswer) {
                                    score++
                                }
                            }
                            sharedPreferences.edit().putInt("score", score).commit()
                            navController.navigate("results")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Submit",
                            color = Color.Blue,
                            fontSize = 18.sp
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in questionsList.indices) {
                val question = questionsList[i].question
                Card(
                    elevation = 10.dp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = """
                            Q ${i + 1}
                            $question
                            """.trimIndent(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                var selectedOption by remember {
                    mutableStateOf("")
                }
                val optionsList = options[i]
                optionsList.forEach { option ->
                    val textColor =
                        if (selectedOption == option)
                            Color(0xff004643)
                        else
                            Color(0xff000000)
                    val rowColor =
                        if (selectedOption == option)
                            Color(0xffABD1C6)
                        else
                            Color(0xffffffff)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .background(rowColor)
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = {
                                selectedOption = option
                                sharedPreferences.edit().putString(i.toString(), option).commit()
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xff004643),
                                unselectedColor = Color(0xff000000)
                            )
                        )
                        Text(
                            text = option,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}
