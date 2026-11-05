package com.example.docinhos

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.docinhos.ui.theme.ReceitasTheme
import org.junit.Rule
import org.junit.Test

class DocinhosTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun vinteConvidados_oitentaBrigadeiros() {
        composeTestRule.setContent {
            ReceitasTheme {
                Docinhos()
            }
        }

        composeTestRule.onNodeWithText("Convidados").performTextInput("20")

        composeTestRule.onNodeWithText("80").assertExists()
    }
}
