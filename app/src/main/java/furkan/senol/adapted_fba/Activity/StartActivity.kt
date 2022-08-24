package furkan.senol.adapted_fba.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import furkan.senol.adapted_fba.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()

        Handler().postDelayed({
            val login_page = android.content.Intent(applicationContext, LoginActivity::class.java)
            startActivity(login_page)
        },2000
        )
    }
}