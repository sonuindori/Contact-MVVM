package com.rdp.contactapp.model

import androidx.lifecycle.LiveData
// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ContactRepository (private val ContactDAO: ContactDAO ) {

    fun allContactsbyid(con_id: Int): LiveData<Contact> {
        return ContactDAO.getallcontactbyid(con_id)
    }

    fun getcontactsfilterd(filter: String): LiveData<List<Contact>> {
        return ContactDAO.getItemsFiltered(filter)
    }

    fun getcontactsfilterdbyName(filter: String): LiveData<List<Contact>> {
        return ContactDAO.getItemsFilteredbyNameOrContact(filter)
    }

    suspend fun insertcontact(Contact: Contact){
        ContactDAO.insert(Contact)
    }

    suspend fun deletecontact (Contact: Contact)
    {
        ContactDAO.delete(Contact)
    }

    suspend fun updatecontact (Contact: Contact)
    {
        ContactDAO.update(Contact)
    }
}