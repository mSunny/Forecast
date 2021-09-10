package com.example.forecast.ui.select_location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecast.App
import com.example.forecast.data.db.Location
import com.example.forecast.databinding.SelectLocationFragmentBinding
import com.example.forecast.ui.injectViewModel
import com.example.forecast.ui.main.MainViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class SelectLocationFragment : Fragment(), LifecycleObserver {

    companion object {
        fun newInstance() = SelectLocationFragment()
    }

    private var adapter = LocationsAdapter(::onItemClick, ::onRemoveItemClick)
    private var _binding: SelectLocationFragmentBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SelectLocationViewModel
    private val mainViewModel: MainViewModel by activityViewModels()
    var compositeDisposable: CompositeDisposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SelectLocationFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        binding.locationRecyclerView.adapter = adapter
        binding.locationRecyclerView.layoutManager = LinearLayoutManager(view.context)
        viewModel.requestLocations()
        val disposable = viewModel.locationsSubject.subscribe{ locations ->
            adapter.items = locations
            adapter.notifyDataSetChanged()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable?.clear()
    }

    fun onItemClick(location: Location) {
        mainViewModel.locationSelected(location)
    }

    fun onRemoveItemClick(itemId: Long) {
        viewModel.removeLocation(itemId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}