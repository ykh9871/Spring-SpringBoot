package ykh.ykhspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("name", "ユン·ギュホン");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name_value, Model model) {
        model.addAttribute("name", name_value);
        return "hello-template";
    }

    //  인자 값 2개를 받는 메서드를 작성하고, View로 렌더링 (name - 문자열, age - 정수)
    @GetMapping("hello-mvc2")
    public String helloAge(@RequestParam("name") String name_value,
                           @RequestParam("age") Integer age_value, Model model) {
        model.addAttribute("name", name_value);
        model.addAttribute("age", age_value);
        return "hello-template2";
    }
    @GetMapping("form-test")
    public String formTest(){
        return "form-test";
    }
    @PostMapping("form-test-get")
    public String formTestGet(@RequestParam("username") String username_value,
                              @RequestParam("password") String password_value, Model model){
        model.addAttribute("username", username_value);
        model.addAttribute("password", password_value);
        return "hello-template3";
    }


}
