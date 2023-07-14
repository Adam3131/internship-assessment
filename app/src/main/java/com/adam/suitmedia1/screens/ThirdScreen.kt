package com.adam.suitmedia1.screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adam.suitmedia1.R
import com.adam.suitmedia1.adapter.UserAdapter
import com.adam.suitmedia1.data.response.User
import com.adam.suitmedia1.data.response.UserResponse
import com.adam.suitmedia1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreen : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private var userList: MutableList<User> = mutableListOf()
    private var currentPage = 1
    private val perPage = 20

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        // Retrieve the action bar
        val actionBar = supportActionBar

        // Set custom action bar layout
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.custom_action_bar)
        actionBar?.setDisplayShowCustomEnabled(true)

        val navBack = findViewById<ImageView>(R.id.action_bar_back_button)

        navBack.setOnClickListener {
            val intent = Intent(this@ThirdScreen, SecondScreen::class.java)
            startActivity(intent)
        }

        val name = intent.getStringExtra(EXTRA_NAME)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.rvUser)
        userAdapter = UserAdapter(userList)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreen)
            adapter = userAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            fetchUsers()
        }

        userAdapter.setOnItemClickListener { user ->
            // Handle item click event
            val intent = Intent(this@ThirdScreen, SecondScreen::class.java)
            intent.putExtra(SecondScreen.EXTRA_USERNAME, user.firstName)

            intent.putExtra(SecondScreen.EXTRA_NAME, name)
            Log.d("name", name.toString())
            startActivity(intent)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    currentPage++
                    fetchUsers()
                }
            }
        })

        fetchUsers()
    }

    private fun fetchUsers() {
        // Make an API call to fetch user data
        ApiConfig.getApiService().getUserList(currentPage, perPage)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    Log.d("API Response", "onResponse")
                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        val users = userResponse?.data
                        if (!users.isNullOrEmpty()) {
                            if (currentPage == 1) {
                                userList.clear()
                            }
                            userList.addAll(users)
                            userAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(this@ThirdScreen, "Failed to fetch users", Toast.LENGTH_SHORT).show()
                    }
                    swipeRefreshLayout.isRefreshing = false
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("API Response", "onFailure: ${t.message}", t)
                    Toast.makeText(this@ThirdScreen, "Failed to fetch users: ${t.message}", Toast.LENGTH_SHORT).show()
                    swipeRefreshLayout.isRefreshing = false
                }
            })
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}