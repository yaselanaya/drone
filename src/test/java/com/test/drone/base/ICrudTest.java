package com.test.drone.base;

public interface ICrudTest {

    void findById();

    void findAllPaging();

    void save() throws Exception;

    void update() throws Exception;
}
