package com.kinotor.tiar.kinotor.ui;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import com.kinotor.tiar.kinotor.R;
import com.kinotor.tiar.kinotor.items.Statics;
import com.kinotor.tiar.kinotor.updater.Update;
import com.kinotor.tiar.kinotor.utils.AppCompatPreferenceActivity;

import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setHomeAsUpIndicator(R.drawable.ic_check);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || InfoPreferenceFragment.class.getName().equals(fragmentName)
                || SettingsPreferenceFragment.class.getName().equals(fragmentName)
                || DomensPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class InfoPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_information);

            Preference btn_update = findPreference("update");
            btn_update.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Update update = new Update(getActivity());
                    update.execute();
                    return true;
                }
            });

            Preference btn_email = findPreference("email");
            btn_email.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KinoTor");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tiarait.dev@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    emailIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailIntent, "Выберите email клиент :"));
                    return true;
                }
            });

            final Preference btn_pb24 = findPreference("pb24");
            btn_pb24.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    copiedText(String.valueOf(btn_pb24.getTitle()));
                    return true;
                }
            });

            final Preference btn_qiwi = findPreference("qiwi");
            btn_qiwi.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    copiedText(String.valueOf(btn_qiwi.getTitle()));
                    return true;
                }
            });

            final Preference btn_wmr = findPreference("wmr");
            btn_wmr.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    copiedText(String.valueOf(btn_wmr.getTitle()));
                    return true;
                }
            });

            final Preference btn_wmu = findPreference("wmu");
            btn_wmu.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    copiedText(String.valueOf(btn_wmu.getTitle()));
                    return true;
                }
            });

            final Preference btn_wmz = findPreference("wmz");
            btn_wmz.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    copiedText(String.valueOf(btn_wmz.getTitle()));
                    return true;
                }
            });

            setHasOptionsMenu(true);
        }

        private void copiedText (String copiedText) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(copiedText);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("TAG", copiedText);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getActivity().getBaseContext(), "Кошелек скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SettingsPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
            setHasOptionsMenu(true);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DomensPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_domen);
            setHasOptionsMenu(true);

            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getActivity());

            EditTextPreference kosharaUrl = (EditTextPreference) findPreference("koshara_furl");
            kosharaUrl.setSummary(preference.getString("koshara_furl", Statics.KOSHARA_URL));
            EditTextPreference coldfilmUrl = (EditTextPreference) findPreference("coldfilm_furl");
            coldfilmUrl.setSummary(preference.getString("coldfilm_furl", Statics.COLDFILM_URL));
            EditTextPreference animevostUrl = (EditTextPreference) findPreference("animevost_furl");
            animevostUrl.setSummary(preference.getString("animevost_furl", Statics.ANIMEVOST_URL));
            EditTextPreference amcetUrl = (EditTextPreference) findPreference("amcet_furl");
            amcetUrl.setSummary(preference.getString("amcet_furl", Statics.AMCET_URL));
            EditTextPreference kinofsUrl = (EditTextPreference) findPreference("kinofs_furl");
            kinofsUrl.setSummary(preference.getString("kinofs_furl", Statics.KINOFS_URL));

            EditTextPreference kinoshaUrl = (EditTextPreference) findPreference("kinosha_furl");
            kinoshaUrl.setSummary(preference.getString("kinosha_furl", Statics.KINOSHA_URL));
            EditTextPreference kinomaniaUrl = (EditTextPreference) findPreference("kinomania_furl");
            kinomaniaUrl.setSummary(preference.getString("kinomania_furl", Statics.KINOMANIA_URL));

            EditTextPreference freerutorUrl = (EditTextPreference) findPreference("freerutor_furl");
            freerutorUrl.setSummary(preference.getString("freerutor_furl", Statics.FREERUTOR_URL));
            EditTextPreference zooqleUrl = (EditTextPreference) findPreference("zooqle_furl");
            zooqleUrl.setSummary(preference.getString("zooqle_furl", Statics.ZOOQLE_URL));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
