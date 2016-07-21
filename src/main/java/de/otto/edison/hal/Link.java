package de.otto.edison.hal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static java.lang.Boolean.TRUE;

/**
 * Created by guido on 05.07.16.
 */
@JsonInclude(NON_ABSENT)
public class Link {

    @JsonIgnore
    public final String rel;
    public final String href;
    public final Boolean templated;
    public final String type;
    public final String hreflang;
    public final String title;
    public final String name;
    public final String profile;
    public final Boolean deprecated;


    private Link(final String rel, final String href) {
        this(rel, href, null, null, null, null, null, null, null);
    }

    private Link(final String rel, final String href,
                 final Boolean templated,
                 final String type,
                 final String hrefLang,
                 final String title,
                 final String name,
                 final String profile,
                 final Boolean deprecated) {
        this.rel = rel;
        this.href = href;
        this.templated = templated;
        this.type = type;
        this.hreflang = hrefLang;
        this.title = title;
        this.name = name;
        this.profile = profile;
        this.deprecated = deprecated;
    }

    public static Link self(final String href) {
        return new Link("self", href);
    }

    public static Link profile(final String href) {
        return new Link("profile", href);
    }

    public static Link item(final String href) {
        return new Link("item", href);
    }

    public static Link collection(final String href) {
        return new Link("collection", href);
    }

    public static Link link(final String rel, final String href) {
        return new Link(rel, href);
    }

    public static Link templated(final String rel, final String uriTemplate) {
        return new Link(rel, uriTemplate, TRUE, null, null, null, null, null, null);
    }

    public static LinkBuilder templatedBuilderBuilder(final String rel, final String uriTemplate) {
        return new LinkBuilder(rel, uriTemplate).beeingTemplated();
    }

    public static LinkBuilder linkBuilder(final String rel, final String href) {
        return new LinkBuilder(rel, href);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (rel != null ? !rel.equals(link.rel) : link.rel != null) return false;
        if (href != null ? !href.equals(link.href) : link.href != null) return false;
        if (templated != null ? !templated.equals(link.templated) : link.templated != null) return false;
        if (type != null ? !type.equals(link.type) : link.type != null) return false;
        if (hreflang != null ? !hreflang.equals(link.hreflang) : link.hreflang != null) return false;
        if (title != null ? !title.equals(link.title) : link.title != null) return false;
        if (name != null ? !name.equals(link.name) : link.name != null) return false;
        if (profile != null ? !profile.equals(link.profile) : link.profile != null) return false;
        return deprecated != null ? deprecated.equals(link.deprecated) : link.deprecated == null;

    }

    @Override
    public int hashCode() {
        int result = rel != null ? rel.hashCode() : 0;
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (templated != null ? templated.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (hreflang != null ? hreflang.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + (deprecated != null ? deprecated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "rel='" + rel + '\'' +
                ", href='" + href + '\'' +
                ", templated=" + templated +
                ", type='" + type + '\'' +
                ", hreflang='" + hreflang + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", profile='" + profile + '\'' +
                ", deprecated=" + deprecated +
                '}';
    }

    public static class LinkBuilder {
        private final String rel;
        private final String href;
        private String type;
        private String hrefLang;
        private String title;
        private String name;
        private String profile;
        private Boolean deprecated;
        private Boolean templated;

        public LinkBuilder(final String rel, final String href) {
            this.rel = rel;
            this.href = href;
        }

        public LinkBuilder withType(final String type) {
            this.type = type;
            return this;
        }

        public LinkBuilder withHrefLang(final String hrefLang) {
            this.hrefLang = hrefLang;
            return this;
        }

        public LinkBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public LinkBuilder withName(final String name) {
            this.name = name;
            return this;
        }

        public LinkBuilder withProfile(final String profile) {
            this.profile = profile;
            return this;
        }

        public LinkBuilder beeingDeprecated() {
            this.deprecated = TRUE;
            return this;
        }

        public LinkBuilder beeingTemplated() {
            this.templated = TRUE;
            return this;
        }

        public Link build() {
            return new Link(rel, href, templated, type, hrefLang, title, name, profile, deprecated);
        }
    }
}