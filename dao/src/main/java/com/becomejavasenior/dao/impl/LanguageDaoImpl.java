package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.LanguageDao;
import com.becomejavasenior.model.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class LanguageDaoImpl extends CommonDao implements LanguageDao {

    private static final Logger LOGGER = LogManager.getLogger(LanguageDaoImpl.class);

    public LanguageDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Language language) {
        return 0;
    }

    @Override
    public Language getLanguageById(int id) {
        return null;
    }

    @Override
    public int update(Language language) {
        return 0;
    }

    @Override
    public int delete(Language language) {
        return 0;
    }

    @Override
    public List<Language> findAll() {
        return null;
    }

}
