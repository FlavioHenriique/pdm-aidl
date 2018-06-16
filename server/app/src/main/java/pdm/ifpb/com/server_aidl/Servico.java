package pdm.ifpb.com.server_aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Servico extends Service {



    public Servico() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
