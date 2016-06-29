package com.zacharysweigart.uacfchat.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zacharysweigart.uacfchat.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    protected FrameLayout mainContent;
    protected FloatingActionButton floatingActionButton;
    protected Toolbar toolbar;
    protected LinearLayout progressLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainContent = (FrameLayout) findViewById(R.id.main_content);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        if(!hasFab()) {
            floatingActionButton.setVisibility(View.GONE);
        }

        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
    }

    protected void setMainContent(View view) {
        mainContent.removeAllViews();
        mainContent.addView(view);
    }

    protected abstract boolean hasFab();

    protected void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    protected void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }
}
