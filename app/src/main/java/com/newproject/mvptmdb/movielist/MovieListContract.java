package com.newproject.mvptmdb.movielist;

import com.newproject.mvptmdb.model.Movie;

import java.util.List;

public interface MovieListContract {

    void onFinished(List<Movie> movies);

    void onFailure(Throwable t);

    interface Model {
        void getTopRatedMovieList(MovieListContract onFinishedListener, int pageNo);

        interface OnFinishedListener {
            void onFinished(List<Movie> movieArrayList);
            void onFailure(Throwable t);
        }

        void getMovieList(OnFinishedListener onFinishedListener, int pageNo);
        void getTopRatedMovieList(OnFinishedListener onFinishedListener, int pageNo);

    }

    interface View {
        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(List<Movie> movieArrayList);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void getMoreData(int pageNo);
        void requestDataFromServer();
        void getTopRated();
    }
}