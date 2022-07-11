package com.example.empty

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


/**
 * @see https://www.tutorialspoint.com/how-to-lock-the-android-device-programmatically
 * @see https://developer.android.com/guide/topics/admin/device-admin#developing
 */
class LockScreenActivity : AppCompatActivity() {
    private lateinit var deviceManger: DevicePolicyManager
    private lateinit var compName: ComponentName
    private lateinit var btnEnable: Button
    private lateinit var btnLock: Button
    private val getPermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            handlePermissionRequestResult(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)

        btnEnable = findViewById(R.id.btn_enable_lock)
        btnLock = findViewById(R.id.btn_lock)
        deviceManger = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        compName = ComponentName(this, DeviceAdmin::class.java)
        val active = deviceManger.isAdminActive(compName)
        if (active) {
            btnEnable.text = "Disable"
            btnLock.visibility = View.VISIBLE
        } else {
            btnEnable.text = "Enable"
            btnLock.visibility = View.GONE
        }
    }

    fun enablePhone(@Suppress("UNUSED_PARAMETER") view: View) {
        val active = deviceManger.isAdminActive(compName)
        if (active) {
            deviceManger.removeActiveAdmin(compName)
            btnEnable.text = "Enable"
            btnLock.visibility = View.GONE
        } else {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                R.string.permission_device_desc
            )
            getPermission.launch(intent)
        }
    }

    fun lockPhone(@Suppress("UNUSED_PARAMETER") view: View) {
        deviceManger.lockNow()
    }

    private val handlePermissionRequestResult: (ActivityResult) -> Unit =
        { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                btnEnable.text = "Disable"
                btnLock.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    applicationContext, "Failed!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

}