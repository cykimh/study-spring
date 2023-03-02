package com.example.nasspring.demo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class DemoRepository {

    @Autowired
    private DemoMapper demoMapper;

    public List<Demo> selectDemoTable() {
        return demoMapper.selectDemoTable();
    }
}