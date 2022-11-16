package com.harsh02.h_memory_game

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.harsh02.h_memory_game.models.BoardSize
import kotlin.math.min


class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cardImages: List<Int>
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    companion object{
        private const val MARGIN_SIZE = 10
        private const val TAG = "MemoryBoardAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardHeight = parent.height /boardSize.getHeight() -(2 * MARGIN_SIZE)
        val cardWeight = parent.width /boardSize.getWidth() - (2 * MARGIN_SIZE)
        val cardSideLength = min(cardHeight,cardWeight)

        val view :View =  LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)
        val layoutParams = view.findViewById<CardView>(R.id.cardview).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.height = cardSideLength
        layoutParams.width = cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(position)
    }

    override fun getItemCount() = boardSize.numCards


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageButton =itemView.findViewById<ImageButton>(R.id.imageButton)


        fun bind(position: Int) {
            imageButton.setImageResource(cardImages[position])
            imageButton.setOnClickListener {
                Log.i(TAG, "clicked position $position")
            }
        }
    }
}
