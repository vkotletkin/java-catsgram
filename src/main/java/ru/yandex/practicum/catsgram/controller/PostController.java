package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam String sort,
                                    @RequestParam Long page,
                                    @RequestParam Long size) {
        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new ParameterNotValidException(sort, "Некорректный тип сортировки.");
        }
        if (size <= 0) {
            throw new ParameterNotValidException(size.toString(),
                    "Некорректный размер выборки. Размер должен быть больше нуля.");
        }

        if (page < 0) {
            throw new ParameterNotValidException(page.toString(),
                    "Некорректное место старта. Место должно быть не меньше нуля.");
        }

        return postService.findAll(page, size, sort);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id) {
        return postService.findById(id);
    }

}
