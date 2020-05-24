package geekbrains.ru.lesson4retrofit.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import geekbrains.ru.lesson4retrofit.R;

public class Utility {
    public static void initReciclerViewAdapter(Activity activity, RecyclerView.Adapter adapter, RecyclerView rw) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(activity.getDrawable(R.drawable.separator));
        rw.addItemDecoration(itemDecoration);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rw.setLayoutManager(linearLayoutManager);
        rw.setAdapter(adapter);
    }

    public static void repaintView(Context context, RecyclerView.ViewHolder holder, int position, int currentPosition) {
        int color;
        if (currentPosition == position)
            color = ContextCompat.getColor(context, R.color.colorGrid);
        else
            color = Color.TRANSPARENT;
        holder.itemView.setBackgroundColor(color);
    }

    public static void openUrl(String url, Context context) {
        Uri address = Uri.parse(url);
        Intent openlink = new Intent(Intent.ACTION_VIEW, address);
        context.startActivity(openlink);
    }
}
