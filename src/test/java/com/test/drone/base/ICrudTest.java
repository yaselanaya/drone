package com.test.drone.base;

public interface ICrudTest {

    void findById() throws Exception;

    void findAllPaging() throws Exception;

    void save() throws Exception;

    void update() throws Exception;
}
