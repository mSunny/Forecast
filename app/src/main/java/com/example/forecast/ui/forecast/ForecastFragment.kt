package com.example.forecast.ui.forecast

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecast.App
import com.example.forecast.R
import com.example.forecast.data.db.Location
import com.example.forecast.data.network.BaseWeatherInfo
import com.example.forecast.databinding.AddLocationFragmentBinding
import com.example.forecast.databinding.ForecastFragmentBinding
import com.example.forecast.databinding.SelectLocationFragmentBinding
import com.example.forecast.ui.add_location.AddLocationViewModel
import com.example.forecast.ui.add_location.SuggestedLocationsAdapter
import com.example.forecast.ui.injectViewModel
import com.example.forecast.ui.main.MainViewModel
import com.google.android.gms.location.LocationServices
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class ForecastFragment : Fragment(), LifecycleObserver {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ForecastViewModel
    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: ForecastFragmentBinding? = null
    private val binding get() = _binding!!
    var compositeDisposable: CompositeDisposable? = null

    companion object {
        fun newInstance() = ForecastFragment()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated(){
        lifecycle.removeObserver(this)
        (requireContext().applicationContext as App).diManager.appComponent.inject(this)
        viewModel = this.injectViewModel(viewModelFactory)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ForecastFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        binding.buttonAddNewLocation.setOnClickListener(::onAddClick)
        binding.buttonSelectNewLocation.setOnClickListener(::onSelectClick)
        val location = mainViewModel.selectedLocation
        location?.let {
            viewModel.getForecast(location)
            val disposable = viewModel.forecastSubject.subscribe {
                updateWeatherInfo(location, it)
            }
            compositeDisposable?.add(disposable)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable?.clear()
    }

    fun updateWeatherInfo(location: Location, baseWeatherInfo: BaseWeatherInfo) {
        binding.textViewTitle.text = getString(R.string.forecast_title, location.name)
        binding.textViewTemperature.text = getString(R.string.temperature, baseWeatherInfo.temperature)
        binding.textViewHumidity.text = getString(R.string.humidity,baseWeatherInfo.humidity)
        binding.textViewPressure.text = getString(R.string.pressure, baseWeatherInfo.pressure)
    }

    fun onAddClick(v: View) {
        mainViewModel.switchFragment(MainViewModel.CurrentFragmentShown.ADD_LOCATION)
    }

    fun onSelectClick(v: View) {
        mainViewModel.switchFragment(MainViewModel.CurrentFragmentShown.SELECT_LOCATION)
    }

}