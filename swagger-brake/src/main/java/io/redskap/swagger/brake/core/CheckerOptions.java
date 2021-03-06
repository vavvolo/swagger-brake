package io.redskap.swagger.brake.core;

import lombok.Data;

@Data
public class CheckerOptions {
    private boolean deprecatedApiDeletionAllowed = true;
    private String betaApiExtensionName = "x-beta-api";
}
