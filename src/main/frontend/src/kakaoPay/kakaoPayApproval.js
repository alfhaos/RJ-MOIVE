import React, { useState, useEffect } from 'react';
import "./KakaoPayApproval.css"; // 스타일링을 위한 CSS 파일
import { Container, Table, Button } from "react-bootstrap";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import CheckModal from "../modal/CheckModal"; // 부트스트랩 스타일시트 임포트

function ReservationList (props) {

    const rsvCancel = (reservation) => {

        let params = {
            time : reservation.reservation.time,
            reservationDate : reservation.reservation.reservationDate,
            screeningTime : reservation.reservation.screeningTime,
            price : reservation.reservation.price,
            seat : reservation.reservation.seat,
            delYn : reservation.reservation.delYn,
            movieCd : reservation.reservation.movieCd,
            reservationId : reservation.reservation.reservationId,
            accountId : reservation.reservation.accountId
        }

        axios.post("/api/rsv/cancel",params)
            .then(response =>
                props.updateModalOpen(true),
            )
            .catch(error => alert("좌석 예약중 오류가 발생했습니다 다시 시도해주세요."))
    };

    return  <div className="sect-movielog-lst">
        <ul id="watched_list_container">
            {props.reservations.map(reservation => (
                <li>
                    <div className="article-movie-info">
                        <div className="box-image">
                            <span className="thumb-image">
                               <img
                                   src={`${process.env.PUBLIC_URL}/movieImg/${reservation.movieCd}.jpg`}
                                   alt="Movie Poster" />
                            </span>
                        </div>
                        <div className="box-contents">
                            <div className="title">
                                <p>{reservation.movieNm}</p>
                                <p>{reservation.reservationDate}</p>
                            </div>
                            <p className="date">상영 시간 : {reservation.screeningTime}</p>
                            <span className="theater">인원: 1명</span>
                            {reservation.delYn === 'N' && <Button className="cancelBtn" type="button" onClick={(event) => {
                                rsvCancel({reservation});
                            }} variant="danger" >예매 취소</Button>}
                            {reservation.delYn === 'Y' && <Button className="cancelBtn" type="button" variant="outline-primary" >취소 완료</Button>}
                        </div>
                    </div>
                </li>
            ))}
        </ul>
    </div>
}

function KakaoPayApproval(){
    const [reservations, setReservations] = useState([]);
    //모달 관련 state
    const [chkModalOpen, setChkModalOpen] = useState(false);
    const [content, setContent] = useState("예매 취소 완료되었습니다.");

    const [searchState, setSearchState] = useState("complete");

    const handleSearchList = () => {
        axiosFunction();
    }
    const closeModal = () => {
        setChkModalOpen(false);
        window.location.reload(); // 페이지 새로고침
    }

    const updateModalOpen = (result) => {
        setChkModalOpen(result);
    }

    //조회 조건변경시
    const handleChangeSelect = (event) => {
        setSearchState(event.target.value);
    }

    const axiosFunction = () => {
        axios.get("/api/rsv/userReservationList", {params:
                {
                    searchState: searchState
                }})
            .then(response => {
                setReservations(response.data);

            })
            .catch(error => {
                console.error("Error fetching reservations:", error);
            });
    }
    //화면 랜더링시 바로 실행
    useEffect(() => {
        axiosFunction();
    }, []);
    return (
        <Container>
            <div id="contaniner" className="">
                <div id="contents" className="">
                    <div className="cols-content">
                        <div className="col-detail">
                            <div className="movielog-detail-wrap">
                                <form id="form1" method="get" noValidate="novalidate">
                                    <div className="tit-mycgv">
                                        <h3>예약목록</h3>
                                        <p><em>{reservations.length}건</em></p>
                                        <div className="set-combo">
                                            <select onChange={handleChangeSelect}>
                                                <option value="all">전체</option>

                                                <option value="complete" selected="selected" >예매 완료</option>

                                                <option value="cancel">예매 취소</option>

                                            </select>
                                            <button type="button" onClick={handleSearchList} className="round gray"><span>조회</span></button>
                                        </div>
                                    </div>
                                </form>
                                <ReservationList key = {searchState} reservations = {reservations} updateModalOpen = {updateModalOpen} ></ReservationList>
                            </div>
                            { chkModalOpen &&(<CheckModal isOpen = {chkModalOpen} content = {content} closeModal = {closeModal}/>)}
                        </div>
                    </div>
                </div>
            </div>
        </Container>
    );
}

export default KakaoPayApproval;