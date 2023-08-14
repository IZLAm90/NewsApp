package com.patient.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.data.remote.NetWorkState
import com.example.base.databinding.ErrorDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class BaseFragment(private val layout: Int) : Fragment() {
    private var fragmentView: ViewGroup? = null
    private var baseActivity: BaseActivity? = null
    var savedInstanceState: Bundle? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layout, container, false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        fragmentView = null
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.baseActivity = activity
        }
    }

    protected fun showProgressFullScreen() {
        baseActivity?.showProgressFullScreen()
    }

    protected fun hideProgressFullScreen(fragmentRecords: Int) {
        baseActivity?.hideProgressFullScreen(fragmentRecords)
    }

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }

    open fun ErrorDialog(error: String) {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding =
            dialog?.layoutInflater?.let { ErrorDialogBinding.inflate(it) } // Inflate with dialog's layoutInflater
        binding?.root?.let { dialog?.setContentView(it) }
        dialog?.setCancelable(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER_VERTICAL)
        dialog?.show()
        Log.d("islam", "ErrorDialog: $error")
//        binding?.islka?.text = error // Set the text for the TextView
//        binding?.con?.setOnClickListener {
//            dialog.dismiss()
//        }
    }

    protected fun handleErrorGeneral(th: Throwable, func: (() -> Unit)? = null): CustomErrorThrow? {
        return baseActivity?.handleErrorGeneral(th, func)
    }


    fun <T> Fragment.handleFlow(
        userFlow: Flow<T>,
        lifeCycle: Lifecycle.State = Lifecycle.State.STARTED,
        onAction: (flow: T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifeCycle) {
                userFlow.collect { flow ->
                    onAction.invoke(flow)
                }
            }
        }
    }

    fun Fragment.handleSharedFlow(
        userFlow: SharedFlow<NetWorkState>,
        lifeCycle: Lifecycle.State = Lifecycle.State.STARTED,
        onShowProgress: (() -> Unit)? = null,
        onHideProgress: (() -> Unit)? = null,
        onSuccess: (data: Any) -> Unit,
        onError: ((th: Throwable) -> Unit)? = null
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifeCycle) {
                userFlow.collect { networkState ->
                    when (networkState) {
                        is NetWorkState.Success<*> -> {
                            onSuccess(networkState.data!!)
                        }
                        is NetWorkState.Error -> {
                            if (onError == null) handleErrorGeneral(networkState.th) else onError(
                                networkState.th
                            )
                        }

                        else -> {
                        }
                    }

                }
            }
        }
    }

 /*   fun Fragment.handleStateFlow(
        userFlow: StateFlow<NetWorkState>,
        onShowProgress: (() -> Unit)? = null,
        onHideProgress: (() -> Unit)? = null,
        onSuccess: (data: Any) -> Unit,
        onError: ((th: Throwable) -> Unit)? = null
    ) {
        (activity as BaseActivity).handleStateFlow(userFlow, onShowProgress, onHideProgress, onSuccess,onError)
    }*/



    fun hideKeyboard(){
        baseActivity?.hideKeyboard()
    }
}