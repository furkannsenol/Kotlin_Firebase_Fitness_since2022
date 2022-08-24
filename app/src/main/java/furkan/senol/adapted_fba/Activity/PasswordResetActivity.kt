package furkan.senol.adapted_fba.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import furkan.senol.adapted_fba.R
import furkan.senol.adapted_fba.isValidEmail

class PasswordResetActivity : AppCompatActivity() {
    private lateinit var password_reset_Email: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var passwordresetinput: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        auth = Firebase.auth
        password_reset_Email=findViewById(R.id.password_reset_email)
        passwordresetinput=findViewById(R.id.password_reset_textinput)

        supportActionBar?.setTitle("Şifre sıfırlama")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun password_reset_passreset_Click(view: View) {
        auth.setLanguageCode("tr")
        //auth.useAppLanguage()
        var  emailAdress= password_reset_Email.text.toString().trim()
        passwordresetinput.error=""
        if(emailAdress.isNullOrEmpty()){
            passwordresetinput.error="*zorunlu alan"
        }

        else if (!emailAdress.isValidEmail()) {
            Firebase.auth.sendPasswordResetEmail(emailAdress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)  {
                        Toast.makeText(baseContext, "Şifre sıfırlama isteği epostanıza gönderilmiştir", Toast.LENGTH_LONG).show()
                    }
                    else
                        Toast.makeText(baseContext, "Email bilginizi kontrol ediniz", Toast.LENGTH_SHORT).show()
                }
        }
        else
            passwordresetinput.error="geçerli bir email adresi giriniz"
    }
}