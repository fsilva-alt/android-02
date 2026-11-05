package com.example.livrodereceitas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CoresClaras = lightColorScheme(
    primary = Terracota,
    onPrimary = Color.White,
    primaryContainer = TerracotaSuave,
    onPrimaryContainer = Color(0xFF380D00),
    secondary = Cafe,
    onSecondary = Color.White,
    secondaryContainer = TerracotaSuave,
    onSecondaryContainer = Color(0xFF2C160D),
    tertiary = Mostarda,
    onTertiary = Color.White,
    background = Creme,
    onBackground = Tinta,
    surface = Creme,
    onSurface = Tinta,
    surfaceVariant = Color(0xFFF5DED5),
    onSurfaceVariant = TintaSuave,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFFFF1EB),
    surfaceContainer = Color(0xFFFCEAE3),
    surfaceContainerHigh = Color(0xFFF6E4DD),
    surfaceContainerHighest = CremeEscuro,
    outline = Color(0xFF85736C),
    outlineVariant = Color(0xFFD8C2B9)
)

private val CoresEscuras = darkColorScheme(
    primary = Pessego,
    onPrimary = Color(0xFF5B1B00),
    primaryContainer = PessegoEscuro,
    onPrimaryContainer = TerracotaSuave,
    secondary = Areia,
    onSecondary = Color(0xFF442A20),
    secondaryContainer = Color(0xFF5D4035),
    onSecondaryContainer = TerracotaSuave,
    tertiary = Palha,
    onTertiary = Color(0xFF3A3005),
    background = Noite,
    onBackground = Giz,
    surface = Noite,
    onSurface = Giz,
    surfaceVariant = TintaSuave,
    onSurfaceVariant = GizSuave,
    surfaceContainerLowest = Color(0xFF140C09),
    surfaceContainerLow = Color(0xFF231A16),
    surfaceContainer = Color(0xFF271E1A),
    surfaceContainerHigh = Color(0xFF322824),
    surfaceContainerHighest = NoiteClara,
    outline = Color(0xFFA08D85),
    outlineVariant = TintaSuave
)

@Composable
fun ReceitasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) CoresEscuras else CoresClaras,
        typography = Typography,
        content = content
    )
}
