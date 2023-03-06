package com.example.nasspring.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@Slf4j //Lombok
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    // private final Logger log = LoggerFactory.getLogger(this.getClass()); // annotation 사용으로 선언하지 않아도됨

    // @PostMapping
    // @PutMapping
    @Value("${run.mode}")
    private String runMode;



    @GetMapping("/")
    public String index(Model model) {

        log.info("info");
        log.debug("debug");
        log.warn("warn");

        model.addAttribute("val", "테스트 입니다.");
        model.addAttribute("runMode", runMode);
        return "index";
    }

    @GetMapping("/db-test")
    public String dbTest(Model model) {
        log.info("db-test");

        List<Demo> demoList = demoService.selectDemoTable();

        model.addAttribute("demoList", demoList);

        return "db-test";
    }
    @GetMapping("/db-test2")
    @ResponseBody //알아서 json으로 만들어버림
    public List<Demo> dbTest2(Model model) {
        log.info("db-test");

        List<Demo> demoList = demoService.selectDemoTable();

//        if login count check
//        Session생성
        return demoList;
    }

    @GetMapping("/layout-test")
    public String layoutTest(Model model){
        log.info("layout-test");

        return "layout-test";
    }

    @GetMapping("/interceptor-test")
    public String interceptorTest(Model model){
        log.info("interceptor_test");

        return "layout-test";
    }

    @GetMapping("/interceptor-exclude")
    public String interceptorExclude(Model model){
        log.info("interceptor_exclude");

        return "layout-test";
    }
}

//POST = INSERT
//PUT = UPDATE


// Config로 사용
// application*.properties application*.yml 순으로
