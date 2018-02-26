package uk.gov.justice.raml.jms.interceptor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.squareup.javapoet.ClassName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventListenerGeneratedClassesNameGeneratorTest {

    @InjectMocks
    private EventListenerGeneratedClassesNameGenerator eventListenerGeneratedClassesNameGenerator;

    @Test
    public void shouldGenerateAnInterceptorNameFromAComponentNameWithEventListenerAtTheEnd() throws Exception {

        final String packageName = "uk.gov.justice.api.interceptor.filter";
        final String classNameSuffix = "EventFilterInterceptor";
        final String eventListenerComponentName = "MY_CUSTOM_EVENT_LISTENER";

        final ClassName className = eventListenerGeneratedClassesNameGenerator.interceptorNameFrom(
                eventListenerComponentName,
                classNameSuffix,
                packageName);

        assertThat(className.toString(), is("uk.gov.justice.api.interceptor.filter.MyCustomEventFilterInterceptor"));
    }

    @Test
    public void shouldGenerateAnInterceptorNameFromAComponentNameWithEventListenerAtTheStart() throws Exception {

        final String packageName = "uk.gov.justice.bloggs.fred.filter";
        final String classNameSuffix = "EventFilterInterceptor";
        final String eventListenerComponentName = "EVENT_LISTENER_CUSTOM";

        final ClassName className = eventListenerGeneratedClassesNameGenerator.interceptorNameFrom(
                eventListenerComponentName,
                classNameSuffix,
                packageName);

        assertThat(className.toString(), is("uk.gov.justice.bloggs.fred.filter.CustomEventFilterInterceptor"));
    }

    @Test
    public void shouldGenerateAnInterceptorNameFromAComponentNameWithEventListenerInTheMiddle() throws Exception {

        final String packageName = "uk.gov.justice.api.interceptor.filter";
        final String classNameSuffix = "EventFilterInterceptor";
        final String eventListenerComponentName = "MY_CUSTOM_EVENT_LISTENER_NAME";

        final ClassName className = eventListenerGeneratedClassesNameGenerator.interceptorNameFrom(
                eventListenerComponentName,
                classNameSuffix,
                packageName);

        assertThat(className.toString(), is("uk.gov.justice.api.interceptor.filter.MyCustomNameEventFilterInterceptor"));
    }
}
