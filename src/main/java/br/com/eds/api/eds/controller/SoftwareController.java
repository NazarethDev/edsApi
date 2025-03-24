package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    SoftwareService softwareService;
}
