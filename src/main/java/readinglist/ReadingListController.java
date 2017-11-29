package readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
//@ConfigurationProperties("amazon") 由于使用AmazonProperties,controller可以不配置这个了.
public class ReadingListController {

	private ReadingListRepository readingListRepository;
	private AmazonProperties amazonConfig;

	@Autowired// 自动注入方法的参数
	public ReadingListController(ReadingListRepository readingListRepository,
								 AmazonProperties amazonConfig) {
		this.readingListRepository = readingListRepository;
		this.amazonConfig = amazonConfig;
	}

	@RequestMapping(method=RequestMethod.GET, value="/fail")
	public void fail() {
		throw new RuntimeException();
	}

	@ExceptionHandler(value=RuntimeException.class)
	@ResponseStatus(value=HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	public String error() {
		return "error";
	}


	@RequestMapping(method=RequestMethod.GET)
	public String readersBooks(Reader reader, Model model) {// 由于参数解析器,解析出了Reader对象
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
			model.addAttribute("amazonID", amazonConfig.getAssociateId());
		}
		return "readingList";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String addToReadingList(Reader reader, Book book) {// 由于参数解析器,解析出了Reader对象
		book.setReader(reader);
		readingListRepository.save(book);
		return "redirect:/";
	}

}
