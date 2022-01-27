package com.mobiapps360.LearnClockTime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class XyzActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xyz);
        RecyclerView recycleViewXyz = findViewById(R.id.recycleViewXyz);
        XyzModel[] xyzModelList = new XyzModel[]{
                new XyzModel("Gardian if Galaxies"),
                new XyzModel("End game"),
                new XyzModel("Captain America")
        };
        if (recycleViewXyz == null) {
            System.out.println("Object is Null");
        }
        ClockLearnAdapterActivity  clockLearnAdapterActivity = new ClockLearnAdapterActivity(xyzModelList);
        recycleViewXyz.setHasFixedSize(true);
        recycleViewXyz.setLayoutManager(new LinearLayoutManager(this));
        recycleViewXyz.setAdapter(clockLearnAdapterActivity);
    }
}
