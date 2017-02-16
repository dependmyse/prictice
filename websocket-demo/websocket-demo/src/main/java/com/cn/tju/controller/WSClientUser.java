package com.cn.tju.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by Boyu on 2017/2/15.
 */
@Slf4j
@Data
public class WSClientUser {
    private String userName;
    private String area = "局域网用户";
    private HttpSession httpSession;
    private Session session;
}
