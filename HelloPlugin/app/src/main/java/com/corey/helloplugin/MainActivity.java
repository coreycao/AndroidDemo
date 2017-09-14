package com.corey.helloplugin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.corey.helloplugin.entity.ApkItem;
import com.morgoo.droidplugin.pm.PluginManager;
import com.morgoo.helper.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sycao on 2017/9/13.
 * 主界面
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Activity activity = this;
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_main) {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        isPluginAPKExist("Browser", "com.ucarinc.uhome");
                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x1);
                    }
                }
            }
        });
    }

    /**
     * 根据包名判断插件APK是否存在
     * targetDictionary 目标目录
     * packageName      目标包名
     */
    void isPluginAPKExist(final String targetDictionary, final String packageName) {
        new Thread("ApkScanner") {
            @Override
            public void run() {

                File file = Environment.getExternalStorageDirectory();

                List<File> apks = new ArrayList<File>(10);
                File[] files = file.listFiles();
                if (files != null) {
                    for (File apk : files) {
                        if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
                            apks.add(apk);
                        }
                    }
                }

                file = new File(Environment.getExternalStorageDirectory(), targetDictionary);
                if (file.exists() && file.isDirectory()) {
                    File[] files1 = file.listFiles();
                    if (files1 != null) {
                        for (File apk : files1) {
                            if (apk.exists() && apk.getPath().toLowerCase().endsWith("apk")) {
                                PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(apk.getPath(), 0);
                                android.util.Log.d("MainActivity", packageInfo.packageName);
                                if (packageInfo.packageName.equals(packageName)) {

                                }
                            }
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 插件是否已经完成安装
     */
    void isPluginInstalled() {

    }

    /**
     * 检查插件是否有更新
     */
    void checkPluginUpdate() {

    }

    /**
     * 下载指定版本的插件
     */
    void downloadPlugin(String versionName, int versionCode) {

    }

    /**
     * 安装插件
     */
    void installPlugin(String filepath) {
        try {
            PluginManager.getInstance().installPackage(filepath, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新插件
     * PackageManagerCompat.INSTALL_FAILED_ALREADY_EXISTS
     */
    void installPlugin(String filepath, int flags) {
        try {
            PluginManager.getInstance().installPackage(filepath, flags);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动插件
     */
    void launchPlugin(ApkItem item) {
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(item.packageInfo.packageName);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i("DroidPlugin", "start " + item.packageInfo.packageName + "@" + intent);
            startActivity(intent);
        } else {
            Log.e("DroidPlugin", "pm " + pm.toString() + " no find intent " + item.packageInfo.packageName);
        }
    }
}
