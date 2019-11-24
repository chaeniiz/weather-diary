package com.chaeniiz.weatherdiary.presentation.splash

import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.home.HomeActivity
import kotlinx.android.synthetic.main.layout_spalsh.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_spalsh)

        startAnimation()
    }

    private fun startAnimation() {
        splashImageView.startAnimation(
            AlphaAnimation(0f, 1f).apply {
                this.duration = 1200
                repeatCount = Animation.REVERSE
                repeatMode = Animation.REVERSE
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationEnd(p0: Animation?) {
                        HomeActivity.start(this@SplashActivity)
                        finish()
                    }
                    override fun onAnimationRepeat(p0: Animation?) = Unit
                    override fun onAnimationStart(p0: Animation?) = Unit
                })
            }
        )
    }
}
