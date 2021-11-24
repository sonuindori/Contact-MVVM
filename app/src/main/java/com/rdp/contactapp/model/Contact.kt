package com.rdp.contactapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
class Contact(@ColumnInfo(name="contact_phone")val contact_phone:String, @ColumnInfo(name="contact_email")val contact_email:String, @ColumnInfo(name="contact_name")val contact_name:String, @ColumnInfo(name="contact_type")val contact_type:String)   {
    @PrimaryKey(autoGenerate = true)var id =0
}