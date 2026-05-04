package com.cyanide9102.catalogservice.book.mapper;

import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;
import com.cyanide9102.catalogservice.book.entity.Book;
import com.cyanide9102.catalogservice.category.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    Book toEntity(BookRequest request, Category category);

    @Mapping(target = "categoryId", source = "category.id")
    BookResponse fromEntity(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Book entity, BookRequest request, Category category);
}
