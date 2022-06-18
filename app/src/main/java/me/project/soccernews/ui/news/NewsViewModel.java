package me.project.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import me.project.soccernews.domain.News;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();

        // TODO Remover Mocku de Noticias
       List<News> news = new ArrayList<>();
        news.add(new News("Ferroviária tem Desfalque Importante","Chegou "));
        news.add(new News("Ferrinha Joga no Sábado","Chegou"));
        news.add(new News("Copa do Mundo Feminina Está Terminado","Chegou"));

        this.news.setValue(news);
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}