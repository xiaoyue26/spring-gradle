package com.xiaoyue.nov.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//<Reader,String>  返回对象类型和对象的id类型
public interface ReaderRepository extends JpaRepository<Reader, String>
        , ReaderSweeper {

    @Query("select username,password,fullname from Reader")
    List<Reader> findAllGmailReaders();
}
