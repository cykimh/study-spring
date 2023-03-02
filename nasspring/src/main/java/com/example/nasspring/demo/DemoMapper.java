package com.example.nasspring.demo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//XML과 연결고리 인터페이스
@Mapper
public interface DemoMapper {
    List<Demo> selectDemoTable();
}