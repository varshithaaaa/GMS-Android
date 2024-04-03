package com.example.growwise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmployeesCardAdapter extends RecyclerView.Adapter<EmployeesCardAdapter.ViewHolder>{
    private final Context context;
    private static ArrayList<User> courseModelArrayList;
    private DBHelper db;
    private String userDesignation;
    private int userId;
    // Constructor
    public EmployeesCardAdapter(Context context, ArrayList<User> courseModelArrayList, String loggedInUserDesignation, int loggedInUserId) {
        this.context = context;
        EmployeesCardAdapter.courseModelArrayList = courseModelArrayList;
        this.userDesignation = loggedInUserDesignation;
        this.userId = loggedInUserId;
    }

    @NonNull
    @Override
    public EmployeesCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_employee_card, parent, false);
        return new EmployeesCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesCardAdapter.ViewHolder holder, int position) {


        SharedPreferences preferences = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "Manager");
        db  = new DBHelper(context);
        User model = courseModelArrayList.get(position);
        if(model.isActive()) {
            holder.deactivateBtn.setText("Active");
            holder.deactivateBtn.setTextColor(ContextCompat.getColor(context, R.color.activeBtnText));
        } else {
            holder.deactivateBtn.setText("Deactive");
            holder.deactivateBtn.setTextColor(ContextCompat.getColor(context, R.color.deactivateBtnText));
        }
        holder.nameTV.setText("Name: "+model.getUserName());
        holder.mobileTV.setText("Ph No: "+model.getPhoneNumber());
        holder.idTVCardDOB.setText("DOB: "+model.getDob());
        if(userDesignation.equalsIgnoreCase("Admin")) {
            holder.idTVCardRole.setText("Role: " + model.getDesignation());
        } else {
            String locationName = db.getLocationNameByUserId(userId, userDesignation);
            holder.idTVCardRole.setText("Location: " + locationName);
            holder.deactivateBtn.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTV;
        private final TextView mobileTV;
        private final TextView idTVCardDOB, idTVCardRole;
        Button deactivateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVCardName);
            mobileTV = itemView.findViewById(R.id.idTVCardMobile);
            idTVCardDOB = itemView.findViewById(R.id.idTVCardDOB);
            idTVCardRole = itemView.findViewById(R.id.idTVCardRole);
            deactivateBtn = itemView.findViewById(R.id.deactivateEmpBtn);

            deactivateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    User userToUpdate = courseModelArrayList.get(position);
                    if (position != RecyclerView.NO_POSITION) {
                        boolean updated = db.toggleUserActiveStatus(userToUpdate.getUserID());
                        if (updated) {
                            String t = deactivateBtn.getText().toString();
                            if (t.equalsIgnoreCase("deactive")) {
                                deactivateBtn.setText("Active");
                                deactivateBtn.setTextColor(ContextCompat.getColor(context, R.color.activeBtnText));
                            } else {
                                deactivateBtn.setText("Deactive");
                                deactivateBtn.setTextColor(ContextCompat.getColor(context, R.color.deactivateBtnText));
                            }
                            Toast.makeText(context, deactivateBtn.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
