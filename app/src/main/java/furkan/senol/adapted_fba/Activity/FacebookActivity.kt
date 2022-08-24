    package furkan.senol.adapted_fba.Activity

    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.TextView
    import android.widget.Toast
    import com.google.android.gms.auth.api.signin.GoogleSignIn
    import com.google.android.gms.auth.api.signin.GoogleSignInClient
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions
    import com.google.android.gms.common.api.ApiException
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.GoogleAuthProvider
    import furkan.senol.adapted_fba.R

    class FacebookActivity : AppCompatActivity() {
        private lateinit var signInWithGoogle_Button: TextView
        private lateinit var client: GoogleSignInClient

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_facebook)
            signInWithGoogle_Button = findViewById(R.id.signInWithGoogle)
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            client = GoogleSignIn.getClient(this, options)
            signInWithGoogle_Button.setOnClickListener {
                val signInIntent = client.signInIntent
                startActivityForResult(signInIntent, 10001)
            }


        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 10001) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val home_page = Intent(this, HomeActivity::class.java)
                            startActivity(home_page)

                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }

        override fun onStart() {
            super.onStart()
            if (FirebaseAuth.getInstance().currentUser != null) {
                val home_page = Intent(this, HomeActivity::class.java)
                startActivity(home_page)
            }
        }
    }