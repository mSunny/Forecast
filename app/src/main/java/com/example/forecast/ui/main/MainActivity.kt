package com.example.forecast.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.forecast.App
import com.example.forecast.R
import com.example.forecast.ui.add_location.AddLocationFragment
import com.example.forecast.ui.forecast.ForecastFragment
import com.example.forecast.ui.injectViewModel
import com.example.forecast.ui.select_location.SelectLocationFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).diManager.appComponent.inject(this)
        mainViewModel= this.injectViewModel(viewModelFactory)
        setContentView(R.layout.main_activity)

        compositeDisposable = CompositeDisposable()
        val disposable = mainViewModel.currentFragmentSubject.subscribe{ currentFragment ->
            val fragment = when (currentFragment) {
                MainViewModel.CurrentFragmentShown.FORECAST -> ForecastFragment.newInstance()
                MainViewModel.CurrentFragmentShown.ADD_LOCATION -> AddLocationFragment.newInstance()
                MainViewModel.CurrentFragmentShown.SELECT_LOCATION -> SelectLocationFragment.newInstance()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}