package client.yalantis.com.foldingtabbar

import android.view.animation.Interpolator

/**
 * Created by andrewkhristyan on 11/15/16.
 */
internal class CustomBounceInterpolator(amplitude: Double, frequency: Double) : Interpolator {
    var mAmplitude = 1.0
    var mFrequency = 10.0

    init {
        mAmplitude = amplitude
        mFrequency = frequency
    }

    override fun getInterpolation(time: Float): Float {
        return (-1.0 * Math.pow(Math.E, -time / mAmplitude) *
                Math.cos(mFrequency * time) + 1).toFloat()
    }
}