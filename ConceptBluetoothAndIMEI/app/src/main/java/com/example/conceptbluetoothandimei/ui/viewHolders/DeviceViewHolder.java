package com.example.conceptbluetoothandimei.ui.viewHolders;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conceptbluetoothandimei.R;
import com.example.conceptbluetoothandimei.ui.adapters.OnItemClickListener;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewDeviceName;


    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);

        this.textViewDeviceName = itemView.findViewById(R.id.textViewDeviceName);
    }

    public void Bind(final BluetoothDevice newDevice, final OnItemClickListener onItemClickListener){
        textViewDeviceName.setText(newDevice.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(newDevice);
            }
        });
    }

}
