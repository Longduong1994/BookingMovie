package booking_movie.controller;

import booking_movie.entity.Category;
import booking_movie.exception.CategoryException;
import booking_movie.exception.UserException;
import booking_movie.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking/v1/category")
@AllArgsConstructor

public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() throws CategoryException, UserException {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
}
