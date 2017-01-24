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

    List<Color> colors;
    List<ColorName> colorNames;
    boolean initialized = false;
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home () {
        if(!initialized) {
            initialize();
        }
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
        int numColors = settings.getNumColors();
        if (numColors != colorNames.size()) {
            int index = 0;
            colorNames = new ArrayList<>();
            for (Color color : Color.values()) {
                if (index < numColors) {
                    String colorName = color.name().toLowerCase();
                    colorName = colorName.substring(0, 1).toUpperCase() + colorName.substring(1, colorName.length());
                    colorNames.add(new ColorName(colorName));
                }
                index++;
            }
        }
        model.addAttribute("color-list", colorNames);
        if(!initialized) {
            initialize();
        }
        return "game";
    }

    @RequestMapping(path= "/config", method = RequestMethod.GET)
    public String config () {
        return "config";
    }

    public void initialize () {
        colors = new ArrayList<>();
        colorNames = new ArrayList<>();
        for (Color color : Color.values()) {
            colors.add(color);
            String colorName = color.name().toLowerCase();
            colorName = colorName.substring(0, 1).toUpperCase() + colorName.substring(1, colorName.length());
            colorNames.add(new ColorName(colorName));
        }

        initialized = true;
    }
}
