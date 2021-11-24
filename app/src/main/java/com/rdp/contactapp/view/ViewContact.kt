package com.rdp.contactapp.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rdp.contactapp.databinding.ActivityViewContactBinding
import com.rdp.contactapp.databinding.BottomSheetDialogDeleteBinding
import com.rdp.contactapp.model.Contact
import com.rdp.contactapp.viewmodel.contactViewModel

class ViewContact : AppCompatActivity() {
     var user_id :Int ? = null
    var contactviewdata :LiveData<Contact> ? = null
    lateinit var binding : ActivityViewContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
        binding = ActivityViewContactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        user_id  =intent.getIntExtra("myContactId",0)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(
            contactViewModel::class.java)
        contactviewdata = viewModel.getRecordByconId(user_id!!)
        contactviewdata?.observe(this, Observer {
            binding.textView3.text = it.contact_name
            binding.textView4.text = it.contact_phone
            binding.textView6.text = it.contact_email
        })
    }

    fun callcontact(view: View)
    {
        val intent =  Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + contactviewdata?.value?.contact_phone.toString().trim())
        startActivity(intent)
    }

    fun messagecontact(view: View)
    {
        val intent =  Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("sms:"+  contactviewdata?.value?.contact_phone.toString().trim())
        startActivity(intent)
    }

    fun deletecontact(view:View)
    {
        val bottomdialog = BottomSheetDialog(this)
        var bindingbottomsheet: BottomSheetDialogDeleteBinding;
        bindingbottomsheet =BottomSheetDialogDeleteBinding.inflate(layoutInflater)
        val bottomview = bindingbottomsheet.root;
        bottomdialog.setContentView(bottomview);
        bottomdialog.show()
        bindingbottomsheet.textView9.text = "Delete \"" + contactviewdata?.value?.contact_name + "\""
        bindingbottomsheet.button5.setOnClickListener(object :View.OnClickListener
        {
            override fun onClick(p0: View?) {
                bottomdialog.dismiss()
            }
        })
        bindingbottomsheet.button4.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(p0: View?) {
                if(contactviewdata!!.hasObservers()){
                    contactviewdata!!.removeObservers(this@ViewContact)
                    viewModel.deleteContact(contactviewdata!!.value!!)
                    Toast.makeText(this@ViewContact,"Contact Deleted", Toast.LENGTH_SHORT).show()
                    bottomdialog.dismiss()
                    finish()
                }
            }
        })


    }

    fun editcontact(view: View)
    {
        val intent = Intent(this,add_contact::class.java)
        intent.putExtra("myContactId",user_id)
        startActivity(intent)
    }

    fun finishact(view: View) {
        finish()
    }
}