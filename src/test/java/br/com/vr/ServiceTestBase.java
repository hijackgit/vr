package br.com.vr;

import static java.util.Objects.isNull;

import java.lang.reflect.Field;
import java.util.UUID;

public class ServiceTestBase {

    /**
     * Responsável por simular injeção durante os testes.
     *
     * @param Nome do atributo privatedo que recebera a injeção.
     * @param Instancia que será injetada
     * @param Instancia que receberá a injenção
     * @throws Exception
     */
    protected void mockDependencyInjection(final String namePrivateAttribute, final Object objectInject, final Object object) {
        Field field;
        try {
            field = object.getClass().getDeclaredField(namePrivateAttribute);
            field.setAccessible(true);
            field.set(object, objectInject);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsável por simular injeção durante os testes.
     *
     * @param Nome do atributo privatedo que recebera a injeção.
     * @param Instancia que será injetada
     * @param Instancia que receberá a injenção
     * @throws Exception
     */
    protected void mockDependencyInjectionSuperClass(final String namePrivateAttribute, final Object objectInject, final Object object) {
        Field field;
        try {
            field = object.getClass().getSuperclass().getDeclaredField(namePrivateAttribute);
            field.setAccessible(true);
            field.set(object, objectInject);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    protected String getCorrelationId(String correlationId) {
        if (isNull(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }
}
