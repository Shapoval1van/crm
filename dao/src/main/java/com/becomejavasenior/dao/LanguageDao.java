package com.becomejavasenior.dao;


import com.becomejavasenior.model.Language;

import java.util.List;

public interface LanguageDao {
    int create(Language language);
    Language getLanguageById(int id);
    int update(Language language);
    int delete(Language language);
    List<Language> findAll();
}
