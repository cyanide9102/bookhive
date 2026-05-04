package com.cyanide9102.catalogservice.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private UUID id;

    private String isbn;

    private String title;

    private Short stockQuantity;

    private UUID categoryId;
}
