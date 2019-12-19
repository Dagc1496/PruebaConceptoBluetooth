package com.example.conceptbluetoothandimei.ui.adapters;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conceptbluetoothandimei.R;
import com.example.conceptbluetoothandimei.ui.viewHolders.DeviceViewHolder;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter {

    private ArrayList<BluetoothDevice> devicesNameList;
    private OnItemClickListener onItemClickListener;

    public DeviceAdapter(ArrayList<BluetoothDevice> devicesName, OnItemClickListener onItemClickListener){
        this.devicesNameList = devicesName;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.devices_list_item, parent, false);
        DeviceViewHolder deviceViewHolder = new DeviceViewHolder(itemView);
        return deviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceViewHolder deviceViewHolder = (DeviceViewHolder) holder;
        deviceViewHolder.Bind(devicesNameList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return devicesNameList.size();
    }
}
