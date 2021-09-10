package com.example.forecast.ui.add_location

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecast.App
import com.example.forecast.R
import com.example.forecast.data.db.Location
import com.example.forecast.databinding.AddLocationFragmentBinding
import com.example.forecast.ui.injectViewModel
import com.example.forecast.ui.main.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class AddLocationFragment : Fragment(), LifecycleObserver {

    companion object {
        fun newInstance() = AddLocationFragment()
        val TAG: String = AddLocationFragment::class.java.simpleName
        var PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AddLocationViewModel
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: AddLocationFragmentBinding? = null
    private val binding get() = _binding!!
    var compositeDisposable: CompositeDisposable? = null

    private var suggestedLocationsAdapter: SuggestedLocationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddLocationFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        var disposable = viewModel.locationSubject.subscribe{
            showCurrentLocation(it)
        }
        compositeDisposable?.add(disposable)
        disposable = viewModel.possibleLocationsSubject.subscribe{
            updateSearchView(it)
        }
        compositeDisposable?.add(disposable)

        disposable = viewModel.addedLocationSubject.subscribe{
            mainViewModel.locationAdded(it)
        }
        compositeDisposable?.add(disposable)

        binding.buttonAdd.setOnClickListener(::onAddClick)
        suggestedLocationsAdapter = SuggestedLocationsAdapter(::onItemClick)
        binding.recyclerViewSuggestions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSuggestions.adapter = suggestedLocationsAdapter
        binding.editTextTextFindLocation.doOnTextChanged { text, start, before, count ->
            if (count > 0) {
                viewModel.getPossibleLocations(text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable?.clear()
    }
    override fun onResume() {
        super.onResume()
        //TODO:
        checkForPermission()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated(){
        lifecycle.removeObserver(this)
        (requireContext().applicationContext as App).diManager.appComponent.inject(this)
        viewModel = this.injectViewModel(viewModelFactory)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                requestCurrentLocation()
            }
        }

    private fun checkForPermission() {
        activity?.let {
            if (hasPermissions(activity as Context, PERMISSIONS)) {
                requestCurrentLocation()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanationDialog()
        } else {
                permReqLauncher.launch(
                    PERMISSIONS
                )
            }
        }
    }

    // util method
    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showExplanationDialog() {
        AlertDialog.Builder(context)
            .setTitle(R.string.location_permission_needed)
            .setMessage(R.string.location_permission_message)
            .setPositiveButton(
                R.string.ok
            ) { _, _ ->
                permReqLauncher.launch(
                    PERMISSIONS
                )
            }
            .setNegativeButton(R.string.cancel) {_, _ ->}
            .create()
            .show()
    }

    fun requestCurrentLocation() {
        viewModel.getCurrentLocation(fusedLocationClient)
    }

    fun showCurrentLocation(location: Location) {
        binding.textViewCurrentLocation.text = getString(R.string.lat_long, location.latitude.toString(), location.longitude.toString())
    }

    fun updateSearchView(possibleLocations: List<Location>){
        suggestedLocationsAdapter?.items = possibleLocations
        suggestedLocationsAdapter?.notifyDataSetChanged()
    }

    fun onItemClick(item: Location) {
        Log.d("LOCATION00", "onItemClick")
        viewModel.addLocationToSelected(item)
    }

    fun onAddClick(v: View) {
        viewModel.locationSubject.value?.let { viewModel.addLocationToSelected(it)}
    }
}