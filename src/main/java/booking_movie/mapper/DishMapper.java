package booking_movie.mapper;

import booking_movie.dto.request.dish.DishRequestDto;
import booking_movie.dto.request.dish.DishUpdateRequestDto;
import booking_movie.entity.Dish;
import booking_movie.exception.DishException;
import booking_movie.repository.CategoryRepository;
import booking_movie.repository.DishRepository;
import booking_movie.service.upload_image.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DishMapper {
    private final CategoryRepository categoryRepository;
    private final DishRepository dishRepository;
    private final UploadFileService uploadFileService;
    public  Dish dishRequestDtoIntoDish(DishRequestDto dishRequestDto, String useName) {
        return Dish.builder()
                .dishName(dishRequestDto.getDishName())
                .category(categoryRepository.findById(dishRequestDto.getCategoryId()).get())
                .price(dishRequestDto.getPrice())
                .sold(0)
                .image(uploadFileService.uploadFile(dishRequestDto.getImage()))
                .createUser(useName)
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .isDelete(false).build();
    }
    public Dish dishUpdateRequestDtoInto(DishUpdateRequestDto dishUpdateRequestDto, String userName) throws DishException{
        Optional<Dish> dish = dishRepository.findById(dishUpdateRequestDto.getId());
        if(dish.isPresent()){
            Dish dish1 = dish.get();
            dish1.setDishName(dishUpdateRequestDto.getDishName());
            dish1.setCategory(categoryRepository.findById(dishUpdateRequestDto.getCategoryId()).get());
            dish1.setImage(uploadFileService.uploadFile(dishUpdateRequestDto.getImage()));
            dish1.setPrice(dishUpdateRequestDto.getPrice());
            dish1.setUpdateTime(LocalDate.now());
            dish1.setUpdateUser(userName);
            return dish1;
        }throw new DishException("Sản phẩm không tồn tại");
    }
}
