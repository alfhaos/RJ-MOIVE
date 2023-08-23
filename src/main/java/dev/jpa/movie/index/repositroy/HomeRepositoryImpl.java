package dev.jpa.movie.index.repositroy;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto;
import dev.jpa.movie.movieInfo.domain.dto.QMovieIndexDto;
import dev.jpa.movie.movieInfo.domain.entity.QMovie;
import jakarta.persistence.EntityManager;

import java.util.List;

import static dev.jpa.movie.movieInfo.domain.entity.QMovie.movie;

public class HomeRepositoryImpl implements HomeRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public HomeRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MovieIndexDto> searchIndexList(String handleParam) {
        return queryFactory
                .select(new QMovieIndexDto(
                        movie.movieCd,
                        movie.movieNm,
                        movie.rank,
                        movie.openDt,
                        movie.audiAcc
                ))
                .from(movie)
                .orderBy(handleParamEq(handleParam))
                .fetch();
    }

    private OrderSpecifier<?> handleParamEq(String handleParam) {


        return handleParam.equals("btnMovie") ? movie.rank.castToNum(Integer.class).asc() : movie.openDt.desc();
    }

}
