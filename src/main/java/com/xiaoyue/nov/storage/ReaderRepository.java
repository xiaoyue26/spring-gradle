package com.xiaoyue.nov.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, String> {

    @Query("select username,password,fullname from Reader")
    List<Reader> findAllGmailReaders();
}
