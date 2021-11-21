package repository;

import entity.Singer;
import entity.Test;

import java.util.List;

public interface SingerDao {

    List<Test> test();
    List<Singer> findAll();
    List<Singer> findAllWithAlbum();
    Singer findById(Long id);
    Singer save(Singer singer);
    void delete(Singer singer);

}