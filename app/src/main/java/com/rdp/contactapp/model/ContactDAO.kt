package com.rdp.contactapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDAO {

    @Query("Select * from contacts_table where id = :con_id order by contact_name asc")
    fun getallcontactbyid(con_id: Int):LiveData<Contact>  // LiveData - life cycle aware data.LiveData can be observed

    @Query("SELECT * from contacts_table WHERE contact_type LIKE '%' || :filter || '%' ORDER BY contact_name asc")
    fun getItemsFiltered(filter: String): LiveData<List<Contact>>

    @Query("SELECT * from contacts_table WHERE contact_name LIKE '%' || :filter || '%' or contact_phone LIKE '%' || :filter || '%'   ORDER BY contact_name asc")
    fun getItemsFilteredbyNameOrContact(filter: String): LiveData<List<Contact>>

    @Insert
    suspend fun insert(Contact: Contact)  // suspend function show that it can run in background (does not block our main UI thread)

    @Delete
    suspend fun delete(Contact: Contact) // suspend function show that it can run in background (does not block our main UI thread)

    @Update
    suspend fun update(Contact: Contact) // suspend function show that it can run in background (does not block our main UI thread)

}