package com.web.greekkino.controller;


import com.web.greekkino.service.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/draw")
public class DrawController {

    private final DrawService drawService;

    @PostMapping("/sync")
    public void sync(){
        drawService.syncData();
    }

}
