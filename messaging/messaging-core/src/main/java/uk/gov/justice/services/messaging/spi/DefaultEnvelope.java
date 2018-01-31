package uk.gov.justice.services.messaging.spi;

import uk.gov.justice.services.messaging.Envelope;
import uk.gov.justice.services.messaging.EnvelopeBuilder;
import uk.gov.justice.services.messaging.Metadata;

/**
 * Default implementation of an envelope.
 */
public class DefaultEnvelope <T> implements Envelope<T> {

    private final Metadata metadata;

    private final T payload;

    DefaultEnvelope(final Metadata metadata, final T payload) {
        this.metadata = metadata;
        this.payload = payload;
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }

    @Override
    public T payload() {
        return payload;
    }

    public static EnvelopeBuilder envelopeBuilder() {
        return new DefaultEnvelope.Builder();
    }

    public static class Builder<T> implements EnvelopeBuilder<T> {

        Metadata metadata;

        T payload;

        @Override
        public EnvelopeBuilder withMetadata(final Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        @Override
        public EnvelopeBuilder withPayload(final T payload) {
            this.payload = payload;
            return this;
        }

        @Override
        public Envelope build() {
            return new DefaultEnvelope(metadata,payload);
        }
    }
}
