package com.example.digits.numbers.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.digits.R


class NumbersFragment : Fragment() {

    lateinit var viewModel: NumbersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val inputText = view.findViewById<EditText>(R.id.et_textfield)
        val factButton = view.findViewById<Button>(R.id.btn_get_fact)
        val randomButton = view.findViewById<Button>(R.id.btn_random_fact)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_history)

        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) {
                TODO("Not yet implemented")
            }

        })

        viewModel.observeState(this) {

        }

        viewModel.observeProgress(this) {

        }

        viewModel.observeList(this) {

        }


        viewModel.init(savedInstanceState == null)
        factButton.setOnClickListener {
//            val detailFragment = DetailsFragment.newInstance(text.toString())
//
//            requireActivity().supportFragmentManager.beginTransaction()
//                .add(R.id.container, detailFragment)
//                .addToBackStack(detailFragment.javaClass.simpleName)
//                .commit()
        }
    }
}