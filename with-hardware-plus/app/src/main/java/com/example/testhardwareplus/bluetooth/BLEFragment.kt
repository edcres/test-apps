package com.example.testhardwareplus.bluetooth

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testhardwareplus.R

// https://developer.android.com/guide/topics/connectivity/bluetooth/find-ble-devices
// https://medium.com/@nithinjith.p/ble-in-android-kotlin-c485f0e83c16

/*
 * BLE (Bluetooth Low Energy)
 * - used for
 *    - Transferring small amounts of data between nearby devices.
 *    - Interacting with proximity sensors to give users a customized experience based on their current location.
 *
 * - Unlike classic Bluetooth, the BLE API’s are under the Location section in Android.
 *
 * setting it up"
 *  - first form a channel of communication.
 *  - declare several permissions in your manifest file
 *  - access the BluetoothAdapter and determine if Bluetooth is available on the device.
 *  - If Bluetooth is available, the device will scan for nearby BLE devices.
 *  - the capabilities of the BLE device are discovered by connecting to the GATT server on the BLE device.
 *  - Once a connection is made, data can be transferred with the connected device
 *      based on the available services and characteristics.
 *
 * Key concepts:
 *  - Generic Attribute Profile (GATT):  a general specification for sending and receiving
 *          short pieces of data.
 *  - Profiles:  a specification for how a device works in a particular application
 *  - ATT:  GATT is built on top of the Attribute Protocol (ATT)
 *  - Characteristic: characteristic contains a single value and 0-n descriptors that describe
 *          the characteristic's value. Is analogous to a class.
 *  - Descriptors: defined attributes that describe a characteristic value
 *  - Service: A collection of characteristics. (Example: "Heart Rate Monitor")
 */

// The app uses the classes below, I did not add them bc I just want to see how it works.
//   BLEConnectionManager, BLEConstants, BLEDeviceManager, BLEService, BleDeviceData.kt, OnDeviceScanListener.kt (implemented as adependemcy)

class BLEFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_b_l_e, container, false)

//        checkLocationPermission()

        return view
    }

//    override fun onScanCompleted(deviceDataList: BleDeviceData) {
//
//        //Initiate a dialog Fragment from here and ask the user to select his device
//        // If the application already know the Mac address, we can simply call connect device
//
//        mDeviceAddress = deviceDataList.mDeviceAddress
//        BLEConnectionManager.connect(deviceDataList.mDeviceAddress)
//
//    }
//
//    /**
//     * If the user either accepts or reject the Permission- The requested App will get a callback
//     * From the call back we can filter the user response with the help of request key
//     * If the user accepts, we can proceed further steps
//     */
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_LOCATION_PERMISSION -> {
//                if (permissions.size != 1 || grantResults.size != 1) {
//                    throw RuntimeException("Error on requesting location permission.")
//                }
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    initBLEModule()
//                } else {
//                    Toast.makeText(requireActivity(), R.string.location_permission_not_granted,
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    /**
//     * Check the Location Permission before calling the BLE API's
//     */
//    private fun checkLocationPermission() {
//        if (isAboveMarshmallow()) {
//            when {
//                isLocationPermissionEnabled() -> initBLEModule()
//                ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
//                    Manifest.permission.ACCESS_COARSE_LOCATION) -> displayRationale()
//                else -> requestLocationPermission()
//            }
//        } else {
//            initBLEModule()
//        }
//    }
//
//    /**
//     * Request Location API
//     * If the request goes to Android OS and the System will throw a dialog message
//     * user can accept or decline the permission from there
//     */
//    private fun requestLocationPermission() {
//        ActivityCompat.requestPermissions(requireActivity(),
//            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//            REQUEST_LOCATION_PERMISSION)
//    }
//
//    /**
//     * If the user decline the Permission request and clicks the never ask again message
//     * Then the application can't proceed further
//     * In this situation- App needs to prompt the user to do the change from settings Manually
//     */
//    private fun displayRationale() {
//        AlertDialog.Builder(requireContext())
//            .setMessage("Location permission disabled")
//            .setPositiveButton("Ok") { _, _ -> requestLocationPermission() }
//            .setNegativeButton("Cancel") { _, _ -> }
//            .show()
//    }
//
//    /**
//     * Check with the system- If the permission is already enabled or not
//     */
//    private fun isLocationPermissionEnabled(): Boolean {
//        return ContextCompat.checkSelfPermission(requireContext(),
//            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//
//    /**
//     * The location permission is incorporated in Marshmallow and Above
//     */
//    private fun isAboveMarshmallow(): Boolean {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//    }
//
//    /**
//     * After the Location Permission is received, the Application need to initialize the
//     * BLE Module and BLE Service
//     */
//    private fun initBLEModule() {
//        // BLE initialization
//        if (!BLEDeviceManager.init(this)) {
//            Toast.makeText(requireContext(), "BLE NOT SUPPORTED", Toast.LENGTH_SHORT).show()
//            return
//        }
//        registerServiceReceiver()
//        BLEDeviceManager.setListener(this)
//
//        if (!BLEDeviceManager.isEnabled()) {
//            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
//        }
//
//        BLEConnectionManager.initBLEService(this@MainActivity)
//    }
//
//    /**
//     * Register GATT update receiver
//     */
//    private fun registerServiceReceiver() {
//        requireActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())
//    }
//
//    private val mGattUpdateReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val action = intent.action
//            when {
//                BLEConstants.ACTION_GATT_CONNECTED.equals(action) -> {
//                    Log.i(TAG, "ACTION_GATT_CONNECTED ")
//                    BLEConnectionManager.findBLEGattService(this@MainActivity)
//                }
//                BLEConstants.ACTION_GATT_DISCONNECTED.equals(action) -> {
//                    Log.i(TAG, "ACTION_GATT_DISCONNECTED ")
//                }
//                BLEConstants.ACTION_GATT_SERVICES_DISCOVERED.equals(action) -> {
//                    Log.i(TAG, "ACTION_GATT_SERVICES_DISCOVERED ")
//                    try {
//                        Thread.sleep(500)
//                    } catch (e: InterruptedException) {
//                        e.printStackTrace()
//                    }
//                    BLEConnectionManager.findBLEGattService(this@MainActivity)
//                }
//                BLEConstants.ACTION_DATA_AVAILABLE.equals(action) -> {
//                    val data = intent.getStringExtra(BLEConstants.EXTRA_DATA)
//                    val uuId = intent.getStringExtra(BLEConstants.EXTRA_UUID)
//                    Log.i(TAG, "ACTION_DATA_AVAILABLE $data")
//
//                }
//                BLEConstants.ACTION_DATA_WRITTEN.equals(action) -> {
//                    val data = intent.getStringExtra(BLEConstants.EXTRA_DATA)
//                    Log.i(TAG, "ACTION_DATA_WRITTEN ")
//                }
//            }
//        }
//    }
//
//    /**
//     * Intent filter for Handling BLEService broadcast.
//     */
//    private fun makeGattUpdateIntentFilter(): IntentFilter {
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(BLEConstants.ACTION_GATT_CONNECTED)
//        intentFilter.addAction(BLEConstants.ACTION_GATT_DISCONNECTED)
//        intentFilter.addAction(BLEConstants.ACTION_GATT_SERVICES_DISCOVERED)
//        intentFilter.addAction(BLEConstants.ACTION_DATA_AVAILABLE)
//        intentFilter.addAction(BLEConstants.ACTION_DATA_WRITTEN)
//
//        return intentFilter
//    }
//
//    /**
//     * Unregister GATT update receiver
//     */
//    private fun unRegisterServiceReceiver() {
//        try {
//            this.unregisterReceiver(mGattUpdateReceiver)
//        } catch (e: Exception) {
//            //May get an exception while user denies the permission and user exists the app
//            Log.e(TAG, e.message)
//        }
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        BLEConnectionManager.disconnect()
//        unRegisterServiceReceiver()
//    }
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.btn_scan ->
//                scanDevice(false)
//            R.id.btn_read_connection ->
//                readMissedConnection()
//            R.id.btn_read_battery ->
//                readBatteryLevel()
//
//            R.id.btn_read_emergency ->
//                readEmergencyGatt()
//
//            R.id.btn_write_emergency ->
//                writeEmergency()
//
//            R.id.btn_write_battery ->
//                writeBattery()
//
//            R.id.btn_write_connection ->
//                writeMissedConnection()
//
//        }
//    }
//
//    private fun writeEmergency() {
//        BLEConnectionManager.writeEmergencyGatt("0xfe");
//    }
//
//    private fun writeBattery() {
//        BLEConnectionManager.writeBatteryLevel("100")
//    }
//
//    private fun writeMissedConnection() {
//        BLEConnectionManager.writeMissedConnection("0x00")
//    }
//
//    private fun readMissedConnection() {
//        BLEConnectionManager.readMissedConnection(getString(R.string.char_uuid_missed_calls))
//    }
//
//    private fun readBatteryLevel() {
//        BLEConnectionManager.readBatteryLevel(getString(R.string.char_uuid_emergency))
//    }
//
//    private fun readEmergencyGatt() {
//        BLEConnectionManager.readEmergencyGatt(getString(R.string.char_uuid_emergency))
//    }
//
//    /**
//     * Scan the BLE device if the device address is null
//     * else the app will try to connect with device with existing device address.
//     */
//    private fun scanDevice(isContinuesScan: Boolean) {
//        if (!mDeviceAddress.isNullOrEmpty()) {
//            connectDevice()
//        } else {
//            BLEDeviceManager.scanBLEDevice(isContinuesScan)
//        }
//    }
//
//    /**
//     * Connect the application with BLE device with selected device address.
//     */
//    private fun connectDevice() {
//        Handler().postDelayed({
//            BLEConnectionManager.initBLEService(this@MainActivity)
//            if (BLEConnectionManager.connect(mDeviceAddress)) {
//                Toast.makeText(this@MainActivity, "DEVICE CONNECTED", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this@MainActivity, "DEVICE CONNECTION FAILED", Toast.LENGTH_SHORT).show()
//            }
//        }, 100)
//    }

}
