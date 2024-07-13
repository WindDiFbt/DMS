package services;

import entity.News;

import java.util.List;

public interface NewsService {
    List<News> getAllNews();

    int countNumberOfNews();

    List<News> getAllNewsByPagination(int page);

    News getById(int id);

    List<News> getAllByDate(String date);

    int update(News news);

    int add(News news);

    int delete(int id);
}
