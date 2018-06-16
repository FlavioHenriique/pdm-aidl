package pdm.ifpb.com.pdm_started_service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
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

public class CEP_Service extends Service {

    public CEP_Service() {
    }

    private final AidlInterface.Stub binder =
            new AidlInterface.Stub() {
        @Override
        public String metodo() throws RemoteException {
            String cep = "";
            cep = intent.getStringExtra("cep");
            Message msg = new Message();
            String link = "https://viacep.com.br/ws/".concat(cep).concat("/json");
            URL url = null;
            try {
                url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuffer buffer = new StringBuffer();
                String linha = "";


                String conteudo = "";
                while ((linha = reader.readLine()) != null){
                    conteudo += linha;
                }
                msg.obj=conteudo;
                MainActivity.handler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return START_STICKY;

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("binder");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

    }
}
