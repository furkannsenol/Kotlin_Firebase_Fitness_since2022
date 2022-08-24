package furkan.senol.adapted_fba.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.ktx.storage
import furkan.senol.adapted_fba.R
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream


class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var profile_Email: EditText
    private lateinit var profile_Name: EditText
    private lateinit var alertdialog_builder: AlertDialog.Builder
    private lateinit var profile_email_verif: TextView
    private lateinit var profile_email_verif_button: Button
    private lateinit var storage: FirebaseStorage
    private lateinit var profile_Image: ImageView

    private lateinit var profile_Email_layout: TextInputLayout
    private lateinit var profile_Name_layout: TextInputLayout


    var name: String? = null
    var email: String? = null
    var photoUrl: Uri? = null
    var email_verif: String? = null

    companion object {
        const val REQUEST_CAMERA = 100
    }

    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        //throw RuntimeException("Crash Deneme")

        auth = Firebase.auth
        storage = Firebase.storage

        profile_Email = findViewById(R.id.profile_email_editText)
        profile_Name = findViewById(R.id.profile_name_editText)
        profile_email_verif = findViewById(R.id.profile_EmailVerification)
        profile_email_verif_button = findViewById(R.id.profile_emailVerif_Button)
        profile_Image = findViewById(R.id.profile_Image)

        supportActionBar?.setTitle("Profil")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profile_Email.isEnabled = false

        //downloadFile()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val user = Firebase.auth.currentUser!!
            user.let {
                name = user.displayName
                email = user.email
                photoUrl = user.photoUrl
                email_verif = user.isEmailVerified.toString()
                //email = photoUrl.toString()

                profile_Name.setText(name)
                profile_Email.setText(email)
                //profile_Image.setImageURI(photoUrl)
                //profile_Image.setImageBitmap(photoUrl)

            }
        }

        if (email_verif == "false") {
            profile_email_verif_button.isVisible = true
            profile_email_verif.text = "Email adresinizi doğrulayın"
        } else if (email_verif == "true") {
            profile_email_verif.text = "Email doğrulandı"
            profile_email_verif_button.isVisible = false
        }
    }

        //val ref = Firebase.storage.reference

   /* fun downloadFile() = CoroutineScope(Dispatchers.IO).launch {
//        val storage = FirebaseStorage.getInstance()
        val items: Task<ListResult> = ref.child("image/").listAll()
        val itemUrls = arrayListOf<Uri>()


//            itemUrls.add(item.downloadUrl.result)
//        }

        var url: Uri? = null

        url = items.result.items[0].downloadUrl.result


        Glide.with(this@ProfileActivity)
            .load("${items.result.items[0].downloadUrl}")
            .into(profile_Image)

//        Log.d("url", "$storageRef")
    }*/

    fun update_Click(view: View) {
        val profileUpdates = userProfileChangeRequest {
            displayName = profile_Name.text.toString()
//            photoUri = Uri.parse("https://flyclipart.com/thumbs/cool-guy-smaller-than-128x128-pixels-1138924.png")
            photoUri =
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/mypc-kotlin-fba.appspot.com/o/image%2FwEr4KiD8GlXyrluP1gy8LCqQucf2?alt=media&token=7f83b986-ae50-45c9-b6a6-eca82d456912")
//            downloadFile(photoUri.toString())
            profile_Image.setImageURI(photoUri)


        }
        val user = Firebase.auth.currentUser
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Güncelleme başarılı bir şekilde gerçekleşmiştir",
                        Toast.LENGTH_SHORT
                    ).show()
                    val profile_page =
                        android.content.Intent(applicationContext, ProfileActivity::class.java)
                    startActivity(profile_page)
                    finish()
                }
            }
    }

    fun delete_account_Click(view: View) {
        var user = auth.currentUser!!

        alertdialog_builder = AlertDialog.Builder(this)
        alertdialog_builder.setTitle("Hesap Silme")
            .setMessage("Silmek istediğinize emin misiniz?")
            .setCancelable(true)
            .setPositiveButton("Evet") { dialogInterface, it ->
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("user_account_deleted", "User account deleted.")
                            Toast.makeText(baseContext, "Hesap başarılı bir şekilde kaldırılmıştır", Toast.LENGTH_SHORT).show()
                            val login_page = android.content.Intent(applicationContext, LoginActivity::class.java)
                            startActivity(login_page)
                            finish()
                        }
                    }
            }
            .setNegativeButton("Hayır") { dialogInterface, it ->
                dialogInterface.cancel()
            }.show()
    }

    fun password_update_Click(view: View) {
        val password_update_page =
            android.content.Intent(applicationContext, PasswordUpdateActivity::class.java)
        startActivity(password_update_page)
    }

    fun email_send_Click(view: View) {
            val user = auth.currentUser
           // auth.setLanguageCode("tr")
            auth.useAppLanguage()
            user!!.sendEmailVerification().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Log.d(TAG, "Email sent.")
                        Toast.makeText(baseContext, "Doğrulama isteği eposta adresinize gönderilmiştir", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(baseContext, "hata", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    fun profile_image_Click(view: View) {
        //intentCamera()
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    /*private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("image/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        ref.putBytes(image).addOnCompleteListener {
            if (it.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener { Task ->
                    Task.result?.let {
                        profile_Image.setImageBitmap(imgBitmap)
                    }
                }
            }
        }
    }

}
