package com.becomejavasenior.dao;

public interface DaoFactory {
    CompanyDao getCompanyDao();
    ContactDao getContactDao();
    DealDao getDealDao();
    FileDao getFileDao();
    LanguageDao getLanguageDao();
    NoteDao getNoteDao();
    RoleDao getRoleDao();
    TagDao getTagDao();
    TaskDao getTaskDao();
    UserDao getUserDao();
    TaskTypeDao getTaskTypeDao();
}
