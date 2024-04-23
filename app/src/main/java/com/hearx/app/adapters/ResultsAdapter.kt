package com.hearx.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hearx.app.R
import com.hearx.app.database.ResultData

class ResultsAdapter(
    private val results: List<ResultData>
) : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtText: TextView = view.findViewById(R.id.txt_text)
        val txtDescription: TextView = view.findViewById(R.id.txt_description)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_result_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val result = results[position]
        val topText = "Difficulty: ${result.difficulty}"
        val bottomText = "Score: ${result.score}"

        viewHolder.txtText.text = topText
        viewHolder.txtDescription.text = bottomText
    }

    override fun getItemCount(): Int {
        return results.size
    }
}