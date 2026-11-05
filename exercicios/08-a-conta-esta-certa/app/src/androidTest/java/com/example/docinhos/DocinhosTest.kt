package com.example.docinhos

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createComposeRule
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

        // TODO 2: digite 20 no campo "Convidados" e confira se o total passa a ser 80
    }
}
