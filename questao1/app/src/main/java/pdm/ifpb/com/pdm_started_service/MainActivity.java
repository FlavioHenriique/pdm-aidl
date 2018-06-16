package pdm.ifpb.com.pdm_started_service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static Handler handler;

    private AidlInterface aidl;
    private ServiceConnection conn  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidl = AidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new MyHandler() ;
        Button bt = findViewById(R.id.botao);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                String cep = "";

                EditText et = findViewById(R.id.cep);
                cep = et.getText().toString();
                System.out.println("O cep é "+cep);
                Intent intent = new Intent(MainActivity.this, CEP_Service.class);
                intent.putExtra("cep",cep);
                startService(intent);
            }
            */
               Intent intent = new Intent(MainActivity.this,CEP_Service.class);
               bindService(intent,conn,BIND_AUTO_CREATE);
                try {
                    System.out.println(aidl.metodo());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


        });
    }
    private class MyHandler extends  Handler{

        public MyHandler() {
            super();
        }

        @Override
        public void handleMessage(Message msg) {

            EditText logradouro = findViewById(R.id.logradouro);
        EditText cidade = findViewById(R.id.cidade);
        EditText complemento = findViewById(R.id.complemento);
        EditText bairro = findViewById(R.id.bairro);
        EditText estado = findViewById(R.id.estado);

            try {
                JSONObject json = new JSONObject((String) msg.obj);
                logradouro.setText(json.getString("logradouro"));
                cidade.setText(json.getString("localidade"));
                complemento.setText(json.getString("complemento"));
                bairro.setText(json.getString("bairro"));
                estado.setText(json.getString("uf"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
