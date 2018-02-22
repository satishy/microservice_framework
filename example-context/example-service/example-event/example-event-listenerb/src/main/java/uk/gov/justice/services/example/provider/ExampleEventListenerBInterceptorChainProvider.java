package uk.gov.justice.services.example.provider;

import uk.gov.justice.services.components.event.listener.interceptors.EventBufferInterceptor;
import uk.gov.justice.services.core.interceptor.InterceptorChainEntry;
import uk.gov.justice.services.core.interceptor.InterceptorChainEntryProvider;

import java.util.ArrayList;
import java.util.List;

public class ExampleEventListenerBInterceptorChainProvider implements InterceptorChainEntryProvider {

    private final List<InterceptorChainEntry> interceptorChainEntries = new ArrayList<>();

    public ExampleEventListenerBInterceptorChainProvider() {
        interceptorChainEntries.add(new InterceptorChainEntry(1000, EventBufferInterceptor.class));
        interceptorChainEntries.add(new InterceptorChainEntry(2000, OtherEventFilterInterceptor.class));
    }

    @Override
    public String component() {
        return "OTHER_EVENT_LISTENER";
    }

    @Override
    public List<InterceptorChainEntry> interceptorChainTypes() {
        return interceptorChainEntries;
    }
}
