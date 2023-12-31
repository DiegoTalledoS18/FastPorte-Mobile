package com.fastporte.controller.fragments.ClientFragments.ClientContracts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fastporte.R
import com.fastporte.helpers.LoadContracts

class ClientPendingContractsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_pending_contracts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadContracts = LoadContracts()
        loadContracts.getPendingContractsOfClient(view, view.context)
    }

    override fun onResume() {
        super.onResume()

        val loadContracts = LoadContracts()
        loadContracts.getPendingContractsOfClient(requireView(), requireContext())
    }
}