package com.xiaoyue.nov.storage;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<Book, Long> {

	//@Cacheable("readinglistCache")// 如果打开这个注解的话,即使新增了book,只要查过一次是null,再查也是null.
	List<Book> findByReader(Reader reader);

}
