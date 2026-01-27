package com.nazarukiv.springwebblog.controllers;

import com.nazarukiv.springwebblog.models.Post;
import com.nazarukiv.springwebblog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("post not found"));

        model.addAttribute("post", post);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(
            @PathVariable long id,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text
    ) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("post not found"));

        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);

        postRepository.save(post);

        return "redirect:/blog/" + id;
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable long id) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        postRepository.deleteById(id);
        return "redirect:/blog";
    }

}
