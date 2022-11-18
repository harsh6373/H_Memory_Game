package com.harsh02.h_memory_game.models

import com.harsh02.h_memory_game.utils.DEFAULT_ICONS

class MemoryGame (private val boardSize: BoardSize){


    val cards : List<MemoryCard>
    var numPairFound = 0

    private var numCardFlips = 0

    private var indexofSingleSelectedCard: Int? = null

    init {
        val chosenImages =  DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randommized_images = (chosenImages + chosenImages).shuffled()
        cards =  randommized_images.map { MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean{
        numCardFlips++
        val card :MemoryCard = cards[position]

        var foundMatch = false
        if (indexofSingleSelectedCard == null){
            restoreCards()
            indexofSingleSelectedCard = position
        }else{
             foundMatch = checkforMatch(indexofSingleSelectedCard!!,position)
            indexofSingleSelectedCard =null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkforMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier){
            return false
        }

        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards){
           if (!card.isMatched) {
               card.isFaceUp = false
           }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairFound == boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp

    }

    fun getNumMoves(): Int {
        return numCardFlips / 2
    }


}