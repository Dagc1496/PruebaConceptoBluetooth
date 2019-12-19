package com.example.conceptbluetoothandimei;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {

    private static final String APP_NAME = "BTChat";
    private static final UUID MY_UUID = UUID.fromString("76adb032-4560-4891-b2b9-a4266677f1fb");

    private final BluetoothSocket mmSocket;
    private BluetoothAdapter bluetoothAdapter;
    private final BluetoothDevice mmDevice;

    public ConnectThread(BluetoothDevice device, BluetoothAdapter bluetoothAdapter) {

        this.bluetoothAdapter = bluetoothAdapter;
        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e(APP_NAME, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        bluetoothAdapter.cancelDiscovery();

        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(APP_NAME, "Could not close the client socket", closeException);
            }
            return;
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(APP_NAME, "Could not close the client socket", e);
        }
    }
}
