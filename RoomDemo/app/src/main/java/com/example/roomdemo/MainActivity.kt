package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberDatabase
import com.example.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var viewModelFactory: SubscriberViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(this).dao
        val repository = SubscriberRepository(dao)
        viewModelFactory = SubscriberViewModelFactory(repository = repository)

        subscriberViewModel =
            ViewModelProvider(this, viewModelFactory)[SubscriberViewModel::class.java]

        binding.myViewModel = subscriberViewModel

        // Since we are intending to use LiveData with DataBinding, we should provide a LifeCycle Owner
        binding.lifecycleOwner = this

        initRecyclerView()

        subscriberViewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let { statusMessage ->
                Toast.makeText(this, statusMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("111. ---", it.toString())
            binding.subscriberRecyclerView.adapter = MyRecyclerViewAdapter(
                it,
                { selectedItem: Subscriber -> listItemClick(selectedItem) }
            )
        })
    }

    /*
    When the user clicks on the list item, name and email should display in the input fields
    and SAVE should change to UPDATE and CLEAR should change to DELETE
     */
    private fun listItemClick(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}