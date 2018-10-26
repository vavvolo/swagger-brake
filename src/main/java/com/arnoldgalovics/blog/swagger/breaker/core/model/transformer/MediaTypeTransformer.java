package com.arnoldgalovics.blog.swagger.breaker.core.model.transformer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.arnoldgalovics.blog.swagger.breaker.core.model.Schema;
import com.arnoldgalovics.blog.swagger.breaker.core.model.SchemaAttribute;
import com.arnoldgalovics.blog.swagger.breaker.core.model.service.SchemaStoreProvider;
import com.arnoldgalovics.blog.swagger.breaker.core.model.service.TypeRefNameResolver;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.MediaType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MediaTypeTransformer implements Transformer<MediaType, Schema> {
    private final TypeRefNameResolver typeRefNameResolver;

    @Override
    public Schema transform(MediaType from) {
        return doIt(from.getSchema());
    }

    private Schema doIt(io.swagger.v3.oas.models.media.Schema swSchema) {
        if (swSchema == null) {
            return null;
        }
        if (swSchema instanceof ArraySchema) {
            return transformSchema(swSchema, doIt(((ArraySchema) swSchema).getItems()));
        } else {
            return transformSchema(swSchema);
        }
    }

    private Schema transformSchema(io.swagger.v3.oas.models.media.Schema swSchema) {
        return transformSchema(swSchema, null);
    }

    private Schema transformSchema(io.swagger.v3.oas.models.media.Schema swSchema, Schema childSchema) {
        if (!StringUtils.isBlank(swSchema.get$ref())) {
            String refName = typeRefNameResolver.resolve(swSchema.get$ref());
            return SchemaStoreProvider.provide().get(refName).orElseThrow(() -> new IllegalStateException("Reference not found for " + swSchema.get$ref()));
        }

        String type = swSchema.getType();
        Map<String, io.swagger.v3.oas.models.media.Schema> properties = swSchema.getProperties();
        List<SchemaAttribute> attributes = Collections.emptyList();
        if (properties != null) {
            attributes = properties.entrySet().stream().map(e -> new SchemaAttribute(e.getKey())).collect(Collectors.toList());
        }
        return new Schema(type, attributes, childSchema);
    }
}