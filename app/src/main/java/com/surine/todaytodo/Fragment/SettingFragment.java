package com.surine.todaytodo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.surine.todaytodo.R;
import com.surine.todaytodo.SettingActivity;

/**
 * Created by surine on 2017/6/24.
 */

public class SettingFragment extends PreferenceFragment{
    private CheckBoxPreference cbox;
    private Preference about;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        getActivity().setTitle("设置");
        findview();   //initview
        setLinsener();   //setlinstener
    }

    private void setLinsener() {
        cbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //EventBus.getDefault().post(
                  //      new SimpleMessage(2, "update",0));
                Toast.makeText(getActivity(),"本宝宝正在考虑要不要出生……",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("about",true);
                startActivity(intent);
                return true;
            }
        });
    }

    private void findview() {
        cbox = (CheckBoxPreference) findPreference("muti_view");
        about = findPreference("about");
    }

}
