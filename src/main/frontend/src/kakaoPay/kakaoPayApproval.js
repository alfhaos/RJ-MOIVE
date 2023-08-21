import React, { useState, useEffect } from 'react';
import "./KakaoPayApproval.css"; // 스타일링을 위한 CSS 파일
import { Container, Table, Button } from "react-bootstrap";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import CheckModal from "../modal/CheckModal"; // 부트스트랩 스타일시트 임포트

function ReservationList (props) {

    const rsvCancel = (reservation) => {

        console.log(reservation.reservation);

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
                            <Button className="cancelBtn" type="button" onClick={(event) => {
                                rsvCancel({reservation});
                            }} variant="danger" >예매 취소</Button>
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

    const closeModal = () => {
        setChkModalOpen(false);
        window.location.reload(); // 페이지 새로고침
    }

    const updateModalOpen = (result) => {
        setChkModalOpen(result);
    }


    useEffect(() => {
        axios.get("/api/rsv/userReservationList") // 예약 정보를 가져오는 API 경로로 변경하세요
            .then(response => {
                setReservations(response.data);

            })
            .catch(error => {
                console.error("Error fetching reservations:", error);
            });
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
                                            <select id="year" name="year">
                                                <option value="" selected="selected">전체</option>

                                                <option value="2019">2019</option>

                                                <option value="2022">2022</option>

                                                <option value="2023">2023</option>

                                            </select>
                                            <button type="submit" className="round gray"><span>GO</span></button>
                                        </div>
                                    </div>
                                </form>
                                <ReservationList reservations = {reservations} updateModalOpen = {updateModalOpen} ></ReservationList>
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