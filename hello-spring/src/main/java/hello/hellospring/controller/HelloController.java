package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Hello !!");

        // return의 의미는 template 하위에 hello.html을 찾음 뷰리졸버가(viewResolver)가
        return "hello";
    }


    // HTML방식
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // API 방식
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // 뷰 엔진을 거치는 것이 아니라 문자열이 그대로 내려감
    }

    @GetMapping("hello-api")
    @ResponseBody //객체가 json으로 반환이 기본
    public Hello helloApi(@RequestParam("name") String name) {

        Hello hello = new Hello();

        hello.setName(name);
        return hello;
    }


    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
