package com.jeannypr.scientificstudy.Transport.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeannypr.scientificstudy.R;
import com.jeannypr.scientificstudy.Transport.activity.ClassDetailActivity;
import com.jeannypr.scientificstudy.Transport.model.ClassSummaryModel;

import java.util.List;

public class ClassSummaryAdapter extends RecyclerView.Adapter {


    Context mContext;
    private List<ClassSummaryModel> classSummaryModels;
    //OnItemClickListener listener;

    public ClassSummaryAdapter(Context context, List<ClassSummaryModel> routes) {
        super();
        mContext = context;
        classSummaryModels = routes;

    }

    @Override
    public int getItemCount() {
        return classSummaryModels.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;


        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_route_list, parent, false);
        return new ClassSummaryAdapter.MyViewHolder(view);


    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ClassSummaryModel route = classSummaryModels.get(position);
        ((MyViewHolder) holder).bind(route);

        ((MyViewHolder) holder).rowRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchToDetail(route);
            }
        });
        ((MyViewHolder) holder).routeDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchToDetail(route);
            }
        });
    }

    private void SwitchToDetail(ClassSummaryModel route) {
        //pass route name in intent
        Intent intent = new Intent(mContext, ClassDetailActivity.class);
        intent.putExtra("className", route.ClassName);
        intent.putExtra("classId", route.Id);
        mContext.startActivity(intent);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView totalStudents, routeName;
        ImageView routeDetailsBtn;
        ConstraintLayout rowRoute;

        MyViewHolder(View itemView) {
            super(itemView);

            totalStudents = itemView.findViewById(R.id.totalStudents);
            routeName = itemView.findViewById(R.id.routeName);
            rowRoute = itemView.findViewById(R.id.rowRoute);
            routeDetailsBtn = itemView.findViewById(R.id.routeDetailsBtn);
        }

        void bind(final ClassSummaryModel route) {
            totalStudents.setText(route.TotalStudents != null ? route.TotalStudents : "");
            routeName.setText(route.ClassName != null ? route.ClassName.substring(0, 1).toUpperCase() + route.ClassName.substring(1).toLowerCase() : "");

        }
    }
}


