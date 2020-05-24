package geekbrains.ru.lesson4retrofit.ui.detailed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import geekbrains.ru.lesson4retrofit.R;
import geekbrains.ru.lesson4retrofit.model.UserRepo;
import geekbrains.ru.lesson4retrofit.utility.Utility;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {
    private List<UserRepo> repoList;
    private int currentPosition = -1;
    private Context context;

    public ReposAdapter(List<UserRepo> repoList) {
        this.repoList = repoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        view = LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false);
        return new ReposAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserRepo repo=repoList.get(position);
        SetOnClickHolder(holder, position);
        holder.peroUrlTW.setText(repo.getUrl());
        Utility.repaintView(context, holder, position, currentPosition);
    }

    private void SetOnClickHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
          String url=holder.peroUrlTW.getText().toString();
            Utility.openUrl(url, context);
        });
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView peroUrlTW;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            peroUrlTW=itemView.findViewById(R.id.itemTextTW);
        }
    }
}
