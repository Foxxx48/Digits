package com.example.digits.details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.digits.R


class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments().getString(TEXT)
        view.findViewById<TextView>(R.id.tv_Hello).text = args

    }

    companion object {
        private const val TEXT = "text"

        fun newInstance(value: String): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(TEXT, value)
                }
            }
        }
    }
}