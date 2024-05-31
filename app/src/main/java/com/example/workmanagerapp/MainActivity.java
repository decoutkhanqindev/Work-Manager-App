package com.example.workmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn = findViewById(R.id.runWM);

        // data
        Data inputData = new Data.Builder().putInt("max_limit", 8888).build();

        // add constraints to wr
        Constraints constraints = new Constraints.Builder().setRequiresCharging(true).build();

        // making use of worker
        WorkRequest wr = new OneTimeWorkRequest.Builder(Worker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        // enqueue the request with WorkManager
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getApplicationContext()).enqueue(wr);
            }
        });

        // monitoring the status of work manager
        WorkManager.getInstance(getApplicationContext())
                .getWorkInfoByIdLiveData(wr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {
                            Toast.makeText(MainActivity.this,
                                    "Status " + workInfo.getState().name(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (Objects.requireNonNull(workInfo).getState().isFinished()) {
                            Data gettingData = workInfo.getOutputData();
                            Toast.makeText(MainActivity.this,
                                    " " + gettingData.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}