package furkan.senol.adapted_fba.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import furkan.senol.adapted_fba.Adapter.FitnessAdapter
import furkan.senol.adapted_fba.Model.FitnessModel
import furkan.senol.adapted_fba.R
import furkan.senol.adapted_fba.databinding.ActivityHomeLowerBinding

class HomeLowerActivity : AppCompatActivity(),FitnessAdapter.SelectedFitnessListener {
    private lateinit var binding: ActivityHomeLowerBinding
    private lateinit var get_header: String
    var fitnessList: ArrayList<FitnessModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeLowerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        get_header = intent.getStringExtra("header").toString()
        supportActionBar?.setTitle(get_header + " Hareketleri")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (get_header == "Ön Kol") {
             fitnessList = arrayListOf<FitnessModel>(
                FitnessModel(R.drawable.muscles, "Reverse Barbell Curl"),
                FitnessModel(R.drawable.ic_baseline_person_24, "Cable Biceps Curl"),
                FitnessModel(R.drawable.ic_baseline_person_24, "Hammer Curl"),
            )

        }
        if (get_header == "Arka Kol") {
            fitnessList = arrayListOf<FitnessModel>(
                FitnessModel(R.drawable.muscles, "Lying Triceps Extension"),
                FitnessModel(R.drawable.ic_baseline_person_24, "Close-Grip Bench Press"),
                FitnessModel(R.drawable.ic_baseline_person_24, "Overhead Dumbbell Extension "),
                FitnessModel(R.drawable.ic_baseline_person_24, "Cable Pressdown \t"),
            )

        }

            var fitnessAdapter = FitnessAdapter(fitnessList, this)
            binding.fitnessLowerRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false
            )
            binding.fitnessLowerRecyclerView.adapter = fitnessAdapter
            binding.fitnessLowerRecyclerView.setHasFixedSize(true)
        }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun selectedFitness(fitness: FitnessModel) {

    }
}