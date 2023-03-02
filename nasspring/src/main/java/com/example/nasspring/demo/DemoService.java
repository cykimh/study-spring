package com.example.nasspring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DemoService {
    @Autowired
    private DemoRepository demoRepository;

    // @Transactional(readOnly = true) # 자동 Slave를 바라보도록 처리됌
    public List<Demo> selectDemoTable() {
        return demoRepository.selectDemoTable();
    }
}