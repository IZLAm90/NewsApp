package com.example.newsapp.ui.home

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.data.model.NewData
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.ui.detailes.DetailsFragment
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

        getAllData()
        setUpNewRv()
        binding.btnSearch.setOnClickListener{
            viewModel.getNewsHeadLines(1,15,"EN", com.app.data.remote.Constants.PrefKeys.APP_KEY ,null)
        }
    }
    fun getAllData(){
        viewModel.getNewsHeadLines(1,15,"EN", com.app.data.remote.Constants.PrefKeys.APP_KEY ,null)
    }

    fun setUpNewRv(){
        binding.recyclerView.adapter=adapter
        binding.recyclerView.setHasFixedSize(false)
        handleSharedFlow(viewModel.prayersFlow, onSuccess = {
            it as List<NewData>
            adapter.AddAll(it)
        })
        adapter.onItemClicked={data->
            val  bundle =Bundle()
            bundle.putString("image",data.urlToImage)
            bundle.putString("title",data.title)
            bundle.putString("date",data.publishedAt )
            bundle.putString("webView",data.url)
            bundle.putString("Source",data.author)
            bundle.putString("Description",data.description)
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,bundle)
        }
    }

}