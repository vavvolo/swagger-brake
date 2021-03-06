package io.redskap.swagger.brake.core.rule.beta;

import static java.lang.String.format;

import io.redskap.swagger.brake.core.BreakingChange;
import io.redskap.swagger.brake.core.model.HttpMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class StandardApiToBetaApiBreakingChange implements BreakingChange {
    private final String path;
    private final HttpMethod method;

    @Override
    public String getMessage() {
        return format("Path %s %s has been as beta however it was already present.", path, method);
    }
}
