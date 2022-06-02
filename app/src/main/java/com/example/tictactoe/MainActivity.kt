package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                val gameState = viewModel.gameState

                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        ButtonGrid(
                            modifier = Modifier.fillMaxHeight(0.7f),
                            board = gameState.currentBoard,
                            onclick = {
                                viewModel.setAction(MainViewModel.UserAction.PlayMove(it))
                            }
                        )

                        if (gameState is MainViewModel.GameState.GameOver) {
                            Box {
                                Text(
                                    text = "Game is Over: ${gameState.winMessage}",
                                    fontSize = 20.sp
                                )
                            }
                        }

                        ResetButton(onClick = {
                            viewModel.setAction(MainViewModel.UserAction.Reset)
                        })
                    }
                }
            }
        }
    }
}