package com.example.hammersystemstestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hammersystemstestapp.beer.Beer
import com.example.hammersystemstestapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var myAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            val response: Response<List<Beer>> = try {
                RetrofitInstance.api.getBeers()
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "IOException", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "HttpException", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.also { myAdapter.beers = it }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Response was not successful",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        myAdapter = Adapter()
        adapter = myAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)

    }
}