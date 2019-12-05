package com.mareow.recaptchademo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.mareow.recaptchademo.Adapters.ShowAllMachineAdapter;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {

    RecyclerView searchRecyclerView;
    ArrayList<RenterMachine> allAvaialbleMaachine=new ArrayList<>();
    ShowAllMachineAdapter allMachineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        searchRecyclerView=(RecyclerView)findViewById(R.id.search_recycle);
        searchRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        searchRecyclerView.setHasFixedSize(false);
        searchRecyclerView.addItemDecoration(new SpacesItemDecoration(5));
        searchRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        allAvaialbleMaachine=(ArrayList<RenterMachine>)getIntent().getSerializableExtra("AllMachineList");

        allMachineAdapter=new ShowAllMachineAdapter(this,(List)allAvaialbleMaachine);

        onSearchRequested();
        handleIntent(getIntent());


    }



    private void handleIntent(Intent intent) {
        searchRecyclerView.setAdapter(allMachineAdapter);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
           // Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

            String userInput=query.toLowerCase();
            List<RenterMachine> newList=new ArrayList<>();

            for (RenterMachine renterMachine:allAvaialbleMaachine){
                if (renterMachine.getMachineName().contains(userInput)){
                    newList.add(renterMachine);
                }
            }

            Constants.SEARCH_RESULT=true;
            Intent intent1=new Intent(this,RenterMainActivity.class);
            intent1.putExtra("newListDate",(ArrayList)newList);
            startActivity(intent1);

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
