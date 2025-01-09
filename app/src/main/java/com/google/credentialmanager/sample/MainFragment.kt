package com.google.credentialmanager.sample

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.credentialmanager.sample.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var listener: MainFragmentCallback
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as MainFragmentCallback
        } catch (castException: ClassCastException) {
            /** The activity does not implement the listener.  */
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUp.setOnClickListener {
            listener.signup()
        }

        binding.signIn.setOnClickListener {
            listener.signIn()
        }

        binding.btnCheckPlatformAuths.setOnClickListener {
            checkPlatformAuthenticatorAvailable(requireActivity()) { isSupported, exception ->
                if (exception != null) {
                    setStatusMessage("Error checking platform authenticator: ${exception.message}")
                } else if (isSupported) {
                    setStatusMessage("Platform authenticators are available.")
                } else {
                    setStatusMessage("Platform authenticators are not available.")
                }
            }
        }

        binding.btnCheckOs.setOnClickListener{
            setStatusMessage("Version: ${Build.VERSION.RELEASE}, API Level: ${Build.VERSION.SDK_INT}")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setStatusMessage(message: String) {
        binding.tvStatusMessage.post {
            binding.tvStatusMessage.text = message
        }
    }

    interface MainFragmentCallback {
        fun signup()
        fun signIn()
        fun checkPlatformAuthenticators()
    }
}
