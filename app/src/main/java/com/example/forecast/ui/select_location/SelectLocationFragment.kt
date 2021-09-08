package com.example.forecast.ui.select_location

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.forecast.R

class SelectLocationFragment : Fragment() {

    companion object {
        fun newInstance() = SelectLocationFragment()
    }

    private lateinit var viewModel: SelectLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectLocationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}