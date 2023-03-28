package com.example.kursach.controllers;

import com.example.kursach.models.Course;
import com.example.kursach.repositories.CourseRepository;
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
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public String allCourses(Model model) {
        model.addAttribute("title", "Курсы");
        Iterable<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/courses/add")
    public String addCourse(Model model) {
        return "courses-add";
    }

    @PostMapping("/courses/add")
    public String addCoursePost(@RequestParam String name, @RequestParam String language, @RequestParam String level,
                                @RequestParam int quantity_of_students, @RequestParam int age_of_group,
                                @RequestParam double price, @RequestParam String description, Model model) {
        Course course = new Course(name, language, level, quantity_of_students, age_of_group, price, description);
        courseRepository.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{id}")
    public String courseInfo(@PathVariable(value = "id") int id, Model model) {
        if(!courseRepository.existsById(id)){
            return "redirect:/courses";
        }
        Optional<Course> course = courseRepository.findById(id);
        ArrayList<Course> result = new ArrayList<>();
        course.ifPresent(result::add);
        model.addAttribute("course", result);
        return "course-info";
    }

    @GetMapping("/courses/{id}/edit")
    public String editCourse(@PathVariable(value = "id") int id, Model model) {
        if(!courseRepository.existsById(id)){
            return "redirect:/courses";
        }
        Optional<Course> course = courseRepository.findById(id);
        ArrayList<Course> result = new ArrayList<>();
        course.ifPresent(result::add);
        model.addAttribute("course", result);
        return "course-edit";
    }

    @PostMapping("/courses/{id}/edit")
    public String editCoursePost(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String language,
                                 @RequestParam String level, @RequestParam int quantity_of_students,
                                 @RequestParam int age_of_group, @RequestParam double price, @RequestParam String description,
                                 Model model) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setName(name);
        course.setLanguage(language);
        course.setLevel(level);
        course.setQuantity_of_students(quantity_of_students);
        course.setAge_of_group(age_of_group);
        course.setPrice(price);
        course.setDescription(description);
        courseRepository.save(course);
        return "redirect:/courses";
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCoursePost(@PathVariable(value = "id") int id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow();
        courseRepository.delete(course);
        return "redirect:/courses";
    }
}
