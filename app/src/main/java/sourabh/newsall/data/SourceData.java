package sourabh.newsall.data;

import java.io.Serializable;

/**
 * Created by Downloader on 6/3/2017.
 */


public class SourceData implements Serializable
{
    Integer id_source;
    Integer id_language;
    Integer id_user;
    Integer id_category;
    String source;
    String source_url;

    String source_image;
    Integer source_share_count;
    String source_created_on;
    Integer status;

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public Integer getId_source() {
        return id_source;
    }

    public void setId_source(Integer id_source) {
        this.id_source = id_source;
    }

    public Integer getId_language() {
        return id_language;
    }

    public void setId_language(Integer id_language) {
        this.id_language = id_language;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_category() {
        return id_category;
    }

    public void setId_category(Integer id_category) {
        this.id_category = id_category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_image() {
        return source_image;
    }

    public void setSource_image(String source_image) {
        this.source_image = source_image;
    }

    public Integer getSource_share_count() {
        return source_share_count;
    }

    public void setSource_share_count(Integer source_share_count) {
        this.source_share_count = source_share_count;
    }

    public String getSource_created_on() {
        return source_created_on;
    }

    public void setSource_created_on(String source_created_on) {
        this.source_created_on = source_created_on;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
