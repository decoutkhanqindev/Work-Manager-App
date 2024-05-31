package com.example.workmanagerapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

public class Worker extends androidx.work.Worker {
    public Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        for(int i = 0; i < 1000; i++){
            Log.e("TAGY", "Count is: " + i);
        }
        return Result.success();
    }
}
