import React, {Fragment, useState} from 'react';
import {useLocation} from "react-router-dom";
import './Reservation.css';
import axios from "axios";
import CheckModal from '../modal/CheckModal';
import SaveModal from '../modal/SaveModal';

function ReservationBtn(prop) {
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
        time : prop.reservationInfo.get("time"),
        movieCd : prop.reservationInfo.get("movieCd"),
        movieNm : prop.reservationInfo.get("movieNm"),
        seat : prop.reservationInfo.get("seat")
    };

    const setReservationContent = () => {

        let content = (
            <div>
                영화 : {params.movieNm}<br />
                상영시간 : {params.time}<br />
                좌석 : {params.seat}<br />
                예약을 진행하겠습니까?
            </div>
        );

        return content;
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


    return <div>
        <button className="reserve-button" onClick={(event) => {
            chkInfo();
        }}>Reserve</button>
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
                //예약된 row가있을시 좌석 확인 후 예약 불가 style 설정
                if(rsvSeatMap[row]){
                    // 특정행에 예약된 좌석이 있을때
                    if(rsvSeatMap[row].includes(String(i))){
                        seatCells.push(<td className="seat unavailable">{row}{i}</td>);
                    } else {
                        seatCells.push(<td className="seat available" onClick={() => {
                            seatClick(row, i);

                        }}>{row}{i}</td>);
                    }
                } else {
                    // 특정행에 예약된 좌석이 없을떄
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
        return <div className="seat-selection">
            <h2>좌석 선택</h2>
            <div className="seat-layout">
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
            </div>
        </div>;
    }
}
function MovieDetail(prop) {

    const movieCd = prop.movie.movieCd;
    //부모 컴포넌트의 영화 정보 map 값 set
    const upDateMovieDetail = new Map(prop.reservationInfo)
    upDateMovieDetail.set("movieCd", movieCd);
    upDateMovieDetail.set("movieNm", prop.movie.movieNm);

    const printSeat = (e) => {

        //선택한 상영시간
        const selectValue = e.target.value;

        upDateMovieDetail.set("time", selectValue);
        prop.updateReservationInfo(upDateMovieDetail);

        if(selectValue !== '0'){
            axios.get('/api/movieInfo/seatList', {
                params: {
                    time : selectValue,
                    movieCd :movieCd
                }
            })
                .then(response => {
                    prop.onChangeMode(response.data);
                })
                .catch(error => console.log(error))
        }

    }

    return <div className="movie-details">
        <h2>{prop.movie.movieNm}</h2>
        <p>순위: {prop.movie.rank}</p>
        <p>개봉일: {prop.movie.openDt}</p>
        <p>상영시간:
            <select  className="form-select" aria-label="Default select example" onChange={printSeat}>
                <option value='0'>선택</option>
                <option value='09:30'>09:30</option>
                <option value='11:30'>11:30</option>
                <option value='13:30'>13:30</option>
                <option value='15:30'>15:30</option>
                <option value='17:30'>17:30</option>
            </select></p>
</div>
}

function Reservation(){

    //MovieList.js 에서 예약하기 버튼을 통해 넘어온 영화 데이터
    const location = useLocation();
    const movie = location.state.movie;
    const [seatList, setSeatList] = useState([]);
    const [seatVisble,setSeatVisble] = useState(false);
    const [reservationInfo, setReservationInfo] = useState(new Map());
    // const [modalOpen, setModalOpen] = useState(false);
    //
    // const openModal = () => {
    //     setModalOpen(true);
    // };
    //
    // const closeModal = () => {
    //     setModalOpen(false);
    // };

    const updateReservationInfo = (newInfo) => {
        setReservationInfo(newInfo);
    };

    return (
        <div className="container">
                <h1>영화 예약</h1>
                <img className = "movieImg" src="https://img.cgv.co.kr/Movie/Thumbnail/Poster/000087/87034/87034_320.jpg" alt="영화이미지"/>
                <MovieDetail movie = {movie}
                             reservationInfo = {reservationInfo}
                             updateReservationInfo = {updateReservationInfo}
                             setReservationInfo = {setReservationInfo}
                             onChangeMode = {(_seatList) => {
                    setSeatList(_seatList);
                    setSeatVisble(true);
                }}></MovieDetail>
            <SeatList reservationInfo = {reservationInfo}
                      updateReservationInfo = {updateReservationInfo}
                      RsvSeatList = {seatList}
                      movie = {movie} SeatVisble = {seatVisble}></SeatList>
            <p></p>
            <ReservationBtn reservationInfo = {reservationInfo}></ReservationBtn>
        </div>
    );
};

export default Reservation;