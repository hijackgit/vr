package br.com.vr;

import java.util.UUID;

import org.junit.experimental.categories.Category;
import org.springframework.http.HttpHeaders;

import br.com.vr.utils.UserContext;
import br.com.vr.utils.IntegrationTest;

@Category(IntegrationTest.class)
public class ServiceIntegrationTestBase extends ServiceTestBase {

    protected HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(UserContext.CORRELATION_ID, UUID.randomUUID().toString());
        return headers;
    }
}
