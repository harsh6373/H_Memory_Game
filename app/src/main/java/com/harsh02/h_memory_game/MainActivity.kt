package com.harsh02.h_memory_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard: RecyclerView
    private lateinit var tvnummoves: TextView
    private lateinit var tvnumpairs: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvBoard = findViewById(R.id.rv_board)
        tvnummoves = findViewById(R.id.tv_num_moves)
        tvnumpairs = findViewById(R.id.tv_num_pairs)
    }
}