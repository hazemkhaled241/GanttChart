package com.hazem.ganttchart


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


var gridColors = mutableStateOf(Array(21) { Array(31) { Color.Transparent } })
val cellSize = 50.dp

@Composable
fun Grid() {
    val state = rememberScrollState()

    val isScrollable = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = if (isScrollable.value) {
                Modifier
                    .horizontalScroll(state)
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ ->
                            val rowIndex = (change.position.y / cellSize.toPx()).toInt()
                            val colIndex = (change.position.x / cellSize.toPx()).toInt()
                            onSquareDragged(rowIndex, colIndex)
                        }

                    }
            } else {
                Modifier
                    .horizontalScroll(state)

            }) {
            Column(
                modifier = Modifier
                    .width(1550.dp)
                    .verticalScroll(rememberScrollState()),

                ) {

                (0..20).forEach { rowIndex ->

                    Row {
                        (0..30).forEach { colIndex ->
                            Box(
                                modifier = Modifier
                                    .background(gridColors.value[rowIndex][colIndex])
                                    .size(50.dp)
                                    .border(width = 1.dp, color = Color.Black)

                            )
                            {
                                Text(
                                    text = if (rowIndex == 0) "0" else colIndex.toString(),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }

                    }
                }
            }


        }
        Button(modifier = Modifier.align(Alignment.BottomCenter),onClick = { isScrollable.value = !isScrollable.value }) {

        }
    }
}


fun onSquareDragged(rowIndex: Int, colIndex: Int) {
    gridColors.value[rowIndex][colIndex] = Color.Blue
    gridColors.value = gridColors.value.copyOf()
}

