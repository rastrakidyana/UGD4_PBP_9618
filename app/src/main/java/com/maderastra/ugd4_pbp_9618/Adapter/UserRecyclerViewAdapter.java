package com.maderastra.ugd4_pbp_9618.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.maderastra.ugd4_pbp_9618.Model.User;
import com.maderastra.ugd4_pbp_9618.R;
import com.maderastra.ugd4_pbp_9618.UpdateFragment;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    private User user;
    private Context context;
    private List<User> userList;
    private List<User> ListArray = new ArrayList<>();

    public UserRecyclerViewAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        ListArray.addAll(userList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        user = userList.get(position);
        holder.tvNumber.setText(Integer.toString(user.getNumberU()));
        holder.tvName.setText(user.getNameU());
        holder.tvAge.setText(Integer.toString(user.getAgeU()) + " " + "years old");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvNumber, tvAge;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.number_user);
            tvName = itemView.findViewById(R.id.name_user);
            tvAge = itemView.findViewById(R.id.age_user);
            tvName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            User user = userList.get(getAdapterPosition());
            Bundle data = new Bundle();
            data.putSerializable("user", user);
            UpdateFragment updateFragment = new UpdateFragment();
            updateFragment.setArguments(data);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, updateFragment)
                    .commit();
        }
    }

    public Filter getFilter() {
        return filterSearch;
    }

    private Filter filterSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            final List<User> filterList = new ArrayList<>();

            if(charSequence==null || charSequence.length()==0) {
                filterList.addAll(ListArray);
            }else{
                String pattern = charSequence.toString().toLowerCase().trim();
                for(User user : ListArray) {
                    if(user.getNameU().toLowerCase().contains(pattern)) {
                        filterList.add(user);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userList.clear();
            userList.addAll((List<User>)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
