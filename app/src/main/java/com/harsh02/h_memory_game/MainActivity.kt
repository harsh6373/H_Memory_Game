package com.harsh02.h_memory_game

import android.animation.ArgbEvaluator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.jinatonic.confetti.CommonConfetti
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
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

        setUpBoard()

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_refresh -> {
                if (memoryGame.getNumMoves() >0 && !memoryGame.haveWonGame()){
                    showAlertDialog("Quit your current game?",null,View.OnClickListener {
                        setUpBoard()
                    })
                }else{
                    setUpBoard()
                }

            }
            R.id.menu_diffculty -> {
                showDiffcultyDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDiffcultyDialog() {
        val boarddiffculty = LayoutInflater.from(this).inflate(R.layout.dialog_board_diffculty,null)
        val radioGroupSize = boarddiffculty.findViewById<RadioGroup>(R.id.radio_group)

        when (boardSize){
            BoardSize.EASY -> radioGroupSize.check(R.id.rbeasy)
            BoardSize.MEDIUM -> radioGroupSize.check(R.id.rbmedium)
            BoardSize.HARD -> radioGroupSize.check(R.id.rbhard)
        }

        showAlertDialog("Choose Difficulty :-",boarddiffculty,View.OnClickListener {
            boardSize = when (radioGroupSize.checkedRadioButtonId) {
                R.id.rbeasy -> BoardSize.EASY
                R.id.rbmedium -> BoardSize.MEDIUM
                else -> BoardSize.HARD
            }
            setUpBoard()
        })
    }

    private fun showAlertDialog(title: String,view: View?,positiveButtonClickListener: View.OnClickListener) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("OK") {_, _ ->
                positiveButtonClickListener.onClick(null)
            }.show()
    }

    private fun setUpBoard() {

        when(boardSize){

            BoardSize.EASY -> {
                tvnummoves.text = "Easy: 4 x 2"
                tvnumpairs.text = "Pairs: 0 / 4"
            }
            BoardSize.MEDIUM -> {
                tvnummoves.text = "Medium: 6 x 3"
                tvnumpairs.text = "Pairs: 0 / 9"
            }
            BoardSize.HARD -> {
                tvnummoves.text = "Medium: 6 x 4"
                tvnumpairs.text = "Pairs: 0 / 12"
            }
            else -> {}
        }

        tvnumpairs.setTextColor(ContextCompat.getColor(this,R.color.color_progressnone))
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
            Snackbar.make(clroot,"Invalid Move!!",Snackbar.LENGTH_SHORT).show()
            return
        }

        if(memoryGame.flipCard(position)){
            Log.i(TAG, "found a match num pairs found: ${memoryGame.numPairFound}")
            val color = ArgbEvaluator().evaluate(
                memoryGame.numPairFound.toFloat() / boardSize.getNumPairs(),
                ContextCompat.getColor(this,R.color.color_progressnone),
                ContextCompat.getColor(this,R.color.color_progress_full)
            ) as Int
            tvnumpairs.setTextColor(color)
            tvnumpairs.text = "Pairs: ${memoryGame.numPairFound} / ${boardSize.getNumPairs()}"

            if (memoryGame.haveWonGame()){
                Snackbar.make(clroot,"Congratulations You Won!!",Snackbar.LENGTH_LONG).show()
                CommonConfetti.rainingConfetti(clroot, intArrayOf(Color.GREEN,Color.BLUE,Color.RED,Color.MAGENTA,Color.YELLOW)).oneShot()
            }
        }

        tvnummoves.text = "Moves: ${memoryGame.getNumMoves()}"

        adapter.notifyDataSetChanged()
    }
}