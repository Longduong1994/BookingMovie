package booking_movie.entity;

import booking_movie.constants.MovieStatus;
import booking_movie.constants.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@Table(name = "MOVIE")
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieName;

    private String movieImage;

    private Double price;

    private String director;

    private String cast;

    private String description;

    private Long runningTime;

    private LocalDate releaseDate;

    private LocalDate stopDate;

    private String language;

    private String rated;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MOVIE_FORMAT",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id"))
    private Set<Format> formats;
  
    private Boolean isDeleted;
  
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MOVIE_GENRE",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus;
}
