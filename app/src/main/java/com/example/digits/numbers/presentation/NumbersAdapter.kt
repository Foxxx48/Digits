package com.example.digits.numbers.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digits.R

class NumbersAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<NumbersViewHolder>(), Mapper.Unit<List<NumberUi>> {

    private val list = mutableListOf<NumberUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        return NumbersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.numbers_item, parent, false),
            clickListener
        )

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun map(source: List<NumberUi>) {
        list.clear()
        list.addAll(source)
        notifyDataSetChanged()
    }
}

class NumbersViewHolder(
    view: View,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(view) {

    private val title = itemView.findViewById<TextView>(R.id.tv_title)
    private val subTitle = itemView.findViewById<TextView>(R.id.tv_subTitle)

    fun bind(model: NumberUi) {
        model.map(title, subTitle)
        itemView.setOnClickListener {
            clickListener.click(model)
        }
    }
}


interface ClickListener {
    fun click(item: NumberUi)
}
