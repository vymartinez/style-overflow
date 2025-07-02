package br.com.styleoverflow.styleoverflow.interfaces;

public interface BaseDAO {

    void create(Record dto);

    void update(Record dto, Integer id);
}
