package com.cn.tju.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.MyHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by Boyu on 2017/2/14.
 */
@Slf4j
@Controller
public class IndexController {



    @RequestMapping(value = "/sendmessage", method = RequestMethod.GET)
    public String user(HttpServletRequest request, Model model) throws IOException {
        log.info("user");
        return "success";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) throws IOException {
        log.info("user login");
        String userName = request.getParameter("userName");
        request.getSession().setAttribute("user",userName);
        return "success";
    }

    @RequestMapping(value = "/loginIn", method = RequestMethod.GET)
    public String loginIn(HttpServletRequest request, Model model) throws IOException {
        log.info("user login");
        return "login";
    }

    @RequestMapping(value = "/websocket", method = RequestMethod.GET)
    public String websocket(HttpServletRequest request, Model model) throws IOException {
        log.info("user login");
        return "websocket";
    }

}
