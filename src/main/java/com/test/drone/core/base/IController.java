package com.test.drone.core.base;

import com.test.drone.core.exception.DroneException;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface IController<Resource, Dto extends IDto, Id> {

    ResponseEntity<Resource> findById(Id id);

    ResponseEntity<PagedModel<Resource>> findAllPaging(HttpServletRequest request) throws DroneException;

    ResponseEntity<Resource> save(Dto dto) throws DroneException;

    ResponseEntity<Resource> update(Dto dto) throws DroneException;

    ResponseEntity<HttpStatus> delete(Id id) throws DroneException;
}
