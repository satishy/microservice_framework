package uk.gov.justice.services.messaging.spi;

import static java.lang.String.format;
import static uk.gov.justice.services.messaging.JsonEnvelope.envelopeFrom;
import static uk.gov.justice.services.messaging.JsonEnvelope.metadataFrom;
import static uk.gov.justice.services.messaging.JsonMetadata.CAUSATION;
import static uk.gov.justice.services.messaging.JsonMetadata.CREATED_AT;
import static uk.gov.justice.services.messaging.JsonMetadata.ID;
import static uk.gov.justice.services.messaging.JsonMetadata.NAME;
import static uk.gov.justice.services.messaging.JsonMetadata.STREAM;

import uk.gov.justice.services.common.converter.ObjectToJsonValueConverter;
import uk.gov.justice.services.common.converter.ZonedDateTimes;
import uk.gov.justice.services.common.util.Clock;
import uk.gov.justice.services.core.enveloper.exception.InvalidEventException;
import uk.gov.justice.services.messaging.Envelope;
import uk.gov.justice.services.messaging.JsonEnvelope;
import uk.gov.justice.services.messaging.JsonObjects;
import uk.gov.justice.services.messaging.Metadata;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class DefaultEnvelopeBuilderProvider implements EnvelopeBuilderProvider {

    Clock clock;

    private ConcurrentHashMap<Class<?>, String> eventMap = new ConcurrentHashMap<>();

    @Inject
    ObjectToJsonValueConverter objectToJsonValueConverter;

    @Override
    public Function<Object, JsonEnvelope> withMetadataFrom(final JsonEnvelope envelope) {
        return x -> envelopeFrom(buildMetaData(x, envelope.metadata()), objectToJsonValueConverter.convert(x));
    }

    @Override
    public Function<Object, JsonEnvelope> withMetadataFrom(final JsonEnvelope envelope, final String name) {
        return x -> envelopeFrom(buildMetaData(envelope.metadata(), name), x == null ? JsonValue.NULL : objectToJsonValueConverter.convert(x));
    }

    @Override
    public <T> Function<T, Envelope<T>> withMetadataFrom(final T envelope) {
        return null;
    }

    @Override
    public <T> Function<T, Envelope<T>> withMetadataFrom(final T envelope, final String name) {
        return null;
    }

    private Metadata buildMetaData(final Object eventObject, final Metadata metadata) {
        if (eventObject == null) {
            throw new IllegalArgumentException("Event object should not be null");
        }

        if (!eventMap.containsKey(eventObject.getClass())) {
            throw new InvalidEventException(format("Failed to map event. No event registered for %s", eventObject.getClass()));
        }

        return buildMetaData(metadata, eventMap.get(eventObject.getClass()));
    }

    private Metadata buildMetaData(final Metadata metadata, final String name) {

        JsonObjectBuilder metadataBuilder = JsonObjects.createObjectBuilderWithFilter(metadata.asJsonObject(),
                x -> !Arrays.asList(ID, NAME, CAUSATION, STREAM).contains(x));

        final JsonObject jsonObject = metadataBuilder
                .add(ID, UUID.randomUUID().toString())
                .add(NAME, name)
                .add(CAUSATION, createCausation(metadata))
                .add(CREATED_AT, ZonedDateTimes.toString(clock.now()))
                .build();

        return metadataFrom(jsonObject).build();
    }

    private JsonArray createCausation(final Metadata metadata) {
        JsonArrayBuilder causation = Json.createArrayBuilder();
        if (metadata.asJsonObject().containsKey(CAUSATION)) {
            metadata.asJsonObject().getJsonArray(CAUSATION).forEach(causation::add);
        }
        causation.add(metadata.id().toString());

        return causation.build();
    }

}
