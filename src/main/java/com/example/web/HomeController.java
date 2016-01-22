package com.example.web;

import com.example.model.SearchDTO;
import com.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kimwonyong on 2016. 1. 22..
 */
@Controller
public class HomeController {
    private static final Logger logger =
            LoggerFactory.getLogger(HomeController.class);

    // home 화면
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() throws Exception{
        logger.info("Come on!! Baby!!");
        return "index";
    }

    // parameters 를 여러개를 담아서 처리할
    @RequestMapping(value = "/exam/ModelAttribute", method = RequestMethod.GET)
    @ResponseBody
    public List<User> examModelAttribute(@ModelAttribute SearchDTO searchParam) throws Exception{
        logger.info(searchParam.toString());

        // user info setting
        List<User> userList = new ArrayList<User>();
        userList.add(new User("iersans1","원디1"));
        userList.add(new User("iersans2","원디2"));

        return userList;
    }

    // parameters 한개 또는 여러개 ... 여러개는 여려개를 나열하면됨 ^^
    // @RequestParam 어노테이션이 적용된 파라미터는 기본적으로 필수 파라미터!!
    // 따라서, 명시한 파라미터가 존재하지 않을 경우 400 에러가 발생!!
    // [Exam]==========================================================
    // @RequestParam(value="userId", defaultValue = "0") Long userId,
    // @RequestParam(value="type, required=false) String type
    @RequestMapping(value = "/exam/RequestParam", method = RequestMethod.GET)
    @ResponseBody
    public User examRequestParam(@RequestParam(value="userName", required = false) String userName) throws Exception{
        logger.info("userName is :" + userName);

        return new User("iersans1","원디1");
    }

    @RequestMapping(value = "/exam/rest/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public User examRestfulGet(@PathVariable("userId") String userId) throws Exception{
        logger.info("userId is :" + userId);

        return new User("iersans1","원디1");
    }

    @RequestMapping(value = "/exam/requestBody/post", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User examRequestBodyPost(@RequestBody User user) throws Exception{
        logger.info("user is " + user);

        // save ...

        return user;
    }

    @RequestMapping(value = "/exam/requestBody/put/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User examRequestBodyPut(@RequestBody User user
                                   ,@PathVariable String userId) throws Exception{
        logger.info("user is " + user);
        logger.info("userId is " + userId);

        // modify...

        return user;
    }

    @RequestMapping(value = "/exam/rest/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User examRestfulDelete(@PathVariable String userId) throws Exception{
        logger.info("userId is " + userId);

        // delete...

        return new User("iersans","원디");
    }


}
