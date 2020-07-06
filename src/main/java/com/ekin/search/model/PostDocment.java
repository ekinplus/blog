package com.ekin.search.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName="post", type="post", createIndex=true)
public class PostDocment implements Serializable {

    @Id
    private Long id;

    // ik分词器
    @Field(type = FieldType.Text, searchAnalyzer="ik_smart", analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Keyword)
    private String userName;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Field(type = FieldType.Date)
    private Date created;


}
