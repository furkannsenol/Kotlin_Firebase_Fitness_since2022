package furkan.senol.adapted_fba.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import furkan.senol.adapted_fba.Model.FitnessModel
import furkan.senol.adapted_fba.databinding.FitnessCardDesignBinding

class FitnessAdapter(private var fitnessList: ArrayList<FitnessModel>, private val selectedFitnessListener: SelectedFitnessListener) :
    RecyclerView.Adapter<FitnessAdapter.FitnessCardDesign>() {

    class FitnessCardDesign(val fitnessCardDesignBinding: FitnessCardDesignBinding) :
        RecyclerView.ViewHolder(fitnessCardDesignBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessCardDesign {
        val layoutInflater = LayoutInflater.from(parent.context)
        val fitnessCardDesignBinding =
            FitnessCardDesignBinding.inflate(layoutInflater, parent, false)
        return FitnessCardDesign(fitnessCardDesignBinding)
    }

    override fun onBindViewHolder(holder: FitnessCardDesign, position: Int) {
        // cogaltilacak satir
        val fitness = fitnessList[position]
        holder.fitnessCardDesignBinding.fitnessImageView.setImageResource(fitness.fitnessImage)
        holder.fitnessCardDesignBinding.fitnessNameText.text = fitness.fitnessName
        holder.fitnessCardDesignBinding.fitnessCardView.setOnClickListener{
            selectedFitnessListener.selectedFitness(fitness)
        }

    }

    override fun getItemCount(): Int  {
        return fitnessList.size
    }

    interface SelectedFitnessListener   {
        fun selectedFitness(fitness:FitnessModel)
    }

}