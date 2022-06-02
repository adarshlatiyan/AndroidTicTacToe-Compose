package com.example.tictactoe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.TicTacToeUtils.BlockState.*
import com.example.tictactoe.TicTacToeUtils.isBoardFull
import com.example.tictactoe.TicTacToeUtils.isGameWon
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var isGameOver = false

    private var winner = ""

    private var board = MutableList(9) { EMPTY }

    private var currentPlayer = TicTacToeUtils.userBlock

    var gameState by mutableStateOf<GameState>(
        GameState.GameActive(board)
    )

    fun setAction(action: UserAction) {
        when (action) {
            UserAction.Reset -> {
                reset()
            }

            is UserAction.PlayMove -> {
                play(action.blockIndex)
            }
        }
    }

    private fun play(move: Int) {
        if (isGameOver) return

        if (board[move] == EMPTY) {
            if (currentPlayer == X) {
                board[move] = X
                currentPlayer = O
                if (!isBoardFull(board) && !isGameWon(board, X)) {
                    val nextMove = TicTacToeUtils.computerMove(board)

                    board[nextMove] = O
                }
                currentPlayer = X

            } else {
                board[move] = O
                currentPlayer = X
                if (!isBoardFull(board) && !isGameWon(board, O)) {
                    val nextMove = TicTacToeUtils.computerMove(board)

                    board[nextMove] = X
                }
                currentPlayer = O
            }
        }

        isGameOver = isGameWon(board, X) || isGameWon(board, O) || isBoardFull(board)
        winner = TicTacToeUtils.gameResult(board)

        gameState = if (isGameOver) {
            GameState.GameOver(winner, board)
        } else {
            GameState.GameActive(board)
        }

        Log.d(TAG, "$board")
    }

    private fun reset() {
        viewModelScope.launch {
            isGameOver = false
            board = MutableList(9) { EMPTY }
            gameState = GameState.GameActive(board)
        }
    }

    sealed class UserAction {
        class PlayMove(val blockIndex: Int) : UserAction()
        object Reset : UserAction()
    }

    sealed class GameState(val currentBoard: List<TicTacToeUtils.BlockState>) {
        class GameActive(
            currentBoard: List<TicTacToeUtils.BlockState>
        ) : GameState(currentBoard)

        class GameOver(
            val winMessage: String,
            currentBoard: List<TicTacToeUtils.BlockState>
        ) : GameState(currentBoard)
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}