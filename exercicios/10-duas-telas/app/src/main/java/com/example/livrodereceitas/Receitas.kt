package com.example.livrodereceitas

// O que muda de uma receita para outra, escolhido pelo número da receita (de 1 a 6).
// São os when do módulo 9, agora em funções, para as duas telas usarem.

fun imagemDa(receita: Int): Int {
    return when (receita) {
        1 -> R.drawable.brigadeiro
        2 -> R.drawable.pao_de_queijo
        3 -> R.drawable.bolo_de_cenoura
        4 -> R.drawable.pudim
        5 -> R.drawable.coxinha
        else -> R.drawable.acai
    }
}

fun nomeDa(receita: Int): Int {
    return when (receita) {
        1 -> R.string.brigadeiro
        2 -> R.string.pao_de_queijo
        3 -> R.string.bolo_de_cenoura
        4 -> R.string.pudim
        5 -> R.string.coxinha
        else -> R.string.acai
    }
}

fun detalhesDa(receita: Int): Int {
    return when (receita) {
        1 -> R.string.brigadeiro_detalhes
        2 -> R.string.pao_de_queijo_detalhes
        3 -> R.string.bolo_de_cenoura_detalhes
        4 -> R.string.pudim_detalhes
        5 -> R.string.coxinha_detalhes
        else -> R.string.acai_detalhes
    }
}

fun ingredientesDa(receita: Int): Int {
    return when (receita) {
        1 -> R.string.brigadeiro_ingredientes
        2 -> R.string.pao_de_queijo_ingredientes
        3 -> R.string.bolo_de_cenoura_ingredientes
        4 -> R.string.pudim_ingredientes
        5 -> R.string.coxinha_ingredientes
        else -> R.string.acai_ingredientes
    }
}

fun preparoDa(receita: Int): Int {
    return when (receita) {
        1 -> R.string.brigadeiro_preparo
        2 -> R.string.pao_de_queijo_preparo
        3 -> R.string.bolo_de_cenoura_preparo
        4 -> R.string.pudim_preparo
        5 -> R.string.coxinha_preparo
        else -> R.string.acai_preparo
    }
}
