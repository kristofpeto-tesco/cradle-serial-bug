package com.tesco.says;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.personalshopper.CradleException;
import com.symbol.emdk.personalshopper.CradleInfo;
import com.symbol.emdk.personalshopper.PersonalShopper;

public class MainActivity extends AppCompatActivity implements EMDKManager.EMDKListener {


    private EMDKManager emdkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button readCradleInfoButton = findViewById(R.id.readCradleInfo);
        readCradleInfoButton.setOnClickListener(v -> {
            this.<TextView>findViewById(R.id.cradleInfo).setText(readCradleInfo());
        });

        EMDKManager.getEMDKManager(getApplicationContext(), this);

    }

    private String readCradleInfo()  {
        try {
            CradleInfo cradleInfo = ((PersonalShopper) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.PERSONALSHOPPER))
                    .cradle.getCradleInfo();
            return formatCradleInfo(cradleInfo);
        } catch (CradleException e) {
            return "Could not read serial info: " + e.getMessage();
        }
    }

    private static String formatCradleInfo(CradleInfo cradleInfo) {
        return "HardwareID:" + cradleInfo.getHardwareID() + System.getProperty("line.separator") +
                "SerialNumber:" + cradleInfo.getSerialNumber() + System.getProperty("line.separator") +
                "FirmwareVersion:" + cradleInfo.getFirmwareVersion() + System.getProperty("line.separator") +
                "PartNumber:" + cradleInfo.getPartNumber();
    }

    @Override
    public void onOpened(EMDKManager emdkManager) {
        this.emdkManager = emdkManager;
    }

    @Override
    public void onClosed() {

    }
}