package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.Mapper;
import com.datajpa.relationship.dto.requestdto.AuthorRequestDto;
import com.datajpa.relationship.dto.responsedto.AuthorResponseDto;
import com.datajpa.relationship.persistence.entity.Author;
import com.datajpa.relationship.persistence.entity.Zipcode;
import com.datajpa.relationship.persistence.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ZipcodeService zipcodeService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ZipcodeService zipcodeService) {
        this.authorRepository = authorRepository;
        this.zipcodeService = zipcodeService;
    }

    @Transactional
    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        if (authorRequestDto.getZipCodeId() == null)
            throw new IllegalArgumentException("Author need a zipcode");
        Zipcode zipcode = zipcodeService.getZipcode(authorRequestDto.getZipCodeId());
        author.setZipcode(zipcode);
        authorRepository.save(author);
        return Mapper.authorToAuthorResponseDto(author);
    }

    @Override
    public List<AuthorResponseDto> getAuthors() {
        List<Author> authors = StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return Mapper.authorsToAuthorsResponseDtos(authors);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
        return Mapper.authorToAuthorResponseDto(getAuthor(authorId));
    }

    @Override
    public Author getAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(()->
                        new IllegalArgumentException(
                                "Author with id: " + authorId + " could not be found"));
        return author;
    }

    @Override
    public AuthorResponseDto deleteAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        authorRepository.delete(author);
        return Mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        Author authorEdit = getAuthor(authorId);
        authorEdit.setName(authorRequestDto.getName());
        if(authorRequestDto.getZipCodeId() != null) {
            Zipcode zipcode = zipcodeService.getZipcode(authorRequestDto.getZipCodeId());
            authorEdit.setZipcode(zipcode);
        }
        return Mapper.authorToAuthorResponseDto(authorEdit);
    }

    @Transactional
    @Override
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId) {
        Author author = getAuthor(authorId);
        Zipcode zipcode = zipcodeService.getZipcode(zipcodeId);
        if(Objects.nonNull(author.getZipcode()))
            throw new RuntimeException("Author already has a zipcode");
        author.setZipcode(zipcode);
        return Mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        author.setZipcode(null);
        return Mapper.authorToAuthorResponseDto(author);
    }
}
