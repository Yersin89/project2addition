package kz.spring.springboot.addition.entities;
import kz.spring.springboot.addition.controllers.Student;
import kz.spring.springboot.addition.db.DbManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 4;
        var allStudents = DbManager.getStudents();
        int totalStudents = allStudents.size();
        int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

        int start = Math.min(page * pageSize, totalStudents);
        int end = Math.min(start + pageSize, totalStudents);
        var studentsPage = allStudents.subList(start, end);

        model.addAttribute("students", studentsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        String previousPage = (page > 0) ? "/?page=" + (page - 1) : null;
        String nextPage = (page < totalPages - 1) ? "/?page=" + (page + 1) : null;
        List<String> pages = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            pages.add("/?page=" + i);
        }
        model.addAttribute("pages", pages);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);

        return "index";
    }
    @GetMapping(value = "/add")
    public String addStudentPage() {
        return "addStudent";
    }

    @PostMapping(value = "/add")
    public String addStudent(@RequestParam String name, @RequestParam String surName, @RequestParam Integer exam) {
        Student student = new Student();
        student.setName(name);
        student.setSurName(surName);
        student.setExam(exam);
        DbManager.addStudent(student);
        return "redirect:/";
    }
}
