package com.example.prog8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ICarService cService;
    EditText prinamt, downamt, intrate, loanterm;
    Button calculateemi;
    TextView txtresult;

    private ServiceConnection sconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            cService = ICarService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            cService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prinamt = findViewById(R.id.editTextpa);
        downamt = findViewById(R.id.editTextdp);
        intrate = findViewById(R.id.editTextir);
        loanterm = findViewById(R.id.editTextlt);
        calculateemi = findViewById(R.id.button);
        txtresult = findViewById(R.id.textViewResult);
        calculateemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float principalamount = Float.parseFloat(prinamt.getText().toString());
                float downpayment = Float.parseFloat(downamt.getText().toString());
                float interestrate = Float.parseFloat(intrate.getText().toString());
                int lnterm = Integer.parseInt(loanterm.getText().toString());
                try {
                    float result = cService.carcal(principalamount, downpayment, interestrate, lnterm);
                    txtresult.setText("Monthly EMI in Rs is: " + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent it = new Intent(this, CarService.class);
        bindService(it, sconnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(sconnection);
    }
}
