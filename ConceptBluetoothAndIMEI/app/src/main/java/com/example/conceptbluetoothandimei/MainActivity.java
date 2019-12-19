package com.example.conceptbluetoothandimei;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.conceptbluetoothandimei.ui.adapters.DeviceAdapter;
import com.example.conceptbluetoothandimei.ui.adapters.OnItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private RecyclerView pairedDevicesRecyclerView;
    private RecyclerView aviableDevicesRecyclerView;
    private DeviceAdapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<BluetoothDevice> devicesAviablesFinded;
    private ArrayList<BluetoothDevice> devicesPairedFinded;
    private BluetoothFinder bluetoothFinder;
    private BluetoothAdapter bluetoothAdapter;

    private  ConnectThread connectThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_list);

        pairedDevicesRecyclerView = findViewById(R.id.recyclerViewPairedDevices);
        aviableDevicesRecyclerView = findViewById(R.id.recyclerViewAviableDevices);

        bluetoothFinder = new BluetoothFinder();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devicesAviablesFinded = new ArrayList<>();
        devicesPairedFinded = bluetoothFinder.Paireds();

        buildRecyclerView(pairedDevicesRecyclerView, devicesPairedFinded);

        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        bluetoothAdapter.startDiscovery();
    }

    private void buildRecyclerView(RecyclerView devicesRecyclerView, ArrayList<BluetoothDevice> devices) {
        devicesRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        deviceAdapter = new DeviceAdapter(devices, new OnItemClickListener() {
            @Override
            public void onItemClick(BluetoothDevice bluetoothDevice) {
                Log.d("Click",bluetoothDevice.getName());
            }
        });

        devicesRecyclerView.setLayoutManager(layoutManager);
        devicesRecyclerView.setAdapter(deviceAdapter);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesAviablesFinded = bluetoothFinder.Aviables(device);
                buildRecyclerView(aviableDevicesRecyclerView, bluetoothFinder.Aviables(device));
            }
        }
    };
}
