package com.uratxe.movetilt

import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.loading_layout.*



fun MotionLayout.loop(enable : Boolean){
    if (enable){
        setTransitionListener(object : MotionLayout.TransitionListener{

            var isStart = true

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                isStart = isStart.not()
                if (isStart) {
                    transitionToEnd()
                } else {
                    transitionToStart()
                }
            }
        })
        transitionToEnd()
    }else{
        setTransitionListener(null)
    }

}
