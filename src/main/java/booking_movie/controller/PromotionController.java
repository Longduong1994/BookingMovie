package booking_movie.controller;

import booking_movie.dto.request.promotion.PromotionRequestDto;
import booking_movie.dto.request.promotion.PromotionUpdateRequestDto;
import booking_movie.exception.PromtionException;
import booking_movie.exception.UserException;
import booking_movie.service.promotion.PromotionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/promotion")
@AllArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createPromotion(Authentication authentication,@Valid @ModelAttribute PromotionRequestDto promotionRequestDto) throws PromtionException, UserException {
        return new ResponseEntity<>(promotionService.create(promotionRequestDto,authentication), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPromotion(@PathVariable("id") Long id) throws PromtionException {
        return new ResponseEntity<>(promotionService.findById(id),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "6") Integer size,
                                    Authentication authentication) throws PromtionException, UserException {
        return new ResponseEntity<>(promotionService.findAll(page,size,search,authentication),HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAllList(){
        return  new ResponseEntity<>(promotionService.findAllList(),HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> edit(@Valid @RequestBody PromotionUpdateRequestDto promotionUpdateRequestDto,Authentication authentication) throws PromtionException, UserException {
        System.out.println(promotionUpdateRequestDto);
        return new ResponseEntity<>(promotionService.update(promotionUpdateRequestDto, authentication),HttpStatus.OK);
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, Authentication authentication) throws PromtionException, UserException {
        return new ResponseEntity<>(promotionService.delete(id,authentication),HttpStatus.OK);
    }
}
