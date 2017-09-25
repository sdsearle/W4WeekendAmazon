package com.example.admin.w4weekendamazon;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.w4weekendamazon.AmazonResponseFragment.OnListFragmentInteractionListener;
import com.example.admin.w4weekendamazon.amazonmodel.AmazonResponse;
import com.example.admin.w4weekendamazon.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AmazonResponseRecyclerViewAdapter extends RecyclerView.Adapter<AmazonResponseRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "AdapterTag";
    private final List<AmazonResponse> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context context;

    public AmazonResponseRecyclerViewAdapter(List<AmazonResponse> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_amazonresponse, parent, false);
        Log.d(TAG, "onCreateViewHolder: ");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mAuthorView.setText(mValues.get(position).getAuthor());
        Glide.with(context).load(mValues.get(position).getImageURL()).into(holder.mBookImg);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mAuthorView;
        public AmazonResponse mItem;
        public ImageView mBookImg;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.tvTitle);
            mAuthorView = view.findViewById(R.id.tvAuthor);
            mBookImg = view.findViewById(R.id.ivBookimg);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAuthorView.getText() + "'";
        }
    }
}
