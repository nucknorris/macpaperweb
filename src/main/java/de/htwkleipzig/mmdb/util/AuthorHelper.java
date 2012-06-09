package de.htwkleipzig.mmdb.util;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htwkleipzig.mmdb.model.Author;

public class AuthorHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorHelper.class);

    /**
     * @param author
     * @return
     * @throws IOException
     */
    public static XContentBuilder dao2json(Author author) throws IOException {
        LOGGER.debug("create the json object from Author");
        XContentBuilder b = jsonBuilder().startObject();
        b.field("authorId", author.getAuthorId());
        b.field("title", author.getTitle());
        b.field("name", author.getName());
        b.field("lastName", author.getLastname());
        b.field("email", author.getEmail());
        b.field("universityId", author.getUniversityId());
        b.field("paperIds", author.getPaperIds());
        LOGGER.debug(b.string());
        return b;
    }

    public static Author source2author(Map<String, Object> source) {
        Author author = new Author();
        author.setAuthorId((String) source.get("authorId"));
        author.setTitle((String) source.get("title"));
        author.setName((String) source.get("name"));
        author.setLastname((String) source.get("lastName"));
        author.setEmail((String) source.get("email"));
        author.setUniversityId((String) source.get("universityId"));
        List<String> paperIds = (List<String>) source.get("paperIds");
        LOGGER.debug("counted papers {}", paperIds.size());
        author.setPaperIds(paperIds);

        return author;
    }
}
