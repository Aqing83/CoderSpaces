package life.qing.community.controller;

import life.qing.community.dto.QuestionDTO;
import life.qing.community.mapper.QuestionMapper;
import life.qing.community.mapper.UserMapper;
import life.qing.community.model.Question;
import life.qing.community.model.User;
import life.qing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    private String index(HttpServletRequest request,
                         Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.selectToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        System.out.println(user.getAvatarUrl());
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionDTOList = questionService.list();
        model.addAttribute("questions",questionDTOList);
        return "index";
    }

}
