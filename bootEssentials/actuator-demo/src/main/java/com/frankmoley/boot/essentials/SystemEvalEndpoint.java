package com.frankmoley.boot.essentials;

import lombok.Data;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Endpoint(id = "system-details")
public class SystemEvalEndpoint {

    @ReadOperation
    public SystemDetails getSystemDetails() {
        SystemDetails details = new SystemDetails();
        details.systemTime= LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return details;
    }

    @Data
    public class SystemDetails{
        private String systemTime;
    }
}
