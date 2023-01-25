package com.example.digits.numbers.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.example.digits.R
import com.example.digits.details.presentation.DetailsFragment
import com.example.digits.main.presentations.MainActivity


class NumbersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE

        val text = view.findViewById<EditText>(R.id.et_textfield).text

        view.findViewById<Button>(R.id.btn_get_fact).setOnClickListener {
            val detailFragment = DetailsFragment.newInstance(text.toString())

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, detailFragment)
                .addToBackStack(detailFragment.javaClass.simpleName)
                .commit()
        }
    }
}