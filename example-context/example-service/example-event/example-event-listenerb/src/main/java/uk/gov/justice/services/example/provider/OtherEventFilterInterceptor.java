package uk.gov.justice.services.example.provider;

import uk.gov.justice.api.OtherEventListenerEventFilter;
import uk.gov.justice.services.core.interceptor.Interceptor;
import uk.gov.justice.services.core.interceptor.InterceptorChain;
import uk.gov.justice.services.core.interceptor.InterceptorContext;

import javax.inject.Inject;

public class OtherEventFilterInterceptor implements Interceptor {

    @Inject
    private OtherEventListenerEventFilter filter;

    @Override
    public InterceptorContext process(final InterceptorContext interceptorContext, final InterceptorChain interceptorChain) {

        if (filter.accepts(interceptorContext.inputEnvelope().metadata().name())) {
            return interceptorChain.processNext(interceptorContext);
        }

        return interceptorContext;
    }
}