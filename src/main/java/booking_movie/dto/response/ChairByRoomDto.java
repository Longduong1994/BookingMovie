package booking_movie.dto.response;

import booking_movie.constants.ChairType;

public class ChairByRoomDto {
    private Long id;
    private String chairName;
    private ChairType chairType;
    private String status;
    private Boolean isDeleted;


    public ChairByRoomDto() {
    }

    public ChairByRoomDto(Long id, String chairName, ChairType chairType, String status, Boolean isDeleted) {
        this.id = id;
        this.chairName = chairName;
        this.chairType = chairType;
        this.status = status;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }

    public ChairType getChairType() {
        return chairType;
    }

    public void setChairType(ChairType chairType) {
        this.chairType = chairType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
