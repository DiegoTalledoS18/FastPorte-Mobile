package com.fastporte.controller.fragments.ClientFragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Filter
import android.widget.Toast
import androidx.navigation.Navigation
import com.fastporte.R
import com.fastporte.helpers.SharedPreferences


class SearchFragment : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        val items = listOf("Trunk", "Van", "Cargo Truck", "Bus", "Taxi")

        val autoComplete: AutoCompleteTextView = view.findViewById(R.id.auto_complete)

        val adapter = context?.let { VehicleAdapter<String>(it, R.layout.list_item, items) }

        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->

                val itemSelected = adapterView.getItemAtPosition(i)
            }
        val buttonresult = view.findViewById<Button>(R.id.bt_result)
        val editnumber = view.findViewById<EditText>(R.id.et_username)

        buttonresult.isEnabled = false

        buttonresult.setOnClickListener() {
            information(autoComplete.text.toString(), editnumber.text.toString())
            next(view)
        }

        editnumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                buttonresult.isEnabled = s.toString().isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                buttonresult.isEnabled = s.toString().isNotEmpty()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonresult.isEnabled = s.toString().isNotEmpty()
            }
        })

        return view
    }

    class VehicleAdapter<String>(context: Context, layout: Int, values: List<String>): ArrayAdapter<String> (context,layout,values) {
        val noFilter = object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                results.values = values
                results.count = values.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }

        override fun getFilter(): Filter {
            return noFilter
        }
    }

    private fun information(type_vehicle: String, Size: String) {
        val _context = requireContext()
        val sharedPreferences = SharedPreferences(_context)
        sharedPreferences.save("type", type_vehicle)
        sharedPreferences.save("size", Size)
    }

    private fun next(view_: View) {
        val btnNext = view_.findViewById<Button>(R.id.bt_result)
        Navigation.findNavController(view_)
            .navigate(R.id.action_searchFragment_to_searchResultFragment)
    }

}