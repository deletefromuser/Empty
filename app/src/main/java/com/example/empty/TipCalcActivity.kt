package com.example.empty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.empty.databinding.ActivityTipCalcBinding
import kotlin.math.ceil

class TipCalcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipCalcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipCalcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
        binding.resultOfTip.text =
            (if (binding.roundUpSwitch.isChecked) ceil(tip) else tip).toString()
    }

    fun onRadioButtonClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        binding.resultOfTip.text = "-"
    }
}