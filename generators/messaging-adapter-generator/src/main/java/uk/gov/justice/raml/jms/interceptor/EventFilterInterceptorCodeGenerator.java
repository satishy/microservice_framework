package uk.gov.justice.raml.jms.interceptor;

import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

import uk.gov.justice.services.core.interceptor.Interceptor;
import uk.gov.justice.services.core.interceptor.InterceptorChain;
import uk.gov.justice.services.core.interceptor.InterceptorContext;

import javax.inject.Inject;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Should generate a custom EventFilterInterceptor that uses a custom Event Filter. Something like this:
 *
 * <pre><blockquote>
 *
 *     public class MyCustomEventFilterInterceptor implements Interceptor {
 *
 *         @Inject
 *         private MyCustomEventFilter eventFilter;
 *
 *         public InterceptorContext process(final InterceptorContext interceptorContext, final InterceptorChain interceptorChain) {
 *
 *             if (eventFilter.accepts(interceptorContext.inputEnvelope().metadata().name())) {
 *                 return interceptorChain.processNext(interceptorContext);
 *              }
 *
 *             return interceptorContext;
 *         }
 *     }
 *
 *     
 * </blockquote></pre>
 */
public class EventFilterInterceptorCodeGenerator {

    private static final String PACKAGE_NAME = "uk.gov.justice.api.interceptor.filter";
    private static final String FIELD_NAME = "eventFilter";

    private final ListenerInterceptorClassNameGenerator listenerInterceptorClassNameGenerator = new ListenerInterceptorClassNameGenerator();

    
    /**
     * Generate a custom EventFilterInterceptor which uses a custom {@see uk.gov.justice.services.event.buffer.api.EventFilter}
     *
     * @param eventFilterClassName The class name of the custom EventFilter
     * @param componentName The Component name. Should name should contain the term 'EVENT_LISTENER' as in 'MY_CUSTOM_EVENT_LISTENER'
     * @return a TypeSpec that will generate the java source file
     */
    public TypeSpec generate(final ClassName eventFilterClassName, final String componentName) {

        final ClassName eventListenerInterceptorClassName = listenerInterceptorClassNameGenerator.interceptorNameFrom(
                componentName,
                PACKAGE_NAME);

        return classBuilder(eventListenerInterceptorClassName)
                .addModifiers(PUBLIC)
                .addSuperinterface(Interceptor.class)
                .addField(createEventFilterField(eventFilterClassName))
                .addMethod(createProcessMethod())
                .build();
    }

    private FieldSpec createEventFilterField(final ClassName eventFilterClassName) {
        return FieldSpec.builder(eventFilterClassName, FIELD_NAME, PRIVATE)
                .addAnnotation(Inject.class)
                .build();
    }

    private MethodSpec createProcessMethod() {
        final ClassName interceptorContextClassName = get(InterceptorContext.class);

        return MethodSpec.methodBuilder("process")
                .addModifiers(PUBLIC)
                .addParameter(interceptorContextClassName, "interceptorContext", FINAL)
                .addParameter(get(InterceptorChain.class), "interceptorChain", FINAL)
                .returns(interceptorContextClassName)
                .addCode(
                        "if($N.accepts(interceptorContext.inputEnvelope().metadata().name())) {\n" +
                        "    return interceptorChain.processNext(interceptorContext);\n" +
                        "}\n",
                        FIELD_NAME)
                .addStatement("return interceptorContext")
                .build();
    }
}
