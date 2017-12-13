package com.xiaoyue.nov.ctrl;

import com.xiaoyue.nov.storage.jpa.Book;
import com.xiaoyue.nov.storage.jpa.Reader;
import com.xiaoyue.nov.storage.jpa.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xiaoyue26 on 17/12/5.
 */
@RestController
@RequestMapping("/api")
public class DataListController {

    private ReadingListRepository readingListRepository;
    @Autowired// 自动注入方法的参数
    public DataListController(ReadingListRepository rp) {
        this.readingListRepository = rp;
    }

    @RequestMapping(value = "/book"
            , method = RequestMethod.GET
            , produces = "application/json")
    public List<Book> spittles(Reader reader) {
        return readingListRepository.findByReader(reader);
    }


}
