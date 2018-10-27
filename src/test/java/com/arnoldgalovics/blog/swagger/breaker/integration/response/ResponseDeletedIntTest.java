package com.arnoldgalovics.blog.swagger.breaker.integration.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;

import com.arnoldgalovics.blog.swagger.breaker.core.BreakingChange;
import com.arnoldgalovics.blog.swagger.breaker.core.model.HttpMethod;
import com.arnoldgalovics.blog.swagger.breaker.core.rule.response.ResponseDeletedBreakingChange;
import com.arnoldgalovics.blog.swagger.breaker.integration.AbstractSwaggerBreakerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ResponseDeletedIntTest extends AbstractSwaggerBreakerTest {
    @Test
    public void testResponseTypeChangeIsBreakingChangeWhenExistingAttributeRemoved() {
        // given
        String oldApiPath = "response/deleted/petstore.yaml";
        String newApiPath = "response/deleted/petstore_v2.yaml";
        ResponseDeletedBreakingChange bc = new ResponseDeletedBreakingChange("/pet", HttpMethod.PUT, "404");
        Collection<BreakingChange> expected = Collections.singleton(bc);
        // when
        Collection<BreakingChange> result = underTest.execute(oldApiPath, newApiPath);
        // then
        assertThat(result).hasSize(1);
        assertThat(result).hasSameElementsAs(expected);
    }
}