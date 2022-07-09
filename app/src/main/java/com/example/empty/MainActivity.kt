package com.example.empty

import android.content.Intent
import android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the Send button */
    fun sendMessage(@Suppress("UNUSED_PARAMETER") view: View) {
        val editText = this.findViewById<EditText>(R.id.editTextTextPersonName)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** show All installed Package in device */
    fun showPackageInDevice(@Suppress("UNUSED_PARAMETER") view: View) {
        this.packageManager.getInstalledPackages(GET_UNINSTALLED_PACKAGES)
            .forEach { Log.i("APP", it.packageName) }
    }

    /** show All installed Package in device */
    fun openTipCalc(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, TipCalcActivity::class.java)
        startActivity(intent)
    }
}