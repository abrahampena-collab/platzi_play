package com.platzi_play.persistence;

import com.platzi_play.domain.dto.MovieDto;
import com.platzi_play.domain.dto.UpdateMovieDto;
import com.platzi_play.domain.exception.MovieAlreadyExistException;
import com.platzi_play.domain.exception.MovieNotFoundException;
import com.platzi_play.domain.repository.MovieRepository;
import com.platzi_play.persistence.crud.CrudMovieEntity;
import com.platzi_play.persistence.entity.MovieEntity;
import com.platzi_play.persistence.mapper.MovieMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieEntityRepository implements MovieRepository {
    private final CrudMovieEntity crudMovieEntity;
    private final MovieMapper movieMapper;

    public MovieEntityRepository(CrudMovieEntity crudMovieEntity, MovieMapper movieMapper) {
        this.crudMovieEntity = crudMovieEntity;
        this.movieMapper = movieMapper;
    }


    @Override
    public List<MovieDto> getAll() {
        return this.movieMapper.toDto(this.crudMovieEntity.findAll());
    }

    @Override
    public MovieDto getById(long id) {
        // Buscamos la entidad; si existe la asigna, si no, lanza la excepción 404
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        // Si llegó a esta línea, significa que sí existía, por lo que la mapeamos a DTO
        return this.movieMapper.toDto(movieEntity);
    }

    @Override
    public MovieDto save(MovieDto movieDto) {
        if (this.crudMovieEntity.findFirstByTitulo(movieDto.title()) != null) {
            throw new MovieAlreadyExistException(movieDto.title());
        }

        MovieEntity movieEntity = this.movieMapper.toEntity(movieDto);
        movieEntity.setEstado("D");

        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDto update(long id, UpdateMovieDto updateMovieDto) {
        // Si no existe, lanza la excepción de inmediato de forma limpia
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        // Mapea los cambios del DTO a la entidad existente
        this.movieMapper.updateEntityFromDto(updateMovieDto, movieEntity);

        // Guarda los cambios y retorna el DTO actualizado
        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDto delete(long id) {
        // 1. Buscamos la entidad o lanzamos excepción si no existe
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        // 2. Convertimos la entidad a DTO antes de que desaparezca de la BD
        MovieDto movieDto = this.movieMapper.toDto(movieEntity);

        // 3. La eliminamos físicamente de la base de datos
        this.crudMovieEntity.delete(movieEntity);

        // 4. Retornamos el DTO de la película eliminada para cumplir el contrato
        return movieDto;
    }


}
