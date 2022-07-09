package com.example.empty

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.empty.databinding.ActivityTipCalcBinding
import java.text.NumberFormat
import java.util.*
import kotlin.math.ceil

class TipCalcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipCalcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipCalcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    fun calcTip(@Suppress("UNUSED_PARAMETER") view: View) {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull() ?: return
        // Check which radio button was clicked
        val tip: Double = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent ->
                cost * 0.2
            R.id.option_eighteen_percent ->
                cost * 0.18
            else ->
                cost * 0.15
        }
        displayTip(if (binding.roundUpSwitch.isChecked) ceil(tip) else tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        binding.resultOfTip.text = getString(R.string.tip_amount, formattedTip)
    }

    fun onRadioButtonClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        binding.resultOfTip.text = "Tip Amount: -"
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}