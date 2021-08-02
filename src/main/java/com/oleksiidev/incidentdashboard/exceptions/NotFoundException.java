package com.oleksiidev.incidentdashboard.exceptions;

import com.oleksiidev.incidentdashboard.model.BaseModel;
import org.springframework.util.StringUtils;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NotFoundException(String entityName, Long id) {
        super(String.format("No %s was found for id: %d", entityName, id));
    }

    public NotFoundException(String entityName, String parameterName, String parameter) {
        super(String.format("No %s was found for for %s: %s", entityName, parameterName, parameter));
    }

    public NotFoundException(Class <? extends BaseModel> clazz, Long id) {
        super(String.format("No %s was found for id: %d", clazz.getName(), id));
    }

    public NotFoundException(Class <? extends BaseModel> clazz, String parameterName, String parameter) {
        super(String.format("No %s was found for for %s: %s", clazz.getName(), parameterName, parameter));
    }
}
