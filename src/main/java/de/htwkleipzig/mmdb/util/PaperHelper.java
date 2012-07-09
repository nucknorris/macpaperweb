package de.htwkleipzig.mmdb.util;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.htwkleipzig.mmdb.model.Paper;

public class PaperHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaperHelper.class);

    /**
     * 
     * @param paper
     * @return
     * @throws IOException
     */
    public static XContentBuilder paper2Json(Paper paper) throws IOException {
        LOGGER.debug("create the json object from Paper");
        XContentBuilder b = jsonBuilder().startObject();
        b.field("paperId", paper.getPaperId() == null ? "empty" : paper.getPaperId());
        b.field("title", paper.getTitle() == null ? "empty" : paper.getTitle());
        b.field("paperAbstract", paper.getPaperAbstract() == null ? "empty" : paper.getPaperAbstract());

        List<String> authorIds = new ArrayList<String>();
        authorIds.add("empty");
        b.field("authorsId", paper.getAuthorIds() == null ? authorIds : paper.getAuthorIds());
        b.field("createDate",
                paper.getCreateDate() == null ? new Date(System.currentTimeMillis()) : paper.getCreateDate());

        List<String> keywords = new ArrayList<String>();
        keywords.add("empty");
        b.field("keywords", paper.getKeywords() == null ? keywords : paper.getKeywords());
        b.field("kindOf", paper.getKindOf() == null ? "empty" : paper.getKindOf());
        b.field("content", paper.getContent() == null ? "empty" : paper.getContent());
        b.field("latexBib", paper.getLatexBib() == null ? "empty" : paper.getLatexBib());
        b.field("uploadDate",
                paper.getUploadDate() == null ? new Date(System.currentTimeMillis()) : paper.getUploadDate());
        b.field("fileName", paper.getFileName() == null ? "empty" : paper.getFileName());

        // b.field("ddc", paper.getDdc()); TODO - currently disabled
        LOGGER.debug(b.string());
        return b;
    }

    /**
     * 
     * @param source
     * @return
     */
    public static Paper source2Paper(Map<String, Object> source) {
        LOGGER.debug("convert from source to paper");
        Paper paper = new Paper();
        paper.setPaperId((String) source.get("paperId"));
        paper.setTitle((String) source.get("title"));
        paper.setPaperAbstract((String) source.get("paperAbstract"));
        paper.setAuthorIds((List<String>) source.get("authorsId"));

        DateTime dt = DateTime.parse((String) source.get("createDate"));// 2012-05-27T17:35:05.989Z
        paper.setCreateDate(dt.toDate());
        paper.setKeywords((List<String>) source.get("keywords"));
        paper.setKindOf((String) source.get("kindOf"));
        paper.setContent((String) source.get("content"));
        paper.setLatexBib((String) source.get("latexBib"));

        dt = DateTime.parse((String) source.get("uploadDate"));// 2012-05-27T17:35:05.989Z
        paper.setUploadDate(dt.toDate());

        paper.setFileName((String) source.get("street2"));
        // paper.setDdc((String) source.get("ddc")); TODO - currently disabled
        LOGGER.debug("finished converting {}", paper.toString());
        return paper;
    }
}
