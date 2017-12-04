package com.xiaoyue.nov.storage;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<Book, Long> {

	@Cacheable("readinglistCache")
	List<Book> findByReader(Reader reader);

}
