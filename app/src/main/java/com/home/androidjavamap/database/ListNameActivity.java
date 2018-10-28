package com.home.androidjavamap.database;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.home.androidjavamap.R;

import java.util.List;

public class ListNameActivity extends AppCompatActivity implements View.OnClickListener {

    private NameViewModel viewModel;
    TextView txvNames;
    EditText etxName;
    Button btnInsert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        viewModel = ViewModelProviders.of(this).get(NameViewModel.class);
        txvNames = findViewById(R.id.txv_names);
        btnInsert = findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(this);
        etxName = findViewById(R.id.etx_name);

        setupObservers();

    }

    private void setupObservers() {

        viewModel.getNames().observe(this, new Observer<List<Name>>() {
            @Override
            public void onChanged(@Nullable List<Name> names) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Name n : names) {
                    txvNames.setText(stringBuilder.append(n.getName() + "\n"));
                }
                viewModel.getNames().removeObserver(this);
            }
        });

        viewModel.getLastName().observe(this, new Observer<Name>() {
            @Override
            public void onChanged(@Nullable Name name) {
                txvNames.append(name.getName() + "\n");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Name name = new Name();
        name.setName(etxName.getText().toString());
        viewModel.addName(name);
    }
}
