package com.arifwidayana.musiclens.presentation

import androidx.lifecycle.ViewModel
import com.arifwidayana.musiclens.R
import com.arifwidayana.musiclens.arch.base.BaseActivity
import com.arifwidayana.musiclens.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity: BaseActivity<ActivityMainBinding, ViewModel>(
    ActivityMainBinding::inflate
) {
    override val viewModel: ViewModel by inject()

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun observeData() {
        TODO("Not yet implemented")
    }
}