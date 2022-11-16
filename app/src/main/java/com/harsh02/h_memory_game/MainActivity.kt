package com.harsh02.h_memory_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harsh02.h_memory_game.models.BoardSize
import com.harsh02.h_memory_game.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard: RecyclerView
    private lateinit var tvnummoves: TextView
    private lateinit var tvnumpairs: TextView


    private var boardSize:BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvBoard = findViewById(R.id.rv_board)
        tvnummoves = findViewById(R.id.tv_num_moves)
        tvnumpairs = findViewById(R.id.tv_num_pairs)

       val chosenImages =  DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randommized_images = (chosenImages + chosenImages).shuffled()


        rvBoard.adapter = MemoryBoardAdapter(this,boardSize,randommized_images)
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this,boardSize.getWidth())

    }
}