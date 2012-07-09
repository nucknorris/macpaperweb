/**
 * 
 */
package de.htwkleipzig.mmdb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author spinner0815
 * 
 */
public class Paper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 275195800963878375L;
    private String paperId;
    private String title;
    private String paperAbstract;
    private List<String> authorIds;

    private Date createDate;
    private List<String> keywords;
    private String kindOf;
    private String content;
    private String latexBib;
    private Date uploadDate;
    private String fileName;
    private DeweyDecimalClassification ddc;

    public List<String> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<String> authorIds) {
        this.authorIds = authorIds;
    }

    /**
     * @return the latexBib
     */
    public String getLatexBib() {
        return latexBib;
    }

    /**
     * @param latexBib
     *            the latexBib to set
     */
    public void setLatexBib(String latexBib) {
        this.latexBib = latexBib;
    }

    /**
     * @return the title
     */
    @ModelAttribute("paper")
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the keywords
     */
    public List<String> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<String>();
        }
        return keywords;
    }

    /**
     * @param keywords
     *            the keywords to set
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the kindOf
     */
    public String getKindOf() {
        return kindOf;
    }

    /**
     * @param kindOf
     *            the kindOf to set
     */
    public void setKindOf(String kindOf) {
        this.kindOf = kindOf;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the uploadDate
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate
     *            the uploadDate to set
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return the ddc
     */
    public DeweyDecimalClassification getDdc() {
        return ddc;
    }

    /**
     * @param ddc
     *            the ddc to set
     */
    public void setDdc(DeweyDecimalClassification ddc) {
        this.ddc = ddc;
    }

    /**
     * @return the paperId
     */
    @ModelAttribute("paper")
    public String getPaperId() {
        return paperId;
    }

    /**
     * @param paperId
     *            the paperId to set
     */
    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the paperAbstract
     */
    @ModelAttribute("paper")
    public String getPaperAbstract() {
        return paperAbstract;
    }

    /**
     * @param paperAbstract
     *            the paperAbstract to set
     */
    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Paper [paperId=");
        builder.append(paperId);
        builder.append(", title=");
        builder.append(title);
        builder.append(", paperAbstract=");
        builder.append(paperAbstract);
        builder.append(", authorsId=");
        builder.append(authorIds);
        builder.append(", createDate=");
        builder.append(createDate);
        builder.append(", keywords=");
        builder.append(keywords);
        builder.append(", kindOf=");
        builder.append(kindOf);
        builder.append(", content=");
        builder.append(content);
        builder.append(", latexBib=");
        builder.append(latexBib);
        builder.append(", uploadDate=");
        builder.append(uploadDate);
        builder.append(", fileName=");
        builder.append(fileName);
        builder.append(", ddc=");
        builder.append(ddc);
        builder.append("]");
        return builder.toString();
    }

}
