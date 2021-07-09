package com.arounders.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    @GetMapping(value = "/members")
    public String getMembers(){

        return "admin/members";
    }

    @GetMapping(value = "/reports")
    public String getReports(){
        return "admin/reports";
    }

    @GetMapping(value = "/boards")
    public String getBoards(){
        return "admin/boards";
    }
}
