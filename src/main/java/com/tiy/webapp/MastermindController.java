package com.tiy.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/19/2017.
 */

@Controller
public class MastermindController {

    List<Color> colors;
    List<String> sizeList;
    List<ColorName> colorNames;
    boolean initialized = false;
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home () {
        if(!initialized) {
            colors = new ArrayList<>();
            colorNames = new ArrayList<>();
            for (Color color : Color.values()) {
                colors.add(color);
                String colorName = color.name().toLowerCase();
                colorName = colorName.substring(0, 1).toUpperCase() + colorName.substring(1, colorName.length());
                colorNames.add(new ColorName(colorName));
            }
            sizeList = new ArrayList<>();
            for (int index = 0; index < Board.DEFAULT_SIZE; index++) {
                sizeList.add("" + index);
            }
            initialized = true;
        }
        return "redirect:/game";
    }

    @RequestMapping(path = "/game", method = RequestMethod.GET)
    public String game (Model model) {
        model.addAttribute("color-list", colorNames);
        model.addAttribute("size-list", sizeList);
        return "game";
    }

    @RequestMapping(path= "/config", method = RequestMethod.GET)
    public String config () {
        return "config";
    }
}
