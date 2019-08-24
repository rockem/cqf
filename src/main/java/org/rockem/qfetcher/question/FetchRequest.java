package org.rockem.qfetcher.question;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class FetchRequest {

    private String manifest;
    private List<String> filter;
}
