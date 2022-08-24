package furkan.senol.adapted_fba.Activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import furkan.senol.adapted_fba.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var register_Email: EditText
    private lateinit var register_Password: EditText
    private lateinit var register_Password2: EditText
    //private lateinit var register_Name: EditText
    private lateinit var register_Email_layout: TextInputLayout
    private lateinit var register_password1_layout: TextInputLayout
    private lateinit var register_password2_layout: TextInputLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        register_Email = findViewById(R.id.register_email_editText)
        register_Password = findViewById(R.id.register_password1_editText)
        register_Password2 = findViewById(R.id.register_password2_editText)
        //register_Name = findViewById(R.id.register_Name)
        register_Email_layout = findViewById(R.id.register_email_textInput)
        register_password1_layout = findViewById(R.id.register_password1_textInput)
        register_password2_layout = findViewById(R.id.register_password2_textInput)

        supportActionBar?.setTitle("Kayıt Ol")
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //register_Name.isVisible=false


    }

    public override fun onStart() {
        super.onStart()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun register_Click(view: View) {

        val email_Input = register_Email.text.toString()
        val password_Input = register_Password.text.toString()
        val password2_Input = register_Password2.text.toString()
        //val name_Input = register_Email.text.toString()
        val name_Input="deneme"
        register_Email_layout.error=""
        register_password1_layout.error=""
        register_password2_layout.error=""

        if (email_Input.isNullOrEmpty() || password_Input.isNullOrEmpty() || password2_Input.isNullOrEmpty() || name_Input.isNullOrEmpty()) {
            Toast.makeText(baseContext, "Bos birakmayin.", Toast.LENGTH_SHORT).show()
        } else if (email_Input.isValidEmail()) {
            register_Email_layout.setError("Geçerli bir email adresi giriniz")

        } else if (password_Input != password2_Input) {
            register_password1_layout.setError("Şifreler eşleşmemektedir")
        }
        else if(!isValidPasswordUpper(password_Input.trim()))
        {
            register_password1_layout.setError("En az bir buyuk harf olmali")
        }

        else if(!isValidPasswordLower(password_Input.trim()))
        {
            register_password1_layout.setError("En az bir kucuk harf olmali")
        }

        else if(!isValidPasswordNumber(password_Input.trim()))
        {
            register_password1_layout.setError("En az bir sayi olmali")
        }

        else if(!isValidPasswordLength(password_Input.trim()))
        {
            register_password1_layout.setError("En az 6 karakter")
        }

        else if(!isValidPasswordSpecialChar(password_Input.trim()))
        {
            register_password1_layout.setError("En az bir özel karakter olmali")
        }

        else {
            auth.createUserWithEmailAndPassword(email_Input.trim(), password_Input.trim())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Basarili bir sekilde kayit olunmustur", Toast.LENGTH_LONG).show()
                        val login_Page = android.content.Intent(applicationContext, LoginActivity::class.java)
                        startActivity(login_Page)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w("firebase_authentication", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Bu hesap daha önceden oluşturulmuş", Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
    }

    //fun isValidPassword(password: String?): Boolean {
    //val pattern: Pattern
    // val matcher: Matcher
    // val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-_])(?=\\S+$).{6,}"
    //  pattern = Pattern.compile(PASSWORD_PATTERN)
    //  matcher = pattern.matcher(password)
    //  return matcher.matches()
    //}
}