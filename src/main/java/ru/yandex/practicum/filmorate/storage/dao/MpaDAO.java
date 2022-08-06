package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaDAO {
    Mpa findById(int id);

    List<Mpa> findAll();

    Mpa create(Mpa mpa);

    Mpa update(Mpa mpa);
}