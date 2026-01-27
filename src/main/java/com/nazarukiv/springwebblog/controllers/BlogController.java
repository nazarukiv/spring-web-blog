package com.nazarukiv.springwebblog.controllers;

import com.nazarukiv.springwebblog.models.Post;
import com.nazarukiv.springwebblog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> postIterable = postRepository.findAll();
        model.addAttribute("posts", postIterable);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        model.addAttribute("post", post);
        return "blog-details";
    }

}
