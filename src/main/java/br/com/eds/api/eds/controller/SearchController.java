package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/search")
@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping
    public ResponseEntity buscar(@RequestParam (required = false) String email,
                                 @RequestParam (required = false) String contato){
        return searchService.search(email, contato);
    }
}
