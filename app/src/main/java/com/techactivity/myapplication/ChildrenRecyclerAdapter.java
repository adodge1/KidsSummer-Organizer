package com.techactivity.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techactivity.myapplication.model.Child;

import java.util.ArrayList;

public class ChildrenRecyclerAdapter extends RecyclerView.Adapter<ChildrenRecyclerAdapter.ChildrenRecyclerViewHolder>{
    private ArrayList<Child> mChildren;
    private final ChildrenRecyclerAdapter.ChildrenRecyclerAdapterOnClickHandler mClickHandler;

    public interface ChildrenRecyclerAdapterOnClickHandler {
        void onClick(Child selectedChild);
    }


    public ChildrenRecyclerAdapter(ChildrenRecyclerAdapter.ChildrenRecyclerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public class ChildrenRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final Button mChildButton;

        public ChildrenRecyclerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mChildButton = view.findViewById(R.id.btn_child);
            //Since this is a Button we need to set this!
            mChildButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the item clicked
            Child myChildSelected = mChildren.get(getAdapterPosition());
            // Then you can do any actions on it, for techactivity:
            mClickHandler.onClick(myChildSelected);
        }


    }


    /* The adapter provides access to our data. It also provides the views for the displayed items.
    We create our custom recycler adapter by extending the RecyclerView.Adapter class.
    There are three methods that you must implement:
    onCreateViewHolder() – creates a new ViewHolder containing our image
    onBindViewHolder() – displays our image at the specified position in the list
    getItemCount() – gets the number of items in the adapter*/

    @Override
    public ChildrenRecyclerAdapter.ChildrenRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_child;
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ChildrenRecyclerAdapter.ChildrenRecyclerViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(ChildrenRecyclerAdapter.ChildrenRecyclerViewHolder holder, int position) {
        Child child= mChildren.get(position);
        Button button = holder.mChildButton;
        button.setText(child.getChildName());

    }


    @Override
    public int getItemCount() {
        if (null == mChildren) return 0;
        return mChildren.size();
    }

    public void setChildData (ArrayList<Child> childData){
        mChildren = childData;
        notifyDataSetChanged();
    }


}
