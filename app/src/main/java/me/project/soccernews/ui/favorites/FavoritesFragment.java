package me.project.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import me.project.soccernews.MainActivity;
import me.project.soccernews.databinding.FragmentFavoritesBinding;
import me.project.soccernews.domain.News;
import me.project.soccernews.ui.adapter.Newsadapter;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);


        loadFavoriteNews();

        return binding.getRoot();
    }

    private void loadFavoriteNews() {
        MainActivity activity = (MainActivity) getActivity();

        if (activity != null) {
            List<News> favoriteNews =  activity.getDb().newsDao().loadFavoriteNews();
            binding.recycleNews.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recycleNews.setAdapter(new Newsadapter(favoriteNews, updatenews -> {
                activity.getDb().newsDao().save(updatenews);
                loadFavoriteNews();
            }));
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}