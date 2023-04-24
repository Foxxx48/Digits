package com.example.digits.numbers.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
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
        val diff = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

class NumbersViewHolder(
    view: View,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(view) {

    private val title = itemView.findViewById<TextView>(R.id.tv_title)
    private val subTitle = itemView.findViewById<TextView>(R.id.tv_subTitle)
    private val mapper = ListItemUi(title, subTitle)

    fun bind(model: NumberUi) {
        model.map(mapper)
        itemView.setOnClickListener {
            clickListener.click(model)
        }
    }
}


interface ClickListener {
    fun click(item: NumberUi)
}

class DiffUtilCallback(
    private val oldList: List<NumberUi>,
    private val newList: List<NumberUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].map(newList[newItemPosition])


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].equals(newList[newItemPosition])


}
