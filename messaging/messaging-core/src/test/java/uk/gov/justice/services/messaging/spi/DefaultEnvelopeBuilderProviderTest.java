package uk.gov.justice.services.messaging.spi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import uk.gov.justice.services.messaging.JsonEnvelope;
import uk.gov.justice.services.messaging.Metadata;

import javax.json.JsonValue;

import org.junit.Test;

public class DefaultEnvelopeBuilderProviderTest {

    @Test
    public void shouldProvideDefaultJsonEnvelopeFromMetadataAndJsonValue() throws Exception {
        final JsonEnvelope envelope = mock(JsonEnvelope.class);

//
//        final JsonEnvelope e = new  DefaultEnvelopeBuilderProvider().withMetadataFrom(envelope).apply().envelopeFrom(metadata, payload);
//
//        assertThat(envelope, instanceOf(DefaultJsonEnvelope.class));
//        assertThat(envelope.metadata(), is(metadata));
//        assertThat(envelope.payload(), is(payload));
    }
}
