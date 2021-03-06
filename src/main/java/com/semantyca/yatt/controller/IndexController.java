package com.semantyca.yatt.controller;

import com.semantyca.yatt.dto.page.Home;
import com.semantyca.yatt.dto.AbstractOutcome;
import com.semantyca.yatt.dto.PageOutcome;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("home")
    public @ResponseBody
    AbstractOutcome home(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Home home = new Home();
        home.setInfo("{principal:" + auth.getPrincipal() + ", roles:" + auth.getAuthorities() + "}");
        return new PageOutcome().addPayload(home).setPageName("home page");
    }
}
