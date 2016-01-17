package net.classconnect.classconnect;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollapsingToolbarFragment extends Fragment {



    private List<CardItemModel> cardItems = new ArrayList<>(30);
    private CourseListActivity appCompatActivity;
    private Toolbar toolbar;
    private RecyclerView recyclerView;


    public CollapsingToolbarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        appCompatActivity = (CourseListActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_collapsing_toolbar, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);

        setupToolbar();

        ((CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar)).setTitle(
                "Matching Classes");

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        setupRecyclerView();

        return view;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        appCompatActivity.setupNavigationDrawer(toolbar);
//    }

    private void setupToolbar(){
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        recyclerView.setHasFixedSize(true);
        initializeCardItemList();
        RecyclerAdapterToolbar adapter = new RecyclerAdapterToolbar(cardItems);
        adapter.setActivity((CourseListActivity) getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void initializeCardItemList(){
        CardItemModel cardItemModel;
//        String[] cardTitles = getResources().getStringArray(R.array.card_titles);
//        String[] cardContents = getResources().getStringArray(R.array.card_contents);
//        final int length = cardTitles.length;
        Map<String, List<String>> map =  CourseListActivity.LcourseAttendeesMap;
        Map<String, Boolean> booleanMap =  CourseListActivity.Lmap;

        for(Map.Entry<String,List<String>> entry : map.entrySet()){
            cardItemModel = new CardItemModel(entry.getKey(), entry.getValue(), "id", booleanMap.get(entry.getKey()));
            cardItems.add(cardItemModel);
        }
    }

}
