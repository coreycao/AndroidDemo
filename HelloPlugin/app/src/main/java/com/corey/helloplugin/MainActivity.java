package com.corey.helloplugin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.corey.helloplugin.entity.ApkItem;
import com.morgoo.droidplugin.pm.PluginManager;
import com.morgoo.helper.Log;
import com.morgoo.helper.compat.PackageManagerCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.morgoo.helper.compat.PackageManagerCompat.INSTALL_FAILED_NOT_SUPPORT_ABI;
import static com.morgoo.helper.compat.PackageManagerCompat.INSTALL_SUCCEEDED;

/**
 * Created by sycao on 2017/9/13.
 * 主界面
 */

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    Handler handler;

    ProgressDialog progressDialog;
    Button btnInstall;
    Button btnLaunch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Activity activity = this;

        handler = new Handler();

        progressDialog = new ProgressDialog(this);
        btnInstall = (Button) findViewById(R.id.btn_main_install);
        btnInstall.setVisibility(View.GONE);

        btnLaunch = (Button) findViewById(R.id.btn_main_launch);
        btnLaunch.setVisibility(View.GONE);

        btnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_main_install) {
                    Toast.makeText(getApplicationContext(), "installing...", Toast.LENGTH_LONG).show();
                    new Thread("installPlugin") {
                        @Override
                        public void run() {
                            installPlugin(btnInstall.getTag().toString());
                        }
                    }.start();
                }
            }
        });

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

        findViewById(R.id.btn_main_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_main_launch) {
                    launchPlugin("com.ucarinc.uhome");
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
        progressDialog.show();
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
                        for (final File apk : files1) {
                            if (apk.exists() && apk.getPath().toLowerCase().endsWith("apk")) {
                                PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(apk.getPath(), 0);
                                android.util.Log.d("MainActivity", packageInfo.packageName);
                                if (packageInfo.packageName.equals(packageName)) {
                                    progressDialog.dismiss();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnInstall.setVisibility(View.VISIBLE);
                                            btnInstall.setTag(apk.getPath());
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 插件是否已经安装
     */
    void isPluginInstalled(ApkItem apkItem) {
        try {
            if (PluginManager.getInstance().getPackageInfo(apkItem.packageInfo.packageName, 0) != null) {
                android.util.Log.d(TAG, "插件已经安装");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
            final int re = PluginManager.getInstance().installPackage(filepath, 0);
//            item.installing = false;

            handler.post(new Runnable() {
                @Override
                public void run() {
                    switch (re) {
                        case PluginManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION:
                            Toast.makeText(getApplicationContext(), "安装失败，文件请求的权限太多", Toast.LENGTH_SHORT).show();
                            break;
                        case INSTALL_FAILED_NOT_SUPPORT_ABI:
                            Toast.makeText(getApplicationContext(), "宿主不支持插件的abi环境，可能宿主运行时为64位，但插件只支持32位", Toast.LENGTH_SHORT).show();
                            break;
                        case INSTALL_SUCCEEDED:
                            Toast.makeText(getApplicationContext(), "安装完成", Toast.LENGTH_SHORT).show();
                            break;
                        case PackageManagerCompat.INSTALL_FAILED_ALREADY_EXISTS:
                            Toast.makeText(getApplicationContext(), "应用已经安装", Toast.LENGTH_SHORT).show();
                            btnLaunch.setVisibility(View.VISIBLE);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), String.valueOf(re), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新插件
     * PackageManagerCompat.INSTALL_FAILED_ALREADY_EXISTS
     * 需要先卸载再更新
     */
    void installPlugin(String filepath, int flags) {
        try {
            PluginManager.getInstance().installPackage(filepath, flags);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插件登录角色校验
     */
    void pluginUserValidate() {

    }

    /**
     * 启动插件
     * intent跳转 - 登录处理 - 角色判断 - 角色选择 - 界面跳转
     */
    void launchPlugin(String packageName) {
        PackageManager pm = getPackageManager();
//        Intent intent = pm.getLaunchIntentForPackage(item.packageInfo.packageName);
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Log.i("DroidPlugin", "start " + item.packageInfo.packageName + "@" + intent);
            startActivity(intent);
        } else {
//            Log.e("DroidPlugin", "pm " + pm.toString() + " no find intent " + item.packageInfo.packageName);
            Log.e("DroidPlugin", "pm " + pm.toString() + " no find intent " + packageName);
        }
    }
}
