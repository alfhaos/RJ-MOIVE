import logo from './logo.svg';
import './App.css';

import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {BrowserRouter, Routes, Route, Link, NavLink, Router} from "react-router-dom";
import MovieList from "./movie/MovieList";
import Reservation from "./movie/Reservation";
import KakaoPayApproval from "./kakaoPay/kakaoPayApproval";
import KakaoPayFail from "./kakaoPay/kakaoPayFail";
import KakaoPayCancel from "./kakaoPay/kakaoPayCancel";
import Header from "./common/Header";
import Footer from "./common/Footer";

function App() {

  return (
      <BrowserRouter>
          <Header />
          <Routes>
              <Route path={"/"} element={<MovieList />}></Route>
              <Route path={"/movieReservation"} element={<Reservation />}></Route>
              <Route path={"/kakaoPayApproval"} element={<KakaoPayApproval />}></Route>
              <Route path={"/kakaoPayFail"} element={<KakaoPayFail />}></Route>
              <Route path={"/kakaoPayCancel"} element={<KakaoPayCancel />}></Route>
          </Routes>
          <Footer />
      </BrowserRouter>

  )
}

export default App;