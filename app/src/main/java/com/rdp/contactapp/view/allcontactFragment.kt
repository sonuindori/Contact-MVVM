package com.rdp.contactapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rdp.contactapp.databinding.FragmentAllcontactBinding
import com.rdp.contactapp.model.Contact
import com.rdp.contactapp.viewmodel.contactViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [allcontactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class allcontactFragment : Fragment() ,IcontactAdap {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private var _binding: FragmentAllcontactBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllcontactBinding.inflate(inflater, container, false)
        binding.allcontactRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = contactAdapter(requireActivity(),this)
        binding.allcontactRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            contactViewModel::class.java)
        viewModel.setFilter("")
        viewModel.allItemsFiltered.observe(requireActivity(), Observer {
            adapter.updatelist(it);
            if(it.size==0)
            {
               binding.textView12.visibility = View.VISIBLE
            }else
            {
                binding.textView12.visibility = View.GONE
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment allcontactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            allcontactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onitemclicked(Contact: Contact) {
        val intent = Intent(requireActivity(),ViewContact::class.java)
       intent.putExtra("myContactId",Contact.id)
        startActivity(intent)
    }
}