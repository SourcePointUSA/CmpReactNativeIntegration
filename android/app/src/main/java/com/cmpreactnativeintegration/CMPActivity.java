package com.cmpreactnativeintegration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.sourcepoint.cmplibrary.NativeMessageController;
import com.sourcepoint.cmplibrary.SpClient;
import com.sourcepoint.cmplibrary.SpConsentLib;
import com.sourcepoint.cmplibrary.core.nativemessage.MessageStructure;
import com.sourcepoint.cmplibrary.creation.FactoryKt;
import com.sourcepoint.cmplibrary.creation.SpConfigDataBuilder;
import com.sourcepoint.cmplibrary.data.network.util.CampaignsEnv;
import com.sourcepoint.cmplibrary.exception.CampaignType;
import com.sourcepoint.cmplibrary.model.ConsentAction;
import com.sourcepoint.cmplibrary.model.MessageLanguage;
import com.sourcepoint.cmplibrary.model.PMTab;
import com.sourcepoint.cmplibrary.model.exposed.SPConsents;
import com.sourcepoint.cmplibrary.model.exposed.SpCampaign;
import com.sourcepoint.cmplibrary.model.exposed.SpConfig;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Collections;

public class CMPActivity extends Activity {

    final private SpConfig mSpConfig = new SpConfigDataBuilder()
            .addAccountId(22)
            .addPropertyName("mobile.multicampaign.demo")
            .addMessageLanguage(MessageLanguage.ENGLISH)
            .addCampaignsEnv(CampaignsEnv.PUBLIC)
            .addCampaign(new SpCampaign(CampaignType.GDPR, Collections.emptyList()))
            .addCampaign(CampaignType.GDPR)
            .addCampaign(CampaignType.CCPA)
            .build();

    private SpConsentLib mSpConsentLib = null;
    private View mSpinner = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cmp_activity);

        mSpinner = findViewById(R.id.spinner);

        mSpConsentLib = FactoryKt.makeConsentLib(mSpConfig, this, new LocalClient());

    }

    @Override
    protected void onResume() {
        super.onResume();
        String type = getIntent().getStringExtra("type");
        String pmId = getIntent().getStringExtra("pmId");
        String pCampaignType = getIntent().getStringExtra("campaignType");
        pCampaignType = pCampaignType != null ? pCampaignType : "GDPR";
        CampaignType ct = CampaignType.valueOf(pCampaignType);

        if(type != null && type.equals("pm")){
            mSpConsentLib.loadPrivacyManager(pmId, PMTab.DEFAULT, ct);
        }else {
            mSpConsentLib.loadMessage();
        }
    }

    public void disposeCmpLib(){
        if (mSpConsentLib != null) {
            mSpConsentLib.dispose();
            mSpConsentLib = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposeCmpLib();
    }

    @Override
    public void onBackPressed() {

    }

    class LocalClient implements SpClient {

        @Override
        public void onSpFinished(@NotNull SPConsents sPConsents) {
            finish();
            // add some nice effect in/out
        }

        @Override
        public void onUIFinished(View view) {
            if (mSpConsentLib != null) {
                mSpConsentLib.removeView(view);
            }
        }

        @Override
        public void onUIReady(View view) {
            if (mSpConsentLib != null) {
                mSpConsentLib.showView(view);
            }
            if (mSpinner != null) {
                mSpinner.setVisibility(View.GONE);
            }
        }

        @Override
        public void onError(Throwable error) {
            disposeCmpLib();
            finish();
        }

        @NotNull
        @Override
        public ConsentAction onAction(@NotNull View view, @NotNull ConsentAction consentAction) {
            return consentAction;
        }

        @Override
        public void onNativeMessageReady(@NotNull MessageStructure messageStructure, @NotNull NativeMessageController nativeMessageController) { }

        @Override
        public void onMessageReady(JSONObject message) {}

        @Override
        public void onNoIntentActivitiesFound(@NotNull String url) { }

        @Override
        public void onConsentReady(SPConsents consents) { }
    }
}
