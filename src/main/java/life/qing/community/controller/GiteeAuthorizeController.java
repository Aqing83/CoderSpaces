package life.qing.community.controller;

import life.qing.community.dto.AccessTokenDTO;
import life.qing.community.dto.GitEEUser;
import life.qing.community.dto.GithubUser;
import life.qing.community.mapper.UserMapper;
import life.qing.community.model.User;
import life.qing.community.provider.GitEEProvider;
import life.qing.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 2020/05/14 0:02
 */
@Controller
public class GiteeAuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${gitEE.client.id}")
    private String clientId;

    @Value("${gitEE.client.secret}")
    private String clientSecret;

    @Value("${gitEE.redirect.uri}")
    private String redirectUrl;

    @GetMapping("/callbackGitEE")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        String accessToken = GitEEProvider.getAccessToken(accessTokenDTO);
        GitEEUser gitEEUser = GitEEProvider.getUser(accessToken);
        if (gitEEUser != null && gitEEUser.getId() != null) {
            System.out.println("gitEE登录");
            System.out.println(gitEEUser);
            User user = new User();
            String token = UUID.randomUUID().toString();

            user.setToken(token);
            user.setName(gitEEUser.getName());
            user.setAccountId(String.valueOf(gitEEUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(user.getAvatarUrl());

            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));

        } else {
            //登录失败，重新登录
        }
        return "redirect:/";

    }
}
