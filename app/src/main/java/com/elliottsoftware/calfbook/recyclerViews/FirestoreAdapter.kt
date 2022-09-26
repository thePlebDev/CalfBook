package com.elliottsoftware.calfbook.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.models.Calf
import com.elliottsoftware.calfbook.models.firebase.FireBaseCalf
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat

class FirestoreAdapter(options: FirestoreRecyclerOptions<FireBaseCalf>) : FirestoreRecyclerAdapter<FireBaseCalf, FirestoreViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirestoreViewHolder {
        //creates the individual items
        return FirestoreViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FirestoreViewHolder, position: Int, model: FireBaseCalf) {
        val current = getItem(position)
        // we will pass on the values to bind
        holder.bind(model)
    }
}


//--------------------------------VIEW HOLDER CLASS----------------------------
class FirestoreViewHolder(calfView: View):RecyclerView.ViewHolder(calfView){

    private val tagNumber: TextView = calfView.findViewById(R.id.text_view_title)
    private val dateView: TextView = calfView.findViewById(R.id.text_view_date);
    private val details: TextView = calfView.findViewById(R.id.text_view_description)
    private val sex: TextView = calfView.findViewById(R.id.text_view_sex)
    private var calfId:Long = 0

    fun bind(calf: FireBaseCalf){
        //set the references to values here
        val pattern = "MM-dd-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        //calfId = calf.id
        tagNumber.text = calf.tagNumber
        dateView.text = simpleDateFormat.format(calf.date)
        details.text = calf.details
        sex.text = calf.sex;

    }

    companion object{
        fun create(parent: ViewGroup):FirestoreViewHolder{
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.indiv_calf_view,parent,false)
            return FirestoreViewHolder(view)
        }
    }

}