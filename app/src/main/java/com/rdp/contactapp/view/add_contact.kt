package com.rdp.contactapp.view

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rdp.contactapp.R
import com.rdp.contactapp.databinding.ActivityAddContactBinding
import com.rdp.contactapp.databinding.BottomSheetDiscardBinding
import com.rdp.contactapp.model.Contact
import com.rdp.contactapp.viewmodel.contactViewModel


private lateinit var binding: ActivityAddContactBinding

class add_contact : AppCompatActivity() {
    var myint :Int ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.editTextTextPersonName.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        myint  =intent.getIntExtra("myContactId", 0)
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        ).get(contactViewModel::class.java)
        if(myint==0)
        {
            binding.textView2.setText("Add Contact")
        }else
        {
            binding.textView2.setText("Edit Contact")
            viewModel.getRecordByconId(myint!!).observe(this, androidx.lifecycle.Observer {
                binding.editTextTextPersonName.text =
                    Editable.Factory.getInstance().newEditable(it.contact_name)
                binding.editTextPhone.text =
                    Editable.Factory.getInstance().newEditable(it.contact_phone)
                binding.editTextTextPersonName2.text =
                    Editable.Factory.getInstance().newEditable(it.contact_email)
                var myarray = resources.getStringArray(R.array.contact_type_array)
                binding.spinner.setSelection(myarray.indexOf(it.contact_type))
            })
        }
    }

    fun addContact(view: View) {
        val contact_name_text = binding.editTextTextPersonName.text.toString()
        val contact_email_text = binding.editTextTextPersonName2.text.toString()
        val contact_phone_text = binding.editTextPhone.text.toString()
        val contact_type_text= binding.spinner.selectedItem.toString()
         if((validate(
                 arrayOf(
                     binding.editTextTextPersonName,
                     binding.editTextTextPersonName2,
                     binding.editTextPhone
                 )
             ))){
             if(myint==0){
                            viewModel.insertcontact(
                                Contact(
                                    contact_phone_text,
                                    contact_email_text,
                                    contact_name_text,
                                    contact_type_text
                                )
                            )
                              Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show()
                        }else
                        {
                            val updatecontact =   Contact(
                                contact_phone_text,
                                contact_email_text,
                                contact_name_text,
                                contact_type_text
                            )
                           myint?.let { updatecontact.id = myint!!}
                            viewModel.update(updatecontact)
                        }
             finish()
         }
    }

    override fun onBackPressed() {
        finishactmethod()
    }

    fun finishact(view: View)
    {
        finishactmethod()
    }

    fun finishactmethod() {
        val bottomdialog = BottomSheetDialog(this)
        var bindingbottomsheet: BottomSheetDiscardBinding;
        bindingbottomsheet = BottomSheetDiscardBinding.inflate(layoutInflater)
        val bottomview = bindingbottomsheet.root;
        bottomdialog.setContentView(bottomview);
        bottomdialog.show()
        bindingbottomsheet.button4.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(p0: View?) {
                bottomdialog.dismiss()
                finish()
            }
        })
        bindingbottomsheet.button5.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(p0: View?) {
                bottomdialog.dismiss()
            }
        })
    }

    private fun validate(fields: Array<EditText>): Boolean {
        for (i in fields.indices) {
            val currentField = fields[i]
            if (currentField.text.toString().length <= 0) {
                currentField.setError("Please Fill This")
                currentField.background = ContextCompat.getDrawable(this, R.drawable.edittexterror)
                return false
            }else
            {
                currentField.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.edittextselector
                )
            }
        }
        return true
    }
}