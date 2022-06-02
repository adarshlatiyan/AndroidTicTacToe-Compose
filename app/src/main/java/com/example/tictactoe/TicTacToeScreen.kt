package com.example.tictactoe

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ResetButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier
            .padding(16.dp)
            .height(50.dp)
    ) {
        Text(
            text = "Restart",
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ButtonGrid(
    modifier: Modifier = Modifier,
    board: List<TicTacToeUtils.BlockState>,
    onclick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .aspectRatio(1f)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tic_tac_toe_board),
            contentDescription = "Board",
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                for (i in 0..2) {
                    TicTacToeBlock(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        type = board[i]
                    ) { onclick(i) }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                for (i in 3..5) {
                    TicTacToeBlock(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        type = board[i]
                    ) { onclick(i) }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                for (i in 6..8) {
                    TicTacToeBlock(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        type = board[i]
                    ) { onclick(i) }
                }
            }
        }
    }
}

@Composable
fun TicTacToeBlock(modifier: Modifier = Modifier, type: TicTacToeUtils.BlockState, onclick: () -> Unit) {
    val context = LocalContext.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .clickable(interactionSource = interactionSource, indication = null) {
                if (type == TicTacToeUtils.BlockState.EMPTY) {
                    onclick()
                } else {
                    Toast
                        .makeText(context, "This is an invalid move", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .fillMaxHeight()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if (type != TicTacToeUtils.BlockState.EMPTY) {
            type.drawableId?.let {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    painter = painterResource(id = it),
                    tint = Color.Red,
                    contentDescription = ""
                )
            }
        }
    }
}