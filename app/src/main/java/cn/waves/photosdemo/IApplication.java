package cn.waves.photosdemo;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by iclick on 2018/1/10.
 */

public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
