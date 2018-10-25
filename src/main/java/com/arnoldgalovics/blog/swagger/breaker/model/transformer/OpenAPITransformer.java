package com.arnoldgalovics.blog.swagger.breaker.model.transformer;

import com.arnoldgalovics.blog.swagger.breaker.model.Specification;
import io.swagger.v3.oas.models.OpenAPI;

public class OpenAPITransformer implements Transformer<OpenAPI, Specification> {
    @Override
    public Specification transform(OpenAPI from) {
        return new Specification(new PathTransformer().transform(from.getPaths()));
    }
}