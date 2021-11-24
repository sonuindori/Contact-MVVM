package com.rdp.contactapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rdp.contactapp.R
import com.rdp.contactapp.model.Contact

class contactAdapter(val context: Context, val listner: IcontactAdap) : RecyclerView.Adapter<contactAdapter.ContacViewHolder>() {
    val  allcontacts = ArrayList<Contact>()
    private var mFilteredNetworkEntries: List<Contact> = ArrayList()
    inner class ContacViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        val textView:TextView = itemview.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContacViewHolder {
     val viewHolder = ContacViewHolder(
         LayoutInflater.from(context).inflate(
             R.layout.item_contact,
             parent,
             false
         )
     )
       viewHolder.textView.setOnClickListener {
           listner.onitemclicked(allcontacts[viewHolder.adapterPosition])
       }
      return  viewHolder
    }

    override fun onBindViewHolder(holder: ContacViewHolder, position: Int) {
      val currentcontact = allcontacts[position]
        holder.textView.text = currentcontact.contact_name
    }

    override fun getItemCount(): Int {
      return allcontacts.size
    }

    fun updatelist(newList: List<Contact>)
    {
        allcontacts.clear()
        allcontacts.addAll(newList)
        notifyDataSetChanged()
    }
}

interface IcontactAdap
{
   fun onitemclicked(Contact: Contact)
}