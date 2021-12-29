package com.android.settings.sound;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;	

import com.android.settings.core.TogglePreferenceController;

import com.android.settings.preference.SystemSettingPrimarySwitchPreference;

public class TouchHapticPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {

    private static final String KEY_HAPTIC_ENABLED = Settings.System.HAPTIC_FEEDBACK_ENABLED;

    private SystemSettingPrimarySwitchPreference mPreference;
    private SettingObserver mSettingObserver;

    public TouchHapticPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mPreference = screen.findPreference(getPreferenceKey());
        mSettingObserver = new SettingObserver(mPreference);
    }

    @Override
    public boolean isChecked() {
        return Settings.System.getInt(mContext.getContentResolver(), KEY_HAPTIC_ENABLED, 1) == 1;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        return Settings.System.putInt(mContext.getContentResolver(), KEY_HAPTIC_ENABLED, isChecked ? 1 : 0);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void onStart() {
        if (mSettingObserver != null) {
            mSettingObserver.register(mContext.getContentResolver());
            mSettingObserver.onChange(false, null);
        }
    }

    @Override
    public void onStop() {
        if (mSettingObserver != null) {
            mSettingObserver.unregister(mContext.getContentResolver());
        }
    }

    private class SettingObserver extends ContentObserver {
        private final Uri mHapticEnabledUri = Settings.System.getUriFor(KEY_HAPTIC_ENABLED);

        private final Preference mPreference;

        SettingObserver(Preference preference) {
            super(new Handler());
            mPreference = preference;
        }

        public void register(ContentResolver cr) {
            cr.registerContentObserver(mHapticEnabledUri, false, this);
        }

        public void unregister(ContentResolver cr) {
            cr.unregisterContentObserver(this);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            if (uri == null || mHapticEnabledUri.equals(uri)) {
                updateState(mPreference);
            }
        }
    }
}
