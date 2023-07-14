package com.adam.suitmedia1.screens

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.adam.suitmedia1.R
import com.adam.suitmedia1.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_check -> {
                showDialogMessage()
            }
            R.id.btn_next -> {
                val nameInputText = binding.edtName.text
                Log.d("name", nameInputText.toString())
                val intent = Intent(this@MainActivity, SecondScreen::class.java)
                intent.putExtra(SecondScreen.EXTRA_NAME, nameInputText.toString())
                startActivity(intent)
            }
        }
    }

    private fun showDialogMessage() {
        val dialogBinding = layoutInflater.inflate(R.layout.my_custom_dialog, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val dialogButton = dialogBinding.findViewById<Button>(R.id.btn_alert)

        dialogButton.setOnClickListener {
            myDialog.dismiss()
        }

        val palindromeTextInput = binding.edtPalindrome.text.toString()
        val dialogMessage = dialogBinding.findViewById<TextView>(R.id.alert_message)

        val cleanedInput = palindromeTextInput.replace(Regex("[^A-Za-z0-9]"), "").lowercase(Locale.getDefault())
        val formattedInput = cleanedInput.reversed()

        if (cleanedInput == formattedInput) {
            dialogMessage.text = getString(R.string.isPalindrome)
        } else {
            dialogMessage.text = getString(R.string.notPalindrome)
        }
    }

}