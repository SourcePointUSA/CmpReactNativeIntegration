package com.cmpreactnativeintegration;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.sourcepoint.cmplibrary.exception.CampaignType;
import com.sourcepoint.cmplibrary.util.SpUtils;

public class SpModule extends ReactContextBaseJavaModule {

    SpModule(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return "SpModule";
    }

    @ReactMethod
    public void clearData() {
        SpUtils.clearAllData(getCurrentActivity());
    }

    @ReactMethod
    public void showGdprPm() {
        Intent intent = new Intent(getCurrentActivity(), CMPActivity.class);
        intent.putExtra("type", "pm");
        intent.putExtra("pmId", "488393");
        intent.putExtra("campaignType", CampaignType.GDPR.name());
        getCurrentActivity().startActivity(intent);
    }

    @ReactMethod
    public void showCcpaPm() {
        Intent intent = new Intent(getCurrentActivity(), CMPActivity.class);
        intent.putExtra("type", "pm");
        intent.putExtra("pmId", "509688");
        intent.putExtra("campaignType", CampaignType.CCPA.name());
        getCurrentActivity().startActivity(intent);
    }
}