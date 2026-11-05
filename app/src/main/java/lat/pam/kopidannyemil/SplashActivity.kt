package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val blokTeks = findViewById<LinearLayout>(R.id.blokTeks)
        val btnStart = findViewById<Button>(R.id.btnStart)

        // animasi muncul pelan (fade in)
        blokTeks.alpha = 0f
        btnStart.alpha = 0f
        blokTeks.animate().alpha(1f).setDuration(800).start()
        btnStart.animate().alpha(1f).setStartDelay(400).setDuration(600).start()

        btnStart.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}