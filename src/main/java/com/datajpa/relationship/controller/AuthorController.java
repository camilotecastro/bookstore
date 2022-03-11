package com.datajpa.relationship.controller;

import com.datajpa.relationship.dto.requestdto.AuthorRequestDto;
import com.datajpa.relationship.dto.requestdto.ZipCodeRequestDto;
import com.datajpa.relationship.dto.responsedto.AuthorResponseDto;
import com.datajpa.relationship.persistence.entity.Author;
import com.datajpa.relationship.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public ResponseEntity<AuthorResponseDto> addAuthor(@RequestBody final AuthorRequestDto authorRequestDto){
        AuthorResponseDto authorResponseDto = authorService.addAuthor(authorRequestDto);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable final Long id){
        AuthorResponseDto authorResponseDto = authorService.getAuthorById(id);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AuthorResponseDto>> getAuthors(){
        List<AuthorResponseDto> authorsResponseDtos = authorService.getAuthors();
        return new ResponseEntity<List<AuthorResponseDto>>(authorsResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<AuthorResponseDto> deleteAuthor(@PathVariable final Long id){
        AuthorResponseDto authorResponseDto = authorService.deleteAuthor(id);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AuthorResponseDto> editAuthor(@RequestBody final AuthorRequestDto authorRequestDto,
                                                        @PathVariable final Long id){
        AuthorResponseDto authorResponseDto = authorService.editAuthor(id, authorRequestDto);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

    @PostMapping("/addZipcode/{zipcodeId}/to{authorId}")
    public ResponseEntity<AuthorResponseDto> addZipcode(@PathVariable final Long zipcodeId,
                                                                @PathVariable final Long authorId){
        AuthorResponseDto authorResponseDto = authorService.addZipcodeToAuthor(authorId, zipcodeId);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);

    }

    @DeleteMapping("/removeZipcode/{id}")
    public ResponseEntity<AuthorResponseDto> removeZipcode(@PathVariable final Long id){
        AuthorResponseDto authorResponseDto = authorService.deleteZipcodeFromAuthor(id);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

}
