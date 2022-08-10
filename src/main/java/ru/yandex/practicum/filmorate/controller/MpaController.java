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
        return mpa.findById(id);
    }

    @GetMapping
    public List<Mpa> findAll() {
        return mpa.findAll();
    }

    @PostMapping
    public Mpa create(@Valid @RequestBody Mpa data) {
        return mpa.create(data);
    }

    @PutMapping
    public Mpa update(@Valid @RequestBody Mpa data) {
        return mpa.update(data);
    }

}
