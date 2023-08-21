import React, {useState} from 'react';
import {useLocation} from "react-router-dom";
import './Reservation.css';
import axios from "axios";
import CheckModal from '../modal/CheckModal';
import SaveModal from '../modal/SaveModal';
import moment from 'moment';
import 'moment/locale/ko';

function MovieHeader(props) {

    //모달 관련 state
    const [chkModalOpen, setChkModalOpen] = useState(false);
    const [saveModalOpen, setSaveModalOpen] = useState(false);
    const [content, setContent] = useState("");

    const modalSaveEvent = () => {
        kakaoPay(true);
    }
    const closeModal = () => {
        setChkModalOpen(false);
        setSaveModalOpen(false);

    }

    const params = {
        time : props.reservationInfo.get("time"),
        movieCd : props.reservationInfo.get("movieCd"),
        movieNm : props.reservationInfo.get("movieNm"),
        seat : props.reservationInfo.get("seat")
    };

    const setReservationContent = () => {

        return (
            <div>
                영화 : {params.movieNm}<br/>
                상영시간 : {params.time}<br/>
                좌석 : {params.seat}<br/>
                예약을 진행하겠습니까?
            </div>
        );
    }

    const chkInfo = () => {
        if(params.seat){
            setContent(setReservationContent());
            setChkModalOpen(false);
            setSaveModalOpen(true);
        } else {
            setContent("선택된 좌석이 없습니다.");
            setChkModalOpen(true);
            setSaveModalOpen(false);
        }
    }

    const kakaoPay = (isModalSave) => {
        if(isModalSave) {
            axios.post("/api/rsv/kakaoPay",params)
                .then(response =>
                    window.location.href = response.data

                )
                .catch(error => alert("좌석 예약중 오류가 발생했습니다 다시 시도해주세요."))
        }
    }

    return <div id="ticket_tnb" className="tnb_container ">
                    <a className="btn-left" href="http://localhost:3000/" title="영화선택">이전단계로 이동</a>
                        <div className="tnb step1">
                            <div className="info theater">
                                <div className="row date">
                                    <span className="header">인원 1명</span>
                                </div>
                                <div className="row screen">
                                    <span className="header">가격 5000원</span>
                                </div>
                            </div>

                            <div className="info path">
                                <div className="row colspan4">
                                    <span className="path-step1" title="영화선택">&nbsp;</span>
                                    <span className="path-step2" title="좌석선택">&nbsp;</span>
                                    <span className="path-step3" title="결제">&nbsp;</span>
                                </div>
                            </div>
                        </div>
                    <a className="btn-right on" id="tnb_step_btn_right" onClick={(event) => {
                        chkInfo();
                    }} title="결제선택">.</a>
        { chkModalOpen &&(<CheckModal isOpen = {chkModalOpen} content = {content} closeModal = {closeModal}/>)}
        { saveModalOpen && (<SaveModal isOpen = {saveModalOpen} content = {content} closeModal = {closeModal} modalSaveEvent = {modalSaveEvent}></SaveModal>)}
    </div>
}

function SeatList(props) {

    const rsvSeatMap = {};

    const trList = [];
    const seatRows = ['A', 'B', 'C', 'D', 'E'];

    const seatClick = (row, seat) => {
        const selected = `${row}${seat}`;
        const upDateSeat = new Map(props.reservationInfo)
        upDateSeat.set("seat", selected);
        props.updateReservationInfo(upDateSeat);
    }

    props.RsvSeatList.forEach(function(temp) {
        let key = temp.seat.slice(0,1);
        let val = temp.seat.slice(1,3);
        //에약된 좌석 목록을 map<String, List<String>> 형식으로 저장
        rsvSeatMap[key] = rsvSeatMap[key] ? [...rsvSeatMap[key], val] : [val];
    });
    //시간대를 골랐을때 출력되도록 설정
    if(props.SeatVisble === true) {

        // A열 ~ E열 열을 반복 하는 반복문
        seatRows.forEach(function(row) {
            let seatCells = [];

            // 1~10 까지의 좌석을 생성하는 반복문
            for(let i = 1; i < 11 ; i++){
                //예약 행 확인
                if(rsvSeatMap[row]){
                    if(rsvSeatMap[row].includes(String(i))){
                        seatCells.push(<td className="seat unavailable">{row}{i}</td>);
                    } else {
                        seatCells.push(<td className="seat available" onClick={(event) => {
                            seatClick(row, i);
                        }}>{row}{i}</td>);
                    }
                } else {
                    seatCells.push(<td className="seat available" onClick={() => {
                        seatClick(row, i);

                    }}>{row}{i}</td>);
                }
            }
            trList.push(<tr>
                <th>{row}</th>
                {seatCells}
            </tr>)
        });
        return<div className="seat-layout">
                <table style={{float:'left'}}>
                    <thead>
                    <tr>
                        <th colSpan="10">Screen</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        {trList}
                    </tr>
                    </tbody>
                </table>
            </div>;
    }
}
function MovieDetail(props) {

    const movieCd = props.movie.movieCd;
    //부모 컴포넌트의 영화 정보 map 값 set
    const upDateMovieDetail = new Map(props.reservationInfo)

    // 남은좌석수
    const [seatCount, setSeatCount] = useState(0);
    // 선택한 상영시간
    const [selectValue, setSelectValue] = useState('0');
    //오늘날짜
    const currentDate = new Date();
    let formattedDate = moment(currentDate).format('YYYY.MM.DD (dddd)');

    upDateMovieDetail.set("movieCd", movieCd);
    upDateMovieDetail.set("movieNm", props.movie.movieNm);

    const PrintScreeningTime = () => {

        const addedHours = 2; // 더할 시간 (2시간)

        const startTime = moment(selectValue, "HH:mm");
        const endTime = startTime.clone().add(addedHours, "hours");

        const formattedStartTime = startTime.format("HH:mm"); // 예: 10:30
        const formattedEndTime = endTime.format("HH:mm");     // 예: 12:30

        return <p className="playYMD-info2">
            <b>{formattedDate}</b>
            <b>{String(formattedStartTime)} ~ {String(formattedEndTime)}</b>
        </p>
    }

    const printSeat = (e) => {
        //선택한 상영시간
        let selTime = e.target.value;

        setSelectValue(selTime);
        upDateMovieDetail.set("time", selTime);
        props.updateReservationInfo(upDateMovieDetail);

        if(selTime !== '0'){
            axios.get('/api/movieInfo/seatList', {
                params: {
                    time : selTime,
                    movieCd :movieCd
                }
            })
                .then(function(response){
                    props.onChangeMode(response.data);
                    setSeatCount((50 - response.data.length));
                })
                .catch(error => console.log(error))
        }

    }

    return <div className="movie-details">
        <div className="steps">
            <div className="step step2">
                <div className="section section-seat">
                    <div className="col-head" id="skip_seat_list">
                    </div>
                    <div className="col-body">
                        <div className="person_screen">
                            <div className="section section-numberofpeople">
                                <div className="col-body">
                                    <div id="nopContainer" className="numberofpeople-select">
                                        <img src={`${process.env.PUBLIC_URL}/movieImg/${props.movie.movieCd}.jpg`} alt="Movie Poster" />
                                    </div>
                                </div>

                            </div>
                            <div className="section section-screen-select">
                                <div id="user-select-info">
                                    <p className="theater-info">
                                        <span className="site"> {props.movie.movieNm} </span>
                                        <span className="screen">홍길동 님</span>
                                        <span className="seatNum">남은좌석  <b className="restNum">{seatCount}</b>/<b className="totalNum">50</b></span>
                                    </p>
                                    <p className="playYMD-info1">
                                        <span className="time">시간선택 </span>
                                        <select className="form-select" aria-label="Default select example" onChange={printSeat}>
                                            <option value='0'>선택</option>
                                            <option value='09:30'>09:30</option>
                                            <option value='11:30'>11:30</option>
                                            <option value='13:30'>13:30</option>
                                            <option value='15:30'>15:30</option>
                                            <option value='17:30'>17:30</option>
                                        </select>
                                    </p>
                                    {selectValue !== '0' && <PrintScreeningTime></PrintScreeningTime>}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>
}

function Reservation(){

    //MovieList.js 에서 예약하기 버튼을 통해 넘어온 영화 데이터
    const location = useLocation();
    const movie = location.state.movie;
    const [seatList, setSeatList] = useState([]);
    const [seatVisble,setSeatVisble] = useState(false);
    const [reservationInfo, setReservationInfo] = useState(new Map());

    const updateReservationInfo = (newInfo) => {
        setReservationInfo(newInfo);
    };

    return (
        <div>
            <div className="container">
                    <MovieDetail movie = {movie}
                                 reservationInfo = {reservationInfo}
                                 updateReservationInfo = {updateReservationInfo}
                                 setReservationInfo = {setReservationInfo}
                                 onChangeMode = {(_seatList) => {
                        setSeatList(_seatList);
                        setSeatVisble(true);
                    }}></MovieDetail>

                <div className="seat-selection">
                    <SeatList reservationInfo = {reservationInfo}
                              updateReservationInfo = {updateReservationInfo}
                              RsvSeatList = {seatList}
                              movie = {movie}
                              SeatVisble = {seatVisble}>

                    </SeatList>
                </div>
            </div>
            <MovieHeader reservationInfo = {reservationInfo}></MovieHeader>
        </div>
    );
};

export default Reservation;