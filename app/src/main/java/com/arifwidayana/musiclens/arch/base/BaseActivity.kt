package com.arifwidayana.musiclens.arch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.arifwidayana.musiclens.arch.utils.ext.getErrorMessage
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import java.lang.Exception
import javax.inject.Inject

/**
 * BaseActivity created for clean, simplify, and reusable code in every activity
 */
abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    private val bindingFactory: (LayoutInflater) -> VB,
    private val viewModelClass: Class<VM>
) : BaseContract, DaggerAppCompatActivity() {
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory.invoke(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
        setContentView(binding.root)
        initView()
        observeData()
    }

    abstract fun initView()
    abstract fun observeData()
    override fun showMessageToast(message: String?, exception: Exception?) {
        when {
            exception != null -> {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getErrorMessage(exception),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun showMessageSnackbar(message: String?, exception: Exception?) {
        when {
            exception != null -> {
                Snackbar.make(
                    binding.root,
                    applicationContext.getErrorMessage(exception),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else -> {
                Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}