package io.keeplearning.ratingsdataservice.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PropsResource {

    @Value("${test.prop: dummy}")
    private String os;

    @Value("${test.props.list}")
    private List<String> osList;

    @Value("#{${test.props.map}}")
    private Map<String, String> osMap;

    @GetMapping("/props")
    public String getProps() {
        System.out.println("Property : " + os);
        System.out.println("Property list : " + osList.toString());
        System.out.println("Property Map : " + osMap.toString());
        return osMap.toString();
    }
}
