package com.moldavets.restful_web_services.versioning;

import com.moldavets.restful_web_services.versioning.model.Name;
import com.moldavets.restful_web_services.versioning.model.PersonV1;
import com.moldavets.restful_web_services.versioning.model.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonRestController {

    @GetMapping("/v1/persons")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("John Doe");
    }

    @GetMapping("/v2/persons")
    public PersonV2 getSecondVersionOfPerson() {
        return new PersonV2(new Name("John", "Doe"));
    }

    @GetMapping(path = "/persons", params = "version=1")
    public PersonV1 getFirstVersionOfPersonByRequestParam() {
        return new PersonV1("John Doe");
    }

    @GetMapping(path = "/persons", params = "version=2")
    public PersonV2 getSecondVersionOfPersonByRequestParam() {
        return new PersonV2(new Name("John", "Doe"));
    }

    @GetMapping(path = "/persons/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonByHeader() {
        return new PersonV1("John Doe");
    }

    @GetMapping(path = "/persons/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonByHeader() {
        return new PersonV2(new Name("John", " Doe"));
    }

    @GetMapping(path = "/persons/header", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonByAcceptHeader() {
        return new PersonV1("John Doe");
    }

    @GetMapping(path = "/persons/header", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonByAcceptHeader() {
        return new PersonV2(new Name("John", "Doe"));
    }
}
