package com.adam.suitmedia1.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import com.adam.suitmedia1.R
import com.adam.suitmedia1.databinding.ActivitySecondScreenBinding

class SecondScreen : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the action bar
        val actionBar = supportActionBar

        // Set custom action bar layout
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.custom_action_bar)
        actionBar?.setDisplayShowCustomEnabled(true)

        val navBack = findViewById<ImageView>(R.id.action_bar_back_button)

        navBack.setOnClickListener {
            val intent = Intent(this@SecondScreen, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnChooseUser.setOnClickListener(this)

        val name = intent.getStringExtra(EXTRA_NAME)
        val username = intent.getStringExtra(EXTRA_USERNAME)

        binding.tvUsernameMain.text = username
        binding.tvUsernameWelcome.text = name

        if (username == null){
            binding.tvUsernameMain.text = getString(R.string.select_username)
        }

        if (name != null) {
            if (name.isEmpty()) {
                binding.tvUsernameWelcome.text = getString(R.string.username)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_choose_user -> {
                val name = binding.tvUsernameWelcome.text.toString()
                val intent = Intent(this@SecondScreen, ThirdScreen::class.java)
                intent.putExtra(ThirdScreen.EXTRA_NAME, name)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_USERNAME = "extra_username"
    }
}