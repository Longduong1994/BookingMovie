package booking_movie.service.location;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.dto.response.LocationResponseDto;
import booking_movie.entity.Location;
import booking_movie.exception.CustomsException;
import booking_movie.mapper.LocationMapper;
import booking_movie.repository.ChairRepository;
import booking_movie.repository.LocationRepository;
import booking_movie.security.user_principle.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository ;
    private ChairRepository chairRepository ;
    private LocationMapper locationMapper ;

    @Override
    public List<LocationResponseDto> finAllNoSearch() {
        List<Location> list = locationRepository.findAll();
        return list.stream().map(item -> locationMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public Page<LocationResponseDto> findAll(String search, Pageable pageable) {
        Page<Location> locationPage ;
        if (search.isEmpty()) {
            locationPage = locationRepository.findAllByIsDeleteFalse( pageable);
        } else  {
            locationPage = locationRepository.findAllByLocationNameContainingIgnoreCaseAndIsDeleteFalse(search,  pageable);
        }
        return locationPage.map(item -> locationMapper.toResponse(item));
    }

    @Override
    public LocationResponseDto findById(Long id) throws CustomsException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new CustomsException("Location Not Fount"));
        return locationMapper.toResponse(location);
    }

    @Override
    public LocationResponseDto save(Authentication authentication, LocationRequestDto locationRequestDto) throws CustomsException {
        if (locationRepository.existsByLocationName(locationRequestDto.getLocationName())){
            throw new CustomsException("Tên vị trí đã tồn tại");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Location location = locationMapper.toEntity(locationRequestDto);
        location.setCreateTime(LocalDateTime.now());
        location.setUpdateTime(LocalDateTime.now());
        location.setCreateUser(userPrincipal.getUsername());
        locationRepository.save(location);
        return locationMapper.toResponse(location);
    }

    @Override
    public LocationResponseDto update(Authentication authentication,Long id, LocationRequestDto locationRequestDto) throws CustomsException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new CustomsException("Vị trí không tồn tại"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        location.setId(id);
        location.setLocationName(locationRequestDto.getLocationName());
        location.setUpdateTime(LocalDateTime.now());
        location.setUpdateUser(userPrincipal.getUsername());
        location.setIsDelete(locationRequestDto.getIsDelete());
        locationRepository.save(location);
        return locationMapper.toResponse(location);
    }
    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new CustomsException("Vị trí không tồn tại"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        location.setIsDelete(!location.getIsDelete());
        location.setUpdateTime(LocalDateTime.now());
        location.setUpdateUser(userPrincipal.getUsername());
        locationRepository.save(location);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Location> list = locationRepository.findAllByIsDeleteTrueAndUpdateTimeBefore(oneMonthAgo);
        locationRepository.deleteAll(list);
    }
}
