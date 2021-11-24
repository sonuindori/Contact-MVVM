package com.rdp.contactapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.rdp.contactapp.model.Contact
import com.rdp.contactapp.model.ContactDatabase
import com.rdp.contactapp.model.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class contactViewModel(application: Application) : AndroidViewModel(application) {
     // Using LiveData and caching what getallcontacts returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
   //   the UI when the data actually changes.
  // - Repository is completely separated from the UI through the ViewModel.
    val  repository:ContactRepository
   // val allcontactsbyid : LiveData<List<contact>>
    val allItemsFiltered : LiveData<List<Contact>>
    val allItemsFilteredByName : LiveData<List<Contact>>
    var filter = MutableLiveData<String>("%")

    init {
        val dao = ContactDatabase.getDatabase(application).getcontactDAO()
        repository = ContactRepository(dao)

        allItemsFilteredByName = Transformations.switchMap(filter) { filter ->
            repository.getcontactsfilterdbyName(filter)
        }
        allItemsFiltered = Transformations.switchMap(filter) { filter ->
            repository.getcontactsfilterd(filter)
        }
    }

    fun deleteContact(Contact: Contact) = viewModelScope.launch { Dispatchers.IO
             repository.deletecontact(Contact)
    }

    fun getRecordByconId(primaryKey: Int) = repository.allContactsbyid(primaryKey)

    fun insertcontact(Contact: Contact) = viewModelScope.launch { Dispatchers.IO
      repository.insertcontact(Contact)
    }

    fun update(Contact: Contact) = viewModelScope.launch { Dispatchers.IO
        repository.updatecontact(Contact)
    }


    fun setFilter(newFilter: String) {
        // optional: add wildcards to the filter
        val f = when {
            newFilter.isEmpty() -> "%"
            else -> "%$newFilter%"
        }
        filter.postValue(f) // apply the filter
    }

}


