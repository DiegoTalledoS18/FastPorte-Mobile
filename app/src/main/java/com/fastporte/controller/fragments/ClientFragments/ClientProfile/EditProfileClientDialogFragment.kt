package com.fastporte.controller.fragments.ClientFragments.ClientProfile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.fastporte.databinding.FragmentDialogEditProfileBinding
import com.fastporte.helpers.BaseURL
import com.fastporte.helpers.SharedPreferences
import com.fastporte.models.User
import com.fastporte.network.ProfileService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditProfileClientDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogEditProfileBinding
    val bundle = Bundle()
    var user: User? = null

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BaseURL.BASE_URL.toString())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val profileService: ProfileService = retrofit.create(ProfileService::class.java)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FragmentDialogEditProfileBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        getCurrentUser()

        showUserInfo()

        builder.setPositiveButton("Update") { _, _ ->
            val name = binding.tilName.editText?.text.toString()
            val lastname = binding.tilLastname.editText?.text.toString()
            val phone = binding.tilPhone.editText?.text.toString()
            val birthday = binding.tilBirthday.editText?.text.toString()

            user?.name = name
            user?.lastname = lastname
            user?.phone = phone
            user?.birthdate = birthday

            updateUser()

            dismiss()
        }

        builder.setNegativeButton("Cancel") { _, _ ->
            dismiss()
        }

        val dialog = builder.create()
        dialog.show()

        return dialog
        // return super.onCreateDialog(savedInstanceState)
    }

    @SuppressLint("SimpleDateFormat")
    private fun showUserInfo() {
        binding.tilName.editText?.setText(user?.name)
        binding.tilLastname.editText?.setText(user?.lastname)
        binding.tilPhone.editText?.setText(user?.phone)
        binding.tilBirthday.editText?.setText(user?.birthdate)
    }

    private fun getCurrentUser() {
        val request = profileService.getClientProfile(
            SharedPreferences(this.requireContext()).getValue("id")!!.toInt(), "json"
        )

        request.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Activity fail", t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user = response.body()!!
                    Log.d("Activity success", user.toString())
                } else {
                    Log.d("Activity fail", response.toString())
                }
            }
        })
    }

    private fun updateUser() {
        val request = profileService.updateClient(
            SharedPreferences(this.requireContext()).getValue("id")!!.toInt(), this.user
        )

        request.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Activity fail", t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("Activity success", response.body().toString())
                } else {
                    Log.d("Activity fail", response.toString())
                }
            }
        })
    }
}