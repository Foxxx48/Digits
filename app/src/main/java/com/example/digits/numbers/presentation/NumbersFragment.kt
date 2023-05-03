package com.example.digits.numbers.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.digits.R
import com.example.digits.main.presentations.BaseFragment


class NumbersFragment() : BaseFragment<NumbersViewModel.Base>() {

    override val viewModelClass = NumbersViewModel.Base::class.java
    override val layoutId: Int = R.layout.fragment_numbers
    private lateinit var inputText: BaseCustomTextInputEditText

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<View>(R.id.progressBar)
        inputText = view.findViewById(R.id.et_textfield)
        val textInputLayout = view.findViewById<BaseCustomTextInputLayout>(R.id.et_layout)
        val factButton = view.findViewById<Button>(R.id.btn_get_fact)
        val randomButton = view.findViewById<Button>(R.id.btn_random_fact)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_history)

        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) {
                viewModel.showDetails(item)
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