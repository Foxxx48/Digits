package com.example.digits.numbers.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.digits.R
import com.example.digits.details.presentation.DetailsFragment
import com.example.digits.main.sl.ProvideViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class NumbersFragment : Fragment() {

    lateinit var viewModel: NumbersViewModel
    lateinit var inputText: TextInputEditText

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).provideViewModel(
            NumbersViewModel.Base::class.java,
            this
        )
    }

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
        inputText = view.findViewById<TextInputEditText>(R.id.et_textfield)
        val textInputLayout = view.findViewById<TextInputLayout>(R.id.et_layout)
        val factButton = view.findViewById<Button>(R.id.btn_get_fact)
        val randomButton = view.findViewById<Button>(R.id.btn_random_fact)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_history)
        val mapper = DetailsUi()
        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) {
                val detailFragment = DetailsFragment.newInstance(item.map(mapper))
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, detailFragment)
                    .addToBackStack(detailFragment.javaClass.simpleName)
                    .commit()
            }
        })

        recyclerView.adapter = adapter

        factButton.setOnClickListener {
            viewModel.fetchNumberFact(inputText.text.toString().trim())
        }
        randomButton.setOnClickListener {
            viewModel.fetchRandomNumberFact()
        }

        viewModel.observeState(this) {
            it.apply(textInputLayout, inputText)
        }

        viewModel.observeProgress(this) {
            progressBar.visibility = it
        }

        viewModel.observeList(this) {
            adapter.map(it)
        }


        viewModel.init(savedInstanceState == null)

    }

    override fun onPause() {
        super.onPause()
        inputText.removeTextChangedListener(watcher)
    }

    override fun onResume() {
        inputText.addTextChangedListener(watcher)
        super.onResume()
    }

    abstract class SimpleTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) = Unit

    }
}