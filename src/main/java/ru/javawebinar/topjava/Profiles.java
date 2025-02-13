package ru.javawebinar.topjava;

import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb",
            HEROKU = "heroku";

    //  Get DB profile depending on DB driver in classpath
    public static String getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }

    public static String getActiveRepoImplProfile(){
        String activeRepoImpl = System.getProperty("spring.profiles.active");
        if (JDBC.equalsIgnoreCase(activeRepoImpl))
            return JDBC;
        else if(JPA.equalsIgnoreCase(activeRepoImpl))
            return JPA;
        else if(DATAJPA.equalsIgnoreCase(activeRepoImpl))
            return DATAJPA;
        else
            throw new IllegalStateException("Could not find out active repository implementation");
    }
}
