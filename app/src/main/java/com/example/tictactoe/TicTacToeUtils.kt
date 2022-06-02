package com.example.tictactoe

import androidx.annotation.DrawableRes
import com.example.tictactoe.TicTacToeUtils.BlockState.*
import kotlin.random.Random

object TicTacToeUtils {
    val userBlock = O // User
    val computerBlock = X // Computer

    fun isBoardFull(board: List<BlockState>): Boolean {
        return !board.any { it == EMPTY }
    }

    private fun copyBoard(board: List<BlockState>): MutableList<BlockState> {
        val newBoard = MutableList(9) { EMPTY }

        for (i in 0 until board.count()) {
            newBoard[i] = board[i]
        }
        return newBoard
    }

    private fun chooseRandomMove(board: List<BlockState>, moves: List<Int>): Int {
        val possibleMoves = mutableListOf<Int>()

        for (i in moves) {
            if (board[i] == EMPTY) possibleMoves.add(i)
        }

        return if (possibleMoves.isEmpty()) {
            -1
        } else {
            val index = Random.nextInt(possibleMoves.count())
            possibleMoves[index]
        }
    }

    fun computerMove(board: List<BlockState>): Int {
        //check if computer can win in this move
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == EMPTY) copy[i] = computerBlock

            //check for win
            if (isGameWon(copy, computerBlock)) return i
        }


        //check if player could win in the next move
        for (i in 0 until board.count()) {
            val copy = copyBoard(board)
            if (copy[i] == EMPTY) copy[i] = userBlock

            if (isGameWon(copy, userBlock)) return i
        }

        //try to make corners if it is free
        val move = chooseRandomMove(board, listOf(0, 2, 6, 8))
        if (move != -1) return move

        //try to take center if it is free
        if (board[4] == EMPTY) return 4

        //finally try to make the sides
        return chooseRandomMove(board, listOf(1, 3, 5, 7))
    }

    fun isGameWon(board: List<BlockState>, player: BlockState): Boolean =
        //check rows
        if (board[0] == player && board[1] == player && board[2] == player) true
        else if (board[3] == player && board[4] == player && board[5] == player) true
        else if (board[6] == player && board[7] == player && board[8] == player) true

        //check columns
        else if (board[0] == player && board[3] == player && board[6] == player) true
        else if (board[1] == player && board[4] == player && board[7] == player) true
        else if (board[2] == player && board[5] == player && board[8] == player) true

        //check diagonals
        else if (board[2] == player && board[4] == player && board[6] == player) true
        else board[0] == player && board[4] == player && board[8] == player


    fun gameResult(board: List<BlockState>): String {
        when {
            isGameWon(board, userBlock) -> return "You Won!!"
            isGameWon(board, computerBlock) -> return "Computer Won"
            isBoardFull(board) -> return "It is a Tie"
        }
        return "Tie"
    }

    enum class BlockState(@DrawableRes val drawableId: Int? = null) {
        X(R.drawable.ic_cross),
        O(R.drawable.ic_circle),
        EMPTY
    }
}