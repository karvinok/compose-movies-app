package com.karvinok.composemovies.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun GeekBroTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.height(55.dp)
    ) {
        Spacer(modifier = Modifier.width(32.dp))
        SemiCircle(modifier = Modifier.width(45.dp).height(45.dp))
        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
        Cards(Modifier
            .width(50.dp)
            .height(23.dp))
        Spacer(modifier = Modifier.width(32.dp))
    }
}

const val ARC_FULL_ROTATION_DEGREE = 250f
const val ARC_START_OFFSET = 105f

@Composable
fun SemiCircle(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawArc(
            color = Color.White,
            startAngle = ARC_FULL_ROTATION_DEGREE - ARC_START_OFFSET,
            sweepAngle = ARC_FULL_ROTATION_DEGREE,
            useCenter = false,
            style = Stroke(width = 5.dp.toPx())
        )
    }
}

@Composable
fun Cards(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        PenaltyCard(modifier.weight(1f), Color.Yellow)
        Spacer(modifier = Modifier.width(3.dp))
        PenaltyCard(modifier.weight(1f), Color.Yellow)
        Spacer(modifier = Modifier.width(3.dp))
        PenaltyCard(modifier.weight(1f), Color.Red)
    }
}

@Composable
fun PenaltyCard(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val strokeWidth = ((size.width + size.height) / 2) / 15
        val radius = ((size.width + size.height) / 2) / 7
        drawRoundRect(
            color = color,
            size = size.copy(
                width = size.width - strokeWidth,
                height = size.height - strokeWidth
            ),
            style = Stroke(width = strokeWidth),
            cornerRadius = CornerRadius(
                x = radius,
                y = radius
            )
        )
    }
}