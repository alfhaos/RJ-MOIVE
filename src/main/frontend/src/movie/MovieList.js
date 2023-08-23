import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from 'axios';
import './MovieList.css';
import { FiChevronRight } from 'react-icons/fi';

function PrevButton({onClick, currentPage}) {

    return     <div className="circle-prev" onClick={onClick}>
        <FiChevronRight style={{transform: 'rotate(180deg)'}}/>
    </div>;
}
function NextButton({ onClick }) {
    return     <div className="circle-arrow" onClick={onClick}>
        <FiChevronRight />
    </div>;
}
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

function MainList(props) {
    const [movieList, setMovieList] = useState([]);
    const navigate = useNavigate();
    let handleParam = props.movieListDivision;

    //해당 컴포넌트 랜딩시 axios을 통해 영화 목록을 가져옴
    useEffect(() => {
        axios.get('/api/movieList',{params : {handleParam : handleParam}})
            .then( response =>   setMovieList(response.data))
            .catch(error => console.log(error))
    }, []);

    const moviesPerPage = 5;
    const startIndex = (props.currentPage - 1) * moviesPerPage;
    const endIndex = startIndex + moviesPerPage;

    return <div className="swiper-wrapper" style={{transform: 'translate3d(0px, 0px, 0px)'}}>
        {movieList.slice(startIndex, endIndex).map(temp => (
        <div className ="movie">
            <img src={`${process.env.PUBLIC_URL}/movieImg/${temp.movieCd}.jpg`} alt="Movie Poster" />
            <strong>{temp.movieNm}</strong>
            <br/>
            <strong>차트 순위 :{temp.rank}</strong>
            <button className="reserve-button" onClick={() =>
                //Reservation.js으로 화면 이동 및 선택한 영화 정보 넘기기
                navigate("/movieReservation",
                    {state:{"movie":temp}})}>예약하기</button>
        </div>
        ))}
    </div>

}
function MovieList(){
    const [currentPage, setCurrentPage] = useState(1);
    const [showPrevButton, setShowPrevButton] = useState(false); // Add state for visibility
    const [showNextButton, setShowNextButton] = useState(true); // Add state for visibility

    //영화목록 출력 종류
    const [movieListDivision, setMovieListDivision] = useState('btnMovie');

    const handleMovieList = (event) => {
        let targetId= event.target.id;
        event.preventDefault();
        setMovieListDivision(targetId);
    }

    //다음 버튼클릭시 상태설정
    const handleNextButtonClick = () => {
        setCurrentPage(prevPage => prevPage + 1);
        setShowPrevButton(true);
        setShowNextButton(false);
    };

    //이전 버튼클릭시 상태설정
    const handlePrevButtonClick = () => {
        setCurrentPage(prevPage => prevPage - 1);
        setShowNextButton(false);
        setShowPrevButton(false);
    };

    return (
        <div className="MovieContainer">
            <Intro></Intro>
            <div className="movieChartBeScreen_wrap">
                <div className="contents">
                    <div className="movieChartBeScreen_btn_wrap">
                        <div className="tabBtn_wrap">
                            <h3><a className={movieListDivision === 'btnMovie' ? 'active' : ''}
                                   id="btnMovie"
                                   onClick={handleMovieList}>무비차트</a></h3>

                            <h3><a className={movieListDivision === 'btnReserMovie' ? 'active' : ''}
                                   id="btnReserMovie"
                                   onClick={handleMovieList}>최신순</a></h3>
                        </div>
                    </div>
                    <div className="swiper movieChart_list swiper-container-initialized swiper-container-horizontal" id="movieChart_list">
                        <MainList key = {movieListDivision} currentPage = {currentPage} setCurrentPage = {setCurrentPage} movieListDivision = {movieListDivision}></MainList>
                    </div>
                    {showNextButton && (<NextButton  onClick={handleNextButtonClick} />)}
                    {showPrevButton  && (<PrevButton onClick={handlePrevButtonClick} />)}
                </div>
            </div>
        </div>
    );
}

export default MovieList;