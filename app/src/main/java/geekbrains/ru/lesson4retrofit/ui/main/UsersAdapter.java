package geekbrains.ru.lesson4retrofit.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import geekbrains.ru.lesson4retrofit.ui.detailed.DetailedActivity;
import geekbrains.ru.lesson4retrofit.R;
import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import geekbrains.ru.lesson4retrofit.utility.Utility;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<GitHubUser> userList;
    private int currentPosition = -1;
    private Context context;

    public UsersAdapter(List<GitHubUser> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       context = parent.getContext();
        View view;
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GitHubUser gitHubUser=userList.get(position);
        SetOnClickHolder(holder, position);
        holder.userNameTW.setText(gitHubUser.getLogin());
        Utility.repaintView(context, holder, position, currentPosition);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void SetOnClickHolder(@NonNull final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(view -> {
            currentPosition = position;
            notifyDataSetChanged();
            GitHubUser user=userList.get(position);
            String login=user.getLogin();
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("login", login);
            context.startActivity(intent);
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTW;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTW=itemView.findViewById(R.id.itemTextTW);
        }
    }
}
