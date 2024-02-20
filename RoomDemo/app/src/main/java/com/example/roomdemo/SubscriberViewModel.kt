package com.example.roomdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {
    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: MutableLiveData<Event<String>>
        get() = statusMessage


    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    /*
    Initializing the values for these button texts as they should change dynamically.
    When the user clicks on a list item, the selected subscribers name and email should display on the input fields and
    SAVE button should change to UPDATE and CLEAR ALL button should change to DELETE
     */
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }


    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        if (isUpdateOrDelete) {
            update(Subscriber(subscriberToUpdateOrDelete.id, name, email))
        } else {
            insert(Subscriber(id = 0, name = name, email = email))
        }

        resetToInitialValues()
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertSubscriber(subscriber)
        withContext(Dispatchers.Main) {
            statusMessage.value = Event("Subscriber Inserted Successfully")
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateSubscriber(subscriber)
        withContext(Dispatchers.Main) {
            statusMessage.value = Event("Subscriber Updated Successfully")
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteSubscriber(subscriber)
        withContext(Dispatchers.Main) {
            resetToInitialValues()
            statusMessage.value = Event("Subscriber Deleted Successfully")
        }
    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
        withContext(Dispatchers.Main) {
            statusMessage.value = Event("All Subscribers Deleted Successfully")
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email

        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber

        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    private fun resetToInitialValues() {
        inputName.value = ""
        inputEmail.value = ""

        isUpdateOrDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
}