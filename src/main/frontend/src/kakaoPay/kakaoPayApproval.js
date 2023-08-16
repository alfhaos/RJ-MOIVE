import React, { useState, useEffect } from 'react';
import "./KakaoPayApproval.css"; // 스타일링을 위한 CSS 파일
import { Container, Table, Button } from "react-bootstrap";
import axios from 'axios';

function KakaoPayApproval(){
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        axios.get("/api/rsv/userReservationList") // 예약 정보를 가져오는 API 경로로 변경하세요
            .then(response => {
                setReservations(response.data);
                console.log(reservations);
            })
            .catch(error => {
                console.error("Error fetching reservations:", error);
            });
    }, []);

    return (
        <Container>
            <h2>예약된 영화 정보</h2>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>영화</th>
                    <th>영화명</th>
                    <th>예약날짜</th>
                    <th>상영시간</th>
                    <th>가격</th>
                    <th>좌석</th>
                    <th>예약 취소 여부</th>
                </tr>
                </thead>
                <tbody>
                {reservations.map((reservation, index) => (
                    <tr key={index}>
                        <td>
                            <img className = "movieImg" src="https://img.cgv.co.kr/Movie/Thumbnail/Poster/000087/87034/87034_320.jpg" alt="영화이미지"/>
                        </td>
                        <td>{reservation.movieNm}</td>
                        <td>{reservation.reservationDate}</td>
                        <td>{reservation.screeningTime}</td>
                        <td>{reservation.price}</td>
                        <td>{reservation.seat}</td>
                        <td>
                            {reservation.delYn === 'Y' ? "취소됨" : "예약완료"}
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </Container>
    );
}

export default KakaoPayApproval;