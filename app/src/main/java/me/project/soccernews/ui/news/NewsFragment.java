package me.project.soccernews.ui.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import me.project.soccernews.MainActivity;
import me.project.soccernews.data.local.AppDatabase;
import me.project.soccernews.databinding.FragmentNewsBinding;
import me.project.soccernews.ui.adapter.Newsadapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.recycleNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(),  news -> {
            binding.recycleNews.setAdapter(new Newsadapter(news, updatenews -> {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.getDb().newsDao().save(updatenews);
                }
            }));
        });

        newsViewModel.getState().observe(getViewLifecycleOwner(),  state -> {
            switch (state){
                case DOING:
                    // TODO inicir o swipeRefreshLayout  / loading
                    break;
                case DONE:
                    //TODO finalizar o swipeRefreshLayout  / loading
                    break;
                case ERROR:
                    //TODO finalizar o swipeRefreshLayout  / loading
                    //TODO Mostrar erro generico
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}