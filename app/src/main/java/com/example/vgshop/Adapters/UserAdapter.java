package com.example.vgshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgshop.Entities.User;
import com.example.vgshop.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.Locale;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public String role;
    public String userName;
    public String email;
    private Context context;
    private UserItemBinding userItemBinding;
    private ArrayList<User> userArrayList;

    public UserAdapter(Context context, ArrayList<User> userArrayList){
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        userItemBinding = UserItemBinding.inflate(LayoutInflater.from(context));
        return new UserViewHolder(userItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.userItemBinding.tvUserName2.setText(user.getUserName());
        holder.userItemBinding.tvEmail2.setText(user.getEmail());
        holder.userItemBinding.tvCountry2.setText(user.getCountry());
        holder.userItemBinding.tvCity2.setText(user.getCity());
        holder.userItemBinding.tvRole2.setText(user.getRole());
        holder.userItemBinding.tvShopName2.setText(user.getShopName());
        role = user.getRole().toLowerCase(Locale.ROOT);
        userName = user.getUserName().toLowerCase(Locale.ROOT);
        email = user.getEmail().toLowerCase(Locale.ROOT);
        /*if(role.equals("seller")){
            holder.userItemBinding.btnUserEdit.setVisibility(View.GONE);
        }*/
        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("users").document(user.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Data deleted",
                                        Toast.LENGTH_SHORT).show();
                                userArrayList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed to delete item",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        holder.userItemBinding.btnUserDelete.setOnClickListener(view -> {
            builder.setMessage("Are you sure to delete product?");
            builder.create().show();
        });
        holder.userItemBinding.btnUserEdit.setOnClickListener(view ->{
            Intent intent = new Intent(context, EditUser.class);
            intent.putExtra("user", user);
            context.startActivity(intent);
        });*/
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        UserItemBinding userItemBinding;

        public UserViewHolder(@NonNull UserItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.userItemBinding = itemBinding;
        }
    }
}
