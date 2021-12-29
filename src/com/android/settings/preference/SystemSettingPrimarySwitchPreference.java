package com.android.settings.preference;

import android.content.Context;
import android.util.AttributeSet;

import org.proton.support.preference.SystemSettingsStore;

import com.android.settings.widget.PrimarySwitchPreference;

public class SystemSettingPrimarySwitchPreference extends PrimarySwitchPreference {

    public SystemSettingPrimarySwitchPreference(Context context, AttributeSet attrs,
            int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setPreferenceDataStore(new SystemSettingsStore(context.getContentResolver()));
    }

    public SystemSettingPrimarySwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPreferenceDataStore(new SystemSettingsStore(context.getContentResolver()));
    }

    public SystemSettingPrimarySwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPreferenceDataStore(new SystemSettingsStore(context.getContentResolver()));
    }

    public SystemSettingPrimarySwitchPreference(Context context) {
        super(context);
        setPreferenceDataStore(new SystemSettingsStore(context.getContentResolver()));
    }
}
