package com.rdp.contactapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rdp.contactapp.databinding.ActivitySearchBinding
import com.rdp.contactapp.model.Contact
import com.rdp.contactapp.viewmodel.contactViewModel

class SearchActivity : AppCompatActivity(),IcontactAdap {
    private lateinit var binding :ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(this)
        val adapter = contactAdapter(this,this)
        binding.recyclerViewSearch.adapter = adapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            contactViewModel::class.java)
        viewModel.setFilter("")
        viewModel.allItemsFilteredByName.observe(this, Observer {
            adapter.updatelist(it);
        })

        binding.searchView.setFocusable(true);
        binding.searchView.requestFocus();
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setFilter(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setFilter(newText)
                return false
            }
        })
    }

    override fun onitemclicked(Contact: Contact) {
        val intent = Intent(this,ViewContact::class.java)
        intent.putExtra("myContactId",Contact.id)
        startActivity(intent)
    }
}