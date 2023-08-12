package com.patient.base

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONObject

class BaseBottomSheet<VB:ViewBinding>( private val bindingInflater: (inflater: LayoutInflater) -> VB):
    BottomSheetDialogFragment() {
    var binding: VB? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater)
        if (binding == null)
            throw IllegalArgumentException("Binding cannot be null")
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.apply {
            setDimAmount(.1f)
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
        binding = null
    }

//    fun isResponseValid(data: ApiResult<*>): Boolean {
//        when (data.status) {
//            ApiStatus.SUCCESS -> return true
//            ApiStatus.ERROR -> checkError(data.throwable)
//        }
//        return false
//    }

    fun showDialog(bottomSheetDialogFragment: DialogFragment) {
        if (childFragmentManager.findFragmentByTag(bottomSheetDialogFragment.javaClass.name.toString()) == null)
            kotlin.runCatching {
                bottomSheetDialogFragment.show(
                    childFragmentManager,
                    bottomSheetDialogFragment.javaClass.name.toString()
                )
            }
    }

    fun showBottomSheet(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        if (childFragmentManager.findFragmentByTag(bottomSheetDialogFragment.id.toString()) == null)
            kotlin.runCatching {
                bottomSheetDialogFragment.show(
                    childFragmentManager,
                    bottomSheetDialogFragment.id.toString()
                )
            }
    }

//    private fun checkError(throwable: Throwable?) {
//        if (throwable == null)
//            return
//        if (throwable is HttpException) {
//            when (throwable.response()?.code()) {
//                401 -> {
//                    context?.let { SharedHelper.clearUser(it) }
//                    FirebaseMessaging.getInstance().deleteToken()
//                    val intent = Intent(context, SplashActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    startActivity(intent)
//                    activity?.finishAffinity()
//                    activity?.overridePendingTransition(
//                        android.R.anim.fade_in,
//                        android.R.anim.fade_out
//                    );
//                }
//                500 -> displayServerMessageError(true, "TemporaryError")
//                440 -> {
//                    displayServerMessageError(true, "Account is Blocked")
//                    context?.let { SharedHelper.clearUser(it) }
//                    FirebaseMessaging.getInstance().deleteToken()
//                    if (activity is SplashActivity)
//                        return
//                    val intent = Intent(context, SplashActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    startActivity(intent)
//                    activity?.finishAffinity()
//                    activity?.overridePendingTransition(
//                        android.R.anim.fade_in,
//                        android.R.anim.fade_out
//                    );
//                }
//                else -> displayServerMessageError(
//                    NetworkConnectionHelper.isConnected(),
//                    throwable.response()?.errorBody()?.string()
//                )
//            }
//        }
//    }

    private var countDownTimer: CountDownTimer? = null

//    fun displayServerMessageError(isNetworkConnected: Boolean?, exception: String?) {
//        kotlin.runCatching {
//            val frame = view?.findViewById<FrameLayout>(R.id.frame_message)
//            val relative = view?.findViewById<CardView>(R.id.card_message)
//            if (relative?.isVisible == true)
//                relative?.scaleX = 0f
//            val textView = view?.findViewById<TextView>(R.id.message)
//            textView?.set("")
//            frame?.isVisible = true
//            relative?.isVisible = true
//            relative?.animate()?.scaleX(1f)?.setDuration(500)?.setListener(object :
//                Animator.AnimatorListener {
//                override fun onAnimationStart(p0: Animator?) {
//
//                }
//
//                override fun onAnimationEnd(p0: Animator?) {
//                    textView?.set(
//                        if (isNetworkConnected == false) getString(R.string.check_internet_connection) else getException(
//                            exception
//                        )
//                    )
//                    countDownTimer?.cancel()
//                    countDownTimer = object : CountDownTimer(3000, 500) {
//                        override fun onTick(p0: Long) {
//                            if (p0 in 400L..500L) {
//                                textView?.set("")
//                                relative.animate().scaleX(0f).setDuration(500).start()
//                            }
//                        }
//
//                        override fun onFinish() {
//                            relative?.isVisible = false
//                            frame?.isVisible = false
//                            countDownTimer?.cancel()
//                            countDownTimer = null
//                        }
//
//                    }
//                    countDownTimer?.start()
//
//                }
//
//                override fun onAnimationCancel(p0: Animator?) {
//
//                }
//
//                override fun onAnimationRepeat(p0: Animator?) {
//
//                }
//
//            })?.start()
//
//        }.onFailure {
//            Toast.makeText(context, "layout not attached", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun getException(exception: String?): String {
        if (exception.isNullOrEmpty())
            return ""
        kotlin.runCatching {
            return handleJsonError(JSONObject(exception))
        }
        return exception
    }

    private fun handleJsonError(jsonObject: JSONObject): String {
        var errorMessage = ""
        if (jsonObject.has("error")) {
            if (jsonObject.get("error") is JSONObject) {
                val keys: Iterator<*> = jsonObject.getJSONObject("error").keys()
                keys.forEach {
                    val temp: String? = jsonObject.getJSONObject("error").getString(it.toString())
                    if (!temp.isNullOrEmpty() && errorMessage.isNullOrEmpty())
                        errorMessage = temp
                }
            } else if (jsonObject.get("error") is String) {
                errorMessage = jsonObject.optString("error")
            }
        } else if (jsonObject.has("message")) {
            errorMessage = jsonObject.getString("message")
        } else {
            errorMessage = jsonObject.toString()
        }
        return errorMessage.replace("]", "").replace("[", "")
            .replace("\"", "")
    }

}