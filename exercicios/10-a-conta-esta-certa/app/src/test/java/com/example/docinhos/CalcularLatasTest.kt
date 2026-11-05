package com.example.docinhos

import org.junit.Assert.assertEquals
import org.junit.Test

class CalcularLatasTest {

    @Test
    fun sessentaBrigadeiros_duasLatas() {
        val latas = calcularLatas(brigadeiros = 60, inteiras = false)

        assertEquals(2.0, latas, 0.001)
    }

    // TODO: teste 45 brigadeiros, sem arredondar. A resposta certa é 1,5 lata.
}
