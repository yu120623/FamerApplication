package com.egceo.app.myfarm.services;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import com.egceo.app.myfarm.bluetooth.BluetoothLeScanner;
import com.egceo.app.myfarm.bluetooth.BluetoothUtils;
import com.egceo.app.myfarm.util.AppUtil;
import com.mob.tools.utils.SharePrefrenceHelper;

import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;

import java.util.HashSet;
import java.util.Set;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;

public class IbeaconService extends Service implements BootstrapNotifier {
    private BluetoothLeScanner mScanner;
    protected SharedPreferences sp;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sp = this.getSharedPreferences("sp", MODE_PRIVATE);
        init();
        return super.onStartCommand(intent, flags, startId);
    }


    private void init(){
        sp.edit().remove(AppUtil.SP_IBEACON_IDS).commit();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            BluetoothUtils bluetoothUtils =  new BluetoothUtils(getApplicationContext());
            if(bluetoothUtils.isBluetoothLeSupported() && bluetoothUtils.isBluetoothOn() && Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mScanner = new BluetoothLeScanner(new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                        device.getUuids();
                        BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
                        if (BeaconUtils.getBeaconType(deviceLe) == BeaconType.IBEACON) {
                            final IBeaconDevice iBeacon = new IBeaconDevice(deviceLe);
                            Set<String> ids = sp.getStringSet(AppUtil.SP_IBEACON_IDS,new HashSet<String>());
                            ids.add(iBeacon.getUUID().toUpperCase());
                            sp.edit().putStringSet(AppUtil.SP_IBEACON_IDS,ids).commit();
                        }
                    }
                }, bluetoothUtils);
                mScanner.scanLeDevice(-1, true);
            }
        }
    }



    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
