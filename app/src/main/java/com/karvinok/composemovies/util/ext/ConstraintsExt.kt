package com.karvinok.composemovies.util.ext

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

interface ConstraintInstructions
data class ConnectConstraint(val startID: Int, val startSide: Int, val endID: Int, val endSide: Int) :
    ConstraintInstructions
data class DisconnectConstraint(val startID: Int, val startSide: Int) : ConstraintInstructions

fun ConstraintLayout.updateConstraints(vararg instructions: ConstraintInstructions) {
    ConstraintSet().also {
        it.clone(this)
        for (instruction in instructions) {
            if (instruction is ConnectConstraint) it.connect(instruction.startID, instruction.startSide, instruction.endID, instruction.endSide)
            if (instruction is DisconnectConstraint) it.clear(instruction.startID, instruction.startSide)
        }
        it.applyTo(this)
    }
}


fun View.marginStart(margin: Int = 0) {
    constraintMargin(ConstraintLayout.LayoutParams.START, margin)
}

fun View.marginTop(margin: Int = 0) {
   constraintMargin(ConstraintLayout.LayoutParams.TOP, margin)
}

fun View.marginEnd(margin: Int = 0) {
    constraintMargin(ConstraintLayout.LayoutParams.END, margin)
}

fun View.marginBottom(margin: Int = 0) {
    constraintMargin(ConstraintLayout.LayoutParams.BOTTOM, margin)
}

private fun View.constraintMargin(anchor: Int, px: Int){
    try {
        (parent as? ConstraintLayout?)?.apply {
            ConstraintSet().also {
                it.clone(this)
                it.setMargin(this@constraintMargin.id, anchor, px)
                it.applyTo(this)
            }
        }?: timber("[constraintMargin()] view required to be constrainLayout child")
    } catch (e: Exception) {
        timber(e, true)
    }
}