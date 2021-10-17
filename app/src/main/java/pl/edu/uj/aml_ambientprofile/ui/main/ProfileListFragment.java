package pl.edu.uj.aml_ambientprofile.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.uj.aml_ambientprofile.R;
import pl.edu.uj.aml_ambientprofile.databinding.ProfileListFragmentActivityBinding;
import pl.edu.uj.aml_ambientprofile.datasource.Profile;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileListFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "ProfileListFragment";

    private PageViewModel pageViewModel;
    private ProfileListFragmentActivityBinding binding;


    protected RecyclerView mRecyclerView;
    protected ProfileListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected Profile[] mDataset;

    private static final int DATASET_COUNT = 10;



    public static ProfileListFragment newInstance(int index) {
        ProfileListFragment fragment = new ProfileListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        initDataset();
        Log.d(TAG, "Creating fragment");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = ProfileListFragmentActivityBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();


        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // Action on fragment change

            }
        });

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();
        mAdapter = new ProfileListAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)


        return rootView;
    }


    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }


        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void initDataset() {
        mDataset = new Profile[]{new Profile(R.drawable.uj, "UJ"),
                new Profile(R.drawable.day, "Weekend Day"),
                new Profile(R.drawable.night, "Weekend Night"),
                new Profile(R.drawable.day, "Workday Day")};
    }
}