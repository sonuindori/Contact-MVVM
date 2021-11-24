package com.rdp.contactapp.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.rdp.contactapp.adapter.ViewPagerAdapter
import com.rdp.contactapp.databinding.ContactMainBinding
import com.rdp.contactapp.model.Contact
import com.rdp.contactapp.viewmodel.contactViewModel


lateinit var viewModel: contactViewModel
val typeArray = arrayOf(
    "All",
    "Personal",
    "Business"
)
class contactsActivity : AppCompatActivity() {
    lateinit var binding: ContactMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContactMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.floatingActionButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@contactsActivity, add_contact::class.java)
                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@contactsActivity,  binding.floatingActionButton, "transition_fab")
                startActivity(intent,options.toBundle())
            }
        })

        binding.imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@contactsActivity, SearchActivity::class.java))
            }
        })

        val adaptera = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adaptera

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->    // A mediator to link a TabLayout with a ViewPager2
            tab.text = typeArray[position]
        }.attach()
    }

}