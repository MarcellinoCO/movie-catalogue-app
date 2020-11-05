package co.marcellino.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.ui.catalogue.CatalogueActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenDuration = 3000L
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        timer = Timer()
        timer.schedule(splashScreenDuration) {
            val intent = Intent(this@SplashScreenActivity, CatalogueActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}