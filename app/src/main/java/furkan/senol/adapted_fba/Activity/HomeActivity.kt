package furkan.senol.adapted_fba.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import furkan.senol.adapted_fba.Adapter.FitnessAdapter
import furkan.senol.adapted_fba.Model.FitnessModel
import furkan.senol.adapted_fba.R
import furkan.senol.adapted_fba.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), FitnessAdapter.SelectedFitnessListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding

    var fitnessName: String?=null
    //menu tanımlama
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menu_Iflater = menuInflater
        menuInflater.inflate(R.menu.toolbar_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.toolbar_profile)
        {
            val currentUser = auth.currentUser
            if(currentUser != null) {
                val profile_page = android.content.Intent(applicationContext, ProfileActivity::class.java)
                startActivity(profile_page)

            }
        }
        if(item.itemId== R.id.toolbar_logout) {
            Firebase.auth.signOut()
            FirebaseAuth.getInstance().signOut()
            val currentUser = auth.currentUser

                if(currentUser == null) {
                    val login_page = android.content.Intent(applicationContext, LoginActivity::class.java)
                    startActivity(login_page)
                    finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth
        //throw RuntimeException("Crash Deneme")
        supportActionBar?.setTitle("Ana Sayfa")

        if (FirebaseAuth.getInstance().currentUser == null) {
            val login_page= Intent(this, LoginActivity::class.java)
            startActivity(login_page)
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fitnessList = arrayListOf<FitnessModel>(
            FitnessModel(R.drawable.muscles,"Ön Kol"),
            FitnessModel(R.drawable.ic_baseline_person_24,"Arka Kol"),
            FitnessModel(R.drawable.ic_baseline_person_24,"Göğüs"),
            FitnessModel(R.drawable.ic_baseline_person_24,"Bacak"),
            FitnessModel(R.drawable.ic_baseline_person_24,"Sırt"),
            FitnessModel(R.drawable.ic_baseline_person_24,"Kanat"),

        )

        val fitnessAdapter = FitnessAdapter(fitnessList,this)
        binding.fitnessRecyclerView.layoutManager= LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        //binding.fitnessRecyclerView.layoutManager= GridLayoutManager(applicationContext,2,GridLayoutManager.VERTICAL,false)
        binding.fitnessRecyclerView.adapter=fitnessAdapter
        binding.fitnessRecyclerView.setHasFixedSize(true)
    }

    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    fun Logout_Click(view: View) {

        Firebase.auth.signOut()
        FirebaseAuth.getInstance().signOut();
        val currentUser = auth.currentUser
        if(currentUser == null) {
            val login_page = android.content.Intent(applicationContext, LoginActivity::class.java)
            startActivity(login_page)
            finish()
        }
    }

    fun Profile_Click(view: View) {
        val currentUser = auth.currentUser
        if(currentUser != null) {
            val profile_page = android.content.Intent(applicationContext, ProfileActivity::class.java)
            startActivity(profile_page)
        }
    }

    override fun selectedFitness(fitness: FitnessModel) {

        fitnessName =  fitness.fitnessName
        Toast.makeText(baseContext, fitnessName, Toast.LENGTH_SHORT).show()
        val profile_page = android.content.Intent(applicationContext, HomeLowerActivity::class.java)
        profile_page.putExtra("header",fitnessName)
        startActivity(profile_page)
    }
}