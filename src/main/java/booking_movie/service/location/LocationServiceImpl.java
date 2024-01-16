package booking_movie.service.location;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.dto.response.LocationResponseDto;
import booking_movie.entity.Location;
import booking_movie.exception.LocationException;
import booking_movie.mapper.LocationMapper;
import booking_movie.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository ;
    private LocationMapper locationMapper ;
    @Override
    public Page<LocationResponseDto> findAll(String search, Pageable pageable) {
        Page<Location> locationPage ;
        if (search.isEmpty()) {
            locationPage = locationRepository.findAllByIsDelete(false, pageable);
        } else  {
            locationPage = locationRepository.findAllByLocationNameContainingIgnoreCaseAndIsDelete(search, false, pageable);
        }
        return locationPage.map(item -> locationMapper.toResponse(item));
    }

    @Override
    public LocationResponseDto findById(Long id) throws LocationException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new LocationException("Location Not Fount"));
        return locationMapper.toResponse(location);
    }

    @Override
    public LocationResponseDto save(LocationRequestDto locationRequestDto) throws LocationException {
        if (locationRepository.existsByLocationName(locationRequestDto.getLocationName())){
            throw new LocationException("Exits LocationName");
        }
        Location location = locationRepository.save(locationMapper.toEntity(locationRequestDto));
        return locationMapper.toResponse(location);
    }

    @Override
    public LocationResponseDto update(Long id, LocationRequestDto locationRequestDto) throws LocationException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new LocationException("Location Not Fount"));
        location.setId(id);
        location.setLocationName(locationRequestDto.getLocationName());
        location.setIsDelete(locationRequestDto.getIsDelete());
        locationRepository.save(location);
        return locationMapper.toResponse(location);
    }
    @Override
    public void delete(Long id) throws LocationException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new LocationException("Location Not Fount"));
        location.setIsDelete(!location.getIsDelete());
        locationRepository.save(location);
    }
}
