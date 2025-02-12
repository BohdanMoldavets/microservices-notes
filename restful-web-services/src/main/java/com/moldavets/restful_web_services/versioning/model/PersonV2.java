package com.moldavets.restful_web_services.versioning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.tomcat.util.codec.binary.StringUtils;

@Getter
@ToString
@AllArgsConstructor
public class PersonV2 {
    private Name name;
}
