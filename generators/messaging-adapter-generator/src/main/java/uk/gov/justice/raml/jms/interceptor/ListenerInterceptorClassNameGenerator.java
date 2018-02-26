package uk.gov.justice.raml.jms.interceptor;

import static com.squareup.javapoet.ClassName.get;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.capitalize;

import com.squareup.javapoet.ClassName;

/**
 * Generates the class name for a custom EventFilterInterceptor. Given a component name of
 * 'MY_CUSTOM_EVENT_LISTENER' and a package of 'org.acme' the resuting class name would be
 * 'org.acme.MyCustomEventFilterInterceptor'
 */
public class ListenerInterceptorClassNameGenerator {

    /**
     * Generate a name for a custom EventFilterInterceptor
     *
     * @param eventListenerComponentName The component name from the pom. Should contain the term 'EVENT_LISTENER'
     *                                   as in 'MY_CUSTOM_EVENT_LISTENER'.
     * @param packageName The package of the custom EventFilterInterceptor
     * @return
     */
    public ClassName interceptorNameFrom(final String eventListenerComponentName, final String packageName) {

        final String choppedName = eventListenerComponentName.replace("EVENT_LISTENER", "");

        final String name = of(choppedName.split("_"))
        .map(token -> capitalize(token.toLowerCase()))
        .collect(joining());

        final String simpleName = format("%sEventFilterInterceptor", name);

        return get(packageName, simpleName);
    }
}
