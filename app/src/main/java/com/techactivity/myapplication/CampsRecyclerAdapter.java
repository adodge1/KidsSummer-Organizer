package com.techactivity.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techactivity.myapplication.model.Camp;

import java.util.ArrayList;

public class CampsRecyclerAdapter extends RecyclerView.Adapter<CampsRecyclerAdapter.CampsRecyclerViewHolder>{


    private ArrayList<Camp> mCamps;
    private final CampsRecyclerAdapter.CampsRecyclerAdapterOnClickHandler mClickHandler;

    public interface CampsRecyclerAdapterOnClickHandler {
        void onClick(Camp selectedCamp);
    }


    public CampsRecyclerAdapter(CampsRecyclerAdapter.CampsRecyclerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public class CampsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final Button mCampButton;

        public CampsRecyclerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mCampButton = view.findViewById(R.id.btn_camp);
            //Since this is a Button we need to set this!
            mCampButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the item clicked
            Camp myCampSelected = mCamps.get(getAdapterPosition());
            // Then you can do any actions on it, for techactivity:
            mClickHandler.onClick(myCampSelected);
        }


    }


    /* The adapter provides access to our data. It also provides the views for the displayed items.
    We create our custom recycler adapter by extending the RecyclerView.Adapter class.
    There are three methods that you must implement:
    onCreateViewHolder() – creates a new ViewHolder containing our image
    onBindViewHolder() – displays our image at the specified position in the list
    getItemCount() – gets the number of items in the adapter*/

    @Override
    public CampsRecyclerAdapter.CampsRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_camp;
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new CampsRecyclerAdapter.CampsRecyclerViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(CampsRecyclerAdapter.CampsRecyclerViewHolder holder, int position) {
        Camp child= mCamps.get(position);
        Button button = holder.mCampButton;
        button.setText(child.getCampName() +"\n"+ child.getWeekFrom());

    }


    @Override
    public int getItemCount() {
        if (null == mCamps) return 0;
        return mCamps.size();
    }

    public void setCampData (ArrayList<Camp> campData){
        mCamps = campData;
        notifyDataSetChanged();
    }






}
