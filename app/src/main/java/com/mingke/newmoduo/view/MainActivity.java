package com.mingke.newmoduo.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mingke.newmoduo.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private ModuoFragment moduoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        moduoFragment = new ModuoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.id_fragment_container, moduoFragment)
                .commit();
    }

    private void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.id_tb));
    }

}
