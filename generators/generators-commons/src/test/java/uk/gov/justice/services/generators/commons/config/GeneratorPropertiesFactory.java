package uk.gov.justice.services.generators.commons.config;

import static uk.gov.justice.services.test.utils.core.reflection.ReflectionUtil.setField;

import uk.gov.justice.maven.generator.io.files.parser.core.GeneratorProperties;

public class GeneratorPropertiesFactory {

    private static final String SERVICE_COMPONENT_KEY = "serviceComponent";
    private static final String CUSTOM_MDB_POOL = "customMDBPool";

    public static GeneratorPropertiesFactory generatorProperties() {
        return new GeneratorPropertiesFactory();
    }

    public GeneratorProperties withServiceComponentOf(final String serviceComponent) {
        final CommonGeneratorProperties properties = new CommonGeneratorProperties();
        try {
            setField(properties, SERVICE_COMPONENT_KEY, serviceComponent);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public GeneratorProperties withDefaultServiceComponent() {
        final CommonGeneratorProperties properties = new CommonGeneratorProperties();
        try {
            setField(properties, SERVICE_COMPONENT_KEY, "COMMAND_API");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
