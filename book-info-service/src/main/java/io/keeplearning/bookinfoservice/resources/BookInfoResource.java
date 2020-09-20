package io.keeplearning.bookinfoservice.resources;

import io.keeplearning.bookinfoservice.model.Book;
import io.keeplearning.bookinfoservice.model.GoodreadsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.text.MessageFormat;

@RestController
@RequestMapping("/books")
public class BookInfoResource {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;

    @GetMapping("/{bookId}")
    public Book getBookInfo(@PathVariable("bookId") String bookId) {
        MessageFormat messageFormat = new MessageFormat(apiUrl);
        String url = messageFormat.format(new Object[]{bookId, apiKey});

        String responseString = restTemplate.getForObject(url, String.class);

        JAXBContext jaxbContext;
        GoodreadsResponse response = null;
        try {
            jaxbContext = JAXBContext.newInstance(GoodreadsResponse.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            response = (GoodreadsResponse) jaxbUnmarshaller.unmarshal(new StringReader(responseString));
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
        GoodreadsResponse.Book book = response.getBook();
        return new Book(bookId, book.getTitle(), book.getDescription());
    }
}
