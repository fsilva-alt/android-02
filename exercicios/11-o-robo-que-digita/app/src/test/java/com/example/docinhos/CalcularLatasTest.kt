package com.example.docinhos

import org.junit.Assert.assertEquals
import org.junit.Test

class CalcularLatasTest {

    @Test
    fun sessentaBrigadeiros_duasLatas() {
        val latas = calcularLatas(brigadeiros = 60, inteiras = false)

        assertEquals(2.0, latas, 0.001)
    }

    @Test
    fun quarentaECincoBrigadeiros_umaLataEMeia() {
        val latas = calcularLatas(brigadeiros = 45, inteiras = false)

        assertEquals(1.5, latas, 0.001)
    }

    // Missão extra
    @Test
    fun quarentaECincoBrigadeiros_latasInteiras_duasLatas() {
        val latas = calcularLatas(brigadeiros = 45, inteiras = true)

        assertEquals(2.0, latas, 0.001)
    }
}
