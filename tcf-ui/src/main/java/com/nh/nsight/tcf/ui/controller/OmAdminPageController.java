package com.nh.nsight.tcf.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OmAdminPageController {

    @GetMapping({"/om/admin", "/om/admin/"})
    public String redirectToLogin() {
        return "redirect:/om/admin/login.html";
    }
}
