package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {

    private final MpaDao mpa;

    @Autowired
    public MpaController(MpaDao mpa) {
        this.mpa = mpa;
    }

    @GetMapping("/{id}")
    public Mpa findById(@PathVariable int id) {
        log.info("Поиск рейтинга по id - {}" + id);
        return mpa.findById(id);
    }

    @GetMapping
    public List<Mpa> findAll() {
        log.info("Все рейтинги");
        return mpa.findAll();
    }

    @PostMapping
    public Mpa create(@Valid @RequestBody Mpa data) {
        log.info("Создание рейтинга");
        return mpa.create(data);
    }

    @PutMapping
    public Mpa update(@Valid @RequestBody Mpa data) {
        log.info("Обновление рейтинга");
        return mpa.update(data);
    }

}
