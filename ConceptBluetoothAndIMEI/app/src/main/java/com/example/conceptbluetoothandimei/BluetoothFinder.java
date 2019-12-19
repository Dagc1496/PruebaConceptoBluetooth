package com.example.conceptbluetoothandimei;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothFinder {

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> btArrayPaireds;
    private ArrayList<BluetoothDevice> btArrayAviables;

    private  ConnectThread connectThread;

    public BluetoothFinder(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btArrayAviables = new ArrayList<>();
    }

    public ArrayList<BluetoothDevice> Paireds() {
        Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
        btArrayPaireds = new ArrayList<>();
        for (BluetoothDevice device : bt) {
            btArrayPaireds.add(device);
        }
        return btArrayPaireds;
    }

    public ArrayList<BluetoothDevice> Aviables(BluetoothDevice newDevice){
        if(btArrayAviables.isEmpty()){
            btArrayAviables.add(newDevice);
        }
        else{
            for (BluetoothDevice device : btArrayAviables){
                if(newDevice.getName().contains("J4+")){
                    connectThread = new ConnectThread(newDevice,bluetoothAdapter);
                    connectThread.run();
                }
                if(!device.getName().equals(newDevice.getName())){
                    btArrayAviables.add(newDevice);
                    break;
                }

            }
        }
        return btArrayAviables;
    }
}
