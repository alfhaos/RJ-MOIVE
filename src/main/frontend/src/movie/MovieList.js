
import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import axios from 'axios';
import './MovieList.css';

function Intro() {
    return (
        <div id="ctl00_PlaceHolderContent_divMovieSelection_wrap" className="movieSelection_wrap">
            <div className="contents">
                <div className="video_wrap">
                    <video autoPlay muted loop>
                        <source src="https://adimg.cgv.co.kr/images/202308/OperationFortune/0802_1080x608.mp4" type="video/mp4"/>
                    </video>

                    <strong id="ctl00_PlaceHolderContent_AD_MOVIE_NM" className="movieSelection_title">스파이 코드명 포춘</strong>
                    <span id="ctl00_PlaceHolderContent_AD_DESCRIPTION_NM" className="movieSelection_txt">넥스트 레벨 '제이슨 스타뎀'이 온다
                        <br />
                        8월 30일 스파이 포춘 작전개시!
                    </span>
                </div>
            </div>
        </div>
    );
}

function MainList() {
    const [movieList, setMovieList] = useState([]);
    const movies = [];
    const navigate = useNavigate();

    //해당 컴포넌트 랜딩시 axios을 통해 영화 목록을 가져옴
    useEffect(() => {
        axios.get('/api/movieList')
            .then(response => setMovieList(response.data))
            .catch(error => console.log(error))
    }, []);

    for(let i = 0; i<movieList.length ; i++) {
        let temp = movieList[i];
        movies.push(
            <div class ="movie">
                <img src={`${process.env.PUBLIC_URL}/movieImg/${temp.movieCd}.jpg`} alt="Movie Poster" />
                <h2>{temp.movieNm}</h2>
                <p>개봉일 : {temp.openDt}</p>
                <button className="reserve-button" onClick={() =>
                    //Reservation.js으로 화면 이동 및 선택한 영화 정보 넘기기
                    navigate("/movieReservation",
                    {state:{"movie":temp}})}>예약하기</button>
            </div>
        )
    }

    return <div>
        {movies}
    </div>

}
function MovieList(){
    return (
        <div className="MovieContainer">
            <Intro></Intro>
            <h2>무비 차트</h2>
            <div className="MovieList">
                <MainList></MainList>
            </div>
        </div>
    );
}



export default MovieList;