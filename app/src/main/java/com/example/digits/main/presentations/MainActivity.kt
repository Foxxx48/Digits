package com.example.digits.main.presentations

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.digits.R
import com.example.digits.main.sl.ProvideViewModel
import com.example.digits.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity(), ProvideViewModel {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fx39", "MainActivity onCreate")
        setContentView(R.layout.activity_main)

        val viewModel = provideViewModel(MainViewModel::class.java, this)

        viewModel.observe(this) { strategy ->
            Log.d("fx39", "MainActivity Observed called")
            strategy.navigate(supportFragmentManager, R.id.container)
        }

        viewModel.init(savedInstanceState == null)
    }

    companion object {
        private const val KEY = "Key"

        fun myLog(any: Any?) {
            Log.d(KEY, "$any")
        }
    }

    override fun <T : ViewModel> provideViewModel(clast: Class<T>, owner: ViewModelStoreOwner): T =
        (application as ProvideViewModel).provideViewModel(clast, owner)


}
