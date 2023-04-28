package com.example.digits.main.presentations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.digits.R
import com.example.digits.main.sl.ProvideViewModel

class MainActivity : AppCompatActivity(), ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = provideViewModel(MainViewModel::class.java, this)

        viewModel.observe(this) { strategy ->
            strategy.navigate(supportFragmentManager, R.id.container)
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun <T : ViewModel> provideViewModel(clast: Class<T>, owner: ViewModelStoreOwner): T =
        (application as ProvideViewModel).provideViewModel(clast, owner)


}
