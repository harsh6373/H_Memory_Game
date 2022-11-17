package com.harsh02.h_memory_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.harsh02.h_memory_game.models.BoardSize
import com.harsh02.h_memory_game.models.MemoryCard
import com.harsh02.h_memory_game.models.MemoryGame
import com.harsh02.h_memory_game.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    private lateinit var clroot: ConstraintLayout
    private lateinit var rvBoard: RecyclerView
    private lateinit var tvnummoves: TextView
    private lateinit var tvnumpairs: TextView
    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter


    companion object{
        private const val TAG = "MainActivity"
    }

    private var boardSize:BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        clroot = findViewById(R.id.clroot)
        rvBoard = findViewById(R.id.rv_board)
        tvnummoves = findViewById(R.id.tv_num_moves)
        tvnumpairs = findViewById(R.id.tv_num_pairs)

        memoryGame = MemoryGame(boardSize)

        adapter = MemoryBoardAdapter(this,boardSize,memoryGame.cards,object: MemoryBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
               // Log.i(TAG, "onCardClicked: $position")

                updateGameWithFlip(position)
            }

        })

        rvBoard.adapter = adapter

        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this,boardSize.getWidth())

    }

    private fun updateGameWithFlip(position: Int) {

        if (memoryGame.haveWonGame()){

            Snackbar.make(clroot,"You already Won!!",Snackbar.LENGTH_LONG).show()
            return
        }

        if (memoryGame.isCardFaceUp(position)){
            Snackbar.make(clroot,"Invalid Move!!",Snackbar.LENGTH_LONG).show()
            return
        }

        memoryGame.flipCard(position)
        adapter.notifyDataSetChanged()
    }
}