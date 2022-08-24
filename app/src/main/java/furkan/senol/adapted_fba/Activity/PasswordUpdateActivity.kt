package furkan.senol.adapted_fba.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.ktx.Firebase
import furkan.senol.adapted_fba.*

class PasswordUpdateActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var password1: EditText
    private lateinit var password2: EditText
    private lateinit var passwordold: EditText
    private lateinit var auth2: AuthCredential
    private lateinit var passwordold_layout: TextInputLayout
    private lateinit var password1_layout: TextInputLayout
    private lateinit var password2_layout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_update)

        auth = Firebase.auth
        password1 = findViewById(R.id.password_update_Pass1_editText)
        password2 = findViewById(R.id.password_update_Pass2_editText)
        passwordold=findViewById(R.id.password_update_old_editText)

        passwordold_layout=findViewById(R.id.password_update_old_textinput)
        password1_layout=findViewById(R.id.password_update_Pass1_textInput)
        password2_layout=findViewById(R.id.password_update_Pass2_textInput)

        supportActionBar?.setTitle("Şifre Güncelle")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun passupdate_Click(view: View) {
        val user = auth.currentUser!!
        var password_Edit = password1.text.toString()
        var password2_Edit = password2.text.toString()
        var passwordold_Edit = passwordold.text.toString()

        password2_layout.error=""
        password1_layout.error=""
        passwordold_layout.error=""

        if (password_Edit.isNullOrEmpty() || password2_Edit.isNullOrEmpty() || passwordold_Edit.isNullOrEmpty()) {
            Toast.makeText(baseContext, "Lütfen Boş Bırakmayın", Toast.LENGTH_SHORT).show()
        }
        else if (!isValidPasswordLength(password_Edit.trim())) {
            // password1.setError("")
            password1_layout.error= "En az 6 karakter olmalidir"
        }else if (password_Edit != password2_Edit) {
            //Toast.makeText(baseContext, "Şifreler uyuşmuyor", Toast.LENGTH_SHORT).show()
            password1_layout.error= "Şifreler uyuşmuyor"
            password2_layout.error= "Şifreler uyuşmuyor"
        } else if (!isValidPasswordUpper(password_Edit.trim())) {
           // password1.setError("")
            password1_layout.error= "En az bir buyuk harf olmalıdır"
        } else if (!isValidPasswordLower(password_Edit.trim())) {
           // password1.setError("")
            password1_layout.error= "En az bir kucuk harf olmali"
        } else if (!isValidPasswordNumber(password_Edit.trim())) {
           // password1.setError("")
            password1_layout.error= "En az bir sayi olmali"
        }  else if (!isValidPasswordSpecialChar(password_Edit.trim())) {
            //password1.setError("")
            password1_layout.error= "En az bir ozel karakter olmali"
        }
        else {

           // var password_control = EmailAuthProvider.getCredential(user.email.toString(),passwordold.toString())
           // user.reauthenticate(password_control)?.addOnCompleteListener {
             //   if(it.isSuccessful){
                //    Toast.makeText(baseContext, "Sifre dogru", Toast.LENGTH_SHORT).show()


                    user!!.updatePassword(password_Edit)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(baseContext, "Güncelleme başarılı bir şekilde gerçekleşmiştir", Toast.LENGTH_SHORT).show()
                                val profile_page = android.content.Intent(applicationContext, ProfileActivity::class.java)
                                startActivity(profile_page)
                                finish()
                            } else {
                                Toast.makeText(baseContext, "Güncelleme işlemi başarısız", Toast.LENGTH_SHORT).show()
                            }
                        }


                //}
                   // else{
                   // Toast.makeText(baseContext, "sifre yanlis ", Toast.LENGTH_SHORT).show()
                //}
            //}

        }
    }
}