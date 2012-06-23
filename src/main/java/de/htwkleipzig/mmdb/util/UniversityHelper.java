package de.htwkleipzig.mmdb.util;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htwkleipzig.mmdb.model.University;

public class UniversityHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityHelper.class);

    public static XContentBuilder university2Json(University university) throws IOException {
        LOGGER.debug("create the json object from University");
        XContentBuilder b = jsonBuilder().startObject();
        b.field("universityId", university.getUniversityId() == null ? "empty" : university.getUniversityId());
        b.field("city", university.getCity() == null ? "empty" : university.getCity());
        b.field("country", university.getCountry() == null ? "empty" : university.getCountry());
        b.field("name", university.getName() == null ? "empty" : university.getName());
        b.field("housenumber", university.getHousenumber() == null ? "empty" : university.getHousenumber());
        b.field("postcode", university.getPostcode() == null ? "empty" : university.getPostcode());
        b.field("street", university.getStreet() == null ? "empty" : university.getStreet());
        b.field("street2", university.getStreet2() == null ? "empty" : university.getStreet2());

        List<String> authorIds = new ArrayList<String>();
        authorIds.add("empty");
        b.field("authors", university.getAuthorIds() == null ? authorIds : university.getAuthorIds());
        LOGGER.debug(b.string());
        return b;
    }

    public static University source2University(Map<String, Object> source) {
        LOGGER.debug("convert from source to university");
        University university = new University();
        university.setUniversityId((String) source.get("universityId"));
        university.setCity((String) source.get("city"));
        university.setCountry((String) source.get("country"));
        university.setName((String) source.get("name"));
        university.setHousenumber((String) source.get("housenumber"));
        university.setPostcode((String) source.get("postcode"));
        university.setStreet((String) source.get("street"));
        university.setStreet2((String) source.get("street2"));
        university.setAuthorIds((List<String>) source.get("authors"));
        LOGGER.debug("finished converting {}", university.toString());
        return university;
    }
}
