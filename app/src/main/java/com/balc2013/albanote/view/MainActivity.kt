package com.balc2013.albanote.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.balc2013.albanote.R
import com.balc2013.albanote.databinding.ActivityMainBinding
import com.balc2013.albanote.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
        binding.vm = vm
        binding.lifecycleOwner = this

        setExceptionHandler(vm, binding)
    }

    override fun setObserve() {
    }

    override fun setView() {
    }
}