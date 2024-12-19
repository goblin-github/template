package com.sy.admin.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Monster
 * @version v1.0
 */
@Controller
@Hidden
public class FaviconController {

    @GetMapping({"favicon.ico"})
    public ResponseEntity<Resource> favicon() {
        return ResponseEntity.notFound().build();
    }
}
