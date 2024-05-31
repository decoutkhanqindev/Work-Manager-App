package com.example.workmanagerapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;

public class Worker extends androidx.work.Worker {
    public Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // getting data from inputData
        Data gettingData = getInputData();
        int countingLimit = gettingData.getInt("max_limit", 0);


        for (int i = 0; i < countingLimit; i++) {
            Log.e("TAGY", "Count is: " + i);
        }

        // sending data and done notification
        Data dataToSend = new Data.Builder().putString("msg", "Task done successfully").build();

        return Result.success(dataToSend);
    }
}
