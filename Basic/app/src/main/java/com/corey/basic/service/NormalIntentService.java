package com.corey.basic.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by sycao on 2018/5/14.
 * IntentService
 */

public class NormalIntentService extends IntentService {

  private static final String intentName = NormalIntentService.class.getSimpleName();

  public NormalIntentService() {
    super(intentName);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

  }
}
