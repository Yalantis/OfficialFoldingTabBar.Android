package client.yalantis.com.foldingtabbar

import android.view.animation.Interpolator

/**
 * Created by andrewkhristyan on 11/15/16.
 */
internal class CustomBounceInterpolator(val amplitude: Double = 0.1,
                                        val frequency: Double = 0.8) : Interpolator {

    override fun getInterpolation(time: Float): Float {
        return (-1.0 * Math.exp(-time / amplitude) *
                Math.cos(frequency * time) + 1).toFloat()
    }
}