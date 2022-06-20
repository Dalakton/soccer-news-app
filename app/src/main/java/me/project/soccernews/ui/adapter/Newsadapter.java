package me.project.soccernews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.project.soccernews.R;
import me.project.soccernews.databinding.NewsItemBinding;
import me.project.soccernews.domain.News;

public class Newsadapter extends RecyclerView.Adapter<Newsadapter.ViewHolder> {

    private final List<News> news;
    private final favoriteListener favoritelistener;


    public Newsadapter(List<News> news, favoriteListener favoritelistener) {
        this.news = news;
        this.favoritelistener = favoritelistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        News news = this.news.get(position);
        holder.binding.textTitle.setText(news.title);
        holder.binding.textDescription.setText(news.description);
        Picasso.get().load(news.image).fit().into(holder.binding.ivThumbnail);
        // Implementação da funcionalidade de abrir link via botão
        holder.binding.buttonOpenLink.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.link));
            context.startActivity(i);
        });
        // Implementação da funcionalidade de compartilha
        holder.binding.imageViewShare.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, news.link);
            context.startActivity(Intent.createChooser(i, "Share"));
        });
        // Implementação da funcionalidade de favorite. / o evento será instanciado pela fragment
        holder.binding.imageViewFavorite.setOnClickListener(view -> {
            news.favorite = !news.favorite;
            this.favoritelistener.onFavorite(news);
            notifyItemChanged(position);
        });
        int favoriteColor = news.favorite ? R.color.favorite_active : R.color.favorite_inactive;
        holder.binding.imageViewFavorite.setColorFilter(context.getResources().getColor(favoriteColor));


    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface favoriteListener {
        void onFavorite(News news );
    }


}
