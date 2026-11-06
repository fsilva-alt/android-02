// Arquivo de build da raiz do projeto: aqui entram os plugins usados pelos subprojetos.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}
