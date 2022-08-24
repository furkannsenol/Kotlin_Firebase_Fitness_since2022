package furkan.senol.adapted_fba.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//import android.content.ContentValues.TAG
//import android.os.PatternMatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
//import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import furkan.senol.adapted_fba.R

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    // Initialize Firebase Auth
    private lateinit var login_Email: EditText
    private lateinit var login_Password: EditText
    private lateinit var login_Email_layout: TextInputLayout
    private lateinit var login_Password_LAYOUT: TextInputLayout
    // private lateinit var login_Remember: EditText



    override fun onCreate(savedInstanceState: Bundle?) { //load ekrani
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        login_Email = findViewById(R.id.login_email_editText)
        login_Password = findViewById(R.id.login_password1_editText)
        //login_Remember= findViewById(R.id.login_remember_me)
        login_Email_layout=findViewById(R.id.login_email_textInput)
        login_Password_LAYOUT=findViewById(R.id.login_password1_textInput)


        val currentUser = auth.currentUser
        if(currentUser != null ){
            val home_page = android.content.Intent(applicationContext, HomeActivity::class.java)
            startActivity(home_page)
            finish()
        }

        supportActionBar?.setTitle("Giriş Yap")
    }


    //override fun onStart() {
       // super.onStart()
        //val currentUser = auth.currentUser
        //if(currentUser != null){
        //  reload();
        //val home_page = android.content.Intent(applicationContext, HomeActivity::class.java)
        //startActivity(home_page)
        //}
   // }


    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    fun login_Click(view: View) {
        val email_Input = login_Email.text.toString()
        val password_Input = login_Password.text.toString()
        login_Email_layout.error=""
        login_Password_LAYOUT.error=""

        if (email_Input.isNullOrEmpty() || password_Input.isNullOrEmpty()) {
            Toast.makeText(baseContext, "Lutfen alanlari doldurunuz", Toast.LENGTH_SHORT).show()
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email_Input).matches()) {
            //Toast.makeText(baseContext, "Email hatali", Toast.LENGTH_SHORT).show()
            login_Email_layout.setError("Email hatalı")
        }

        else {
            auth.signInWithEmailAndPassword(email_Input.trim(), password_Input.trim())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("firebase_authentication", "Log for signInWithEmail:success")
                        val user = auth.currentUser

//                      updateUI(user)
                        Log.d("firebase_authentication", "${task.result.toString()}")
                        Toast.makeText(baseContext, "Başarılı bir şekilde giriş yaptınız", Toast.LENGTH_SHORT).show()

                        val home_page = android.content.Intent(applicationContext, HomeActivity::class.java)
                        startActivity(home_page)
                        //finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("firebase_authentication", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Kullanıcı adı veya parola hatalı", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                    }
                }
        }
    }

    fun login_to_register_Click(view: View) {
        val register_page = android.content.Intent(applicationContext, RegisterActivity::class.java)
        startActivity(register_page)
    }

    fun login_password_reset_Click(view: View) {
        val password_reset_page = android.content.Intent(applicationContext, PasswordResetActivity::class.java)
        startActivity(password_reset_page)

    }



}