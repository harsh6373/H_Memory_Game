package com.harsh02.h_memory_game.models


data class MemoryCard(
    val identifier :Int,
    var isFaceUp :Boolean = false,
    var isMatched :Boolean = false
)