package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.core.errors.ErrorMapper;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;

public abstract class BaseOperationProcessor {
    protected final ConversionService conversionService;
    protected final Validator validator;
    protected final ErrorMapper errorMapper;

    protected BaseOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper) {
        this.conversionService = conversionService;
        this.validator = validator;
        this.errorMapper = errorMapper;
    }
}