package com.alexmenaya.timeme.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexmenaya.timeme.R
import com.alexmenaya.timeme.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel : MainViewModel

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState : Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState:  Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {

        super.onViewCreated(view, savedInstanceState)

    }

}