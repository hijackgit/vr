package br.com.vr;

import java.util.UUID;

import org.junit.experimental.categories.Category;

import br.com.vr.utils.UnitTest;

@Category(UnitTest.class)
public class ServiceUnitTestBase extends ServiceTestBase {

	protected String generateCorrelationId() {
		return UUID.randomUUID().toString();
	}
}
