package com.example.admin.w4weekendamazon;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.w4weekendamazon.amazonmodel.AmazonResponse;
import com.example.admin.w4weekendamazon.dummy.DummyContent;
import com.example.admin.w4weekendamazon.dummy.DummyContent.DummyItem;

import java.io.Serializable;
import java.util.List;

public class AmazonResponseFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_LIST = "list";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<AmazonResponse> amazonResponses;
    private OnListFragmentInteractionListener mListener;

    public AmazonResponseFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AmazonResponseFragment newInstance(int columnCount, List<AmazonResponse> amazonResponses) {
        AmazonResponseFragment fragment = new AmazonResponseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(ARG_LIST, (Serializable) amazonResponses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            amazonResponses = (List<AmazonResponse>) getArguments().getSerializable(ARG_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amazonresponse_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new AmazonResponseRecyclerViewAdapter(amazonResponses, mListener, getContext()));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(AmazonResponse item);
    }
}
