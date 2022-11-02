package com.gbossoufolly.blogapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistsException extends RuntimeException {

    String resourceName;
    String fieldName;
    String fieldValue;

    public AlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s : %s already exists", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
