import {Table} from "react-bootstrap";
import React from "react";

<Table striped bordered hover>
    <thead>
    <tr>
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


<div className="box-contents">
    <div className="title">
        <a href="/movies/detail-view/?midx=87045">
            <strong id="strong_87045">범죄도시3</strong>
        </a>
        <p>THE ROUNDUP : NO WAY OUT</p>
    </div>
    <p className="date">2023.06.11 (일) 15:00 ~ 16:55</p>
    <p className="theater">CGV송도타임스페이스 7관 (리클라이너) / 2명</p>
    <ul className="writerinfo" id="wid_460526386">
        <li className="writer-opinion">
            <a className="link-gradewrite" id="wIdx_460526386" href="javascript:void(0);" data-movieidx="87045" data-movietitle="범죄도시3">이 영화를 평가해주세요</a>
        </li>
    </ul>