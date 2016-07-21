package de.otto.edison.hal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.util.List;

import static de.otto.edison.hal.Embedded.embeddedBuilder;
import static de.otto.edison.hal.Link.link;
import static de.otto.edison.hal.Link.self;
import static de.otto.edison.hal.Links.linkingTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by guido on 05.07.16.
 */
public class HalRepresentationEmbeddingTest {

    @Test
    public void shouldRenderEmbeddedResourcesWithProperties() throws JsonProcessingException {
        // given
        final List<HalRepresentation> items = asList(
                new HalRepresentation(linkingTo(self("http://example.org/test/bar/01"))) {public String amount="42€";},
                new HalRepresentation(linkingTo(self("http://example.org/test/bar/02"))) {public String amount="4711€";}
        );
        final HalRepresentation representation = new HalRepresentation(
                linkingTo(self("http://example.org/test/bar")),
                Embedded.embedded("orders", items)) {public String total="4753€";};
        // when
        final String json = new ObjectMapper().writeValueAsString(representation);
        // then
        assertThat(json, is(
                "{" +
                        "\"total\":\"4753€\"," +
                        "\"_links\":{\"self\":{\"href\":\"http://example.org/test/bar\"}}," +
                        "\"_embedded\":{\"orders\":[" +
                                "{" +
                                    "\"amount\":\"42€\"," +
                                    "\"_links\":{\"self\":{\"href\":\"http://example.org/test/bar/01\"}}" +
                                "}," +
                                "{" +
                                    "\"amount\":\"4711€\"," +
                                    "\"_links\":{\"self\":{\"href\":\"http://example.org/test/bar/02\"}}" +
                                "}" +
                                "]}" +
                "}"));
    }

    @Test
    public void shouldRenderEmbeddedResourcesWithMultipleLinks() throws JsonProcessingException {
        // given
        final List<HalRepresentation> items = asList(
                new HalRepresentation(linkingTo(
                        link("test", "http://example.org/test/bar/01"),
                        link("test", "http://example.org/test/bar/02"))) {public String amount="42€";}
        );
        final HalRepresentation representation = new HalRepresentation(
                linkingTo(self("http://example.org/test/bar")),
                Embedded.embedded("orders", items)) {public String total="4753€";};
        // when
        final String json = new ObjectMapper().writeValueAsString(representation);
        // then
        assertThat(json, is(
                "{" +
                        "\"total\":\"4753€\"," +
                        "\"_links\":{\"self\":{\"href\":\"http://example.org/test/bar\"}}," +
                        "\"_embedded\":{\"orders\":[" +
                                "{" +
                                    "\"amount\":\"42€\"," +
                                    "\"_links\":{\"test\":[{\"href\":\"http://example.org/test/bar/01\"},{\"href\":\"http://example.org/test/bar/02\"}]}" +
                                "}" +
                                "]}" +
                "}"));
    }

    @Test
    public void shouldRenderMultipleEmbeddedResources() throws JsonProcessingException {
        // given
        final HalRepresentation representation = new HalRepresentation(
                linkingTo(self("http://example.org/test/bar")),
                embeddedBuilder()
                        .withEmbedded("foo", asList(
                                new HalRepresentation(linkingTo(self("http://example.org/test/foo/01"))),
                                new HalRepresentation(linkingTo(self("http://example.org/test/foo/02")))
                        ))
                        .withEmbedded("bar", asList(
                                new HalRepresentation(linkingTo(self("http://example.org/test/bar/01"))),
                                new HalRepresentation(linkingTo(self("http://example.org/test/bar/02")))
                        )).build());
        // when
        final String json = new ObjectMapper().writeValueAsString(representation);
        // then
        assertThat(json, is(
                "{" +
                        "\"_links\":{\"self\":{\"href\":\"http://example.org/test/bar\"}}," +
                        "\"_embedded\":{" +
                            "\"foo\":[{" +
                                    "\"_links\":{\"self\":{\"href\":\"http://example.org/test/foo/01\"}}" +
                                "},{" +
                                    "\"_links\":{\"self\":{\"href\":\"http://example.org/test/foo/02\"}}" +
                                "}]," +
                            "\"bar\":[{" +
                                    "\"_links\":{\"self\":{\"href\":\"http://example.org/test/bar/01\"}}" +
                                "},{" +
                                    "\"_links\":{\"self\":{\"href\":\"http://example.org/test/bar/02\"}}" +
                                "}]" +
                        "}" +
                "}"));
    }

}