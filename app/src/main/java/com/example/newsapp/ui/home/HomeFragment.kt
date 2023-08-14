package com.example.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.data.model.NewData
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.adapter.NewsAdapter
import com.patient.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel:HomeViewModel by viewModels()
    private val adapter = NewsAdapter()

    override fun onStart() {
        super.onStart()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentHomeBinding.bind(view)
        Log.d("islam", "onViewCreated: send ")
        viewModel.getNewsHeadLines(1,15,"EN", "125a2fcd18074fe99dc2aeefb779b466",null)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.setHasFixedSize(false)
        setUpNewRv()
//        binding.btnSearch.setOnClickListener{
//            Log.d("islam", "onViewCreated: btnSearch ")
//            viewModel.getNewsHeadLines(1,15,"EN", "125a2fcd18074fe99dc2aeefb779b466",null)
//        }
    }

    fun setUpNewRv(){
        handleSharedFlow(viewModel.prayersFlow, onSuccess = {
            it as List<NewData>
            adapter.AddAll(it)
        })
    }

}