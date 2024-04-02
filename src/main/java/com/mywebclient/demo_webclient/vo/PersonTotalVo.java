package com.mywebclient.demo_webclient.vo;

import java.util.List;

import lombok.Data;

@Data
public class PersonTotalVo {

    int count;
    List<PersonVo> list;
}
