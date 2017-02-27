package com.tiy.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/19/2017.
 */

@Controller
public class MastermindController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home () {
        return "redirect:/game";
    }

    @RequestMapping(path = "/game", method = RequestMethod.GET)
    public String game (HttpSession session, Model model) {
        session.setAttribute("board", null);
        SettingsContainer settings = (SettingsContainer)session.getAttribute("settings");
        if (settings == null) {
            settings = SettingsContainer.getDefaultSettings();
            session.setAttribute("settings", settings);
        }
        return "game";
    }

    @RequestMapping(path= "/config", method = RequestMethod.GET)
    public String config () {
        return "config";
    }

    @RequestMapping(path = "/help", method = RequestMethod.GET)
    public String help () {
        return "help";
    }
}
