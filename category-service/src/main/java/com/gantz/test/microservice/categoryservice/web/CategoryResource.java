package com.gantz.test.microservice.categoryservice.web;

import com.gantz.test.microservice.categoryservice.domain.Category;
import com.gantz.test.microservice.categoryservice.dto.CategoryDTO;
import com.gantz.test.microservice.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryResource {

    private final CategoryRepository categoryRepository;

    @GetMapping("/page")
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> all = categoryRepository.findAll(pageable);
        return new PageImpl<>(
                all.stream().map(p -> new CategoryDTO(p.getId(), p.getTitle())).collect(Collectors.toList()),
                all.getPageable(),
                all.getTotalElements()
        );
    }

    @GetMapping("/list")
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(p -> new CategoryDTO(p.getId(), p.getTitle()))
                .sorted(Comparator.comparing(CategoryDTO::getId))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Integer id) {
        return categoryRepository.findById(id)
                .map(p -> new CategoryDTO(p.getId(), p.getTitle()))
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("There is no id"));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> findAllByIdIn(@RequestParam("ids") List<Integer> ids) {
        List<CategoryDTO> categoryDTOS = Optional.ofNullable(ids).filter(f -> !f.isEmpty())
                .map(categoryRepository::findAllById)
                .map(list -> list.stream().map(p -> new CategoryDTO(p.getId(), p.getTitle())).toList())
                .orElseGet(Collections::emptyList);
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
        return Optional.ofNullable(categoryDTO)
                .filter(f -> Objects.isNull(f.getId()))
                .map(p -> new Category(p.getTitle()))
                .map(categoryRepository::save)
                .map(p -> new CategoryDTO(p.getId(), p.getTitle()))
                .map(p -> new ResponseEntity<>(p, HttpStatus.CREATED))
                .orElseThrow(() -> new RuntimeException("id must be not null"));
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO) {
        return Optional.ofNullable(categoryDTO)
                .filter(f -> Objects.nonNull(f.getId()))
                .flatMap(dto -> categoryRepository.findById(dto.getId())
                        .map(category -> {
                            category.setTitle(dto.getTitle());
                            return categoryRepository.save(category);
                        })
                )
                .map(p -> new CategoryDTO(p.getId(), p.getTitle()))
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("there is no videoInfo with id"));
    }

}
