import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import './Header.css';

function Header(props) {
    return (
        <Navbar bg="dark" variant="dark" className="headerNav">
            <Navbar.Brand as={Link} to="http://localhost:3000/">RJ-MOVIE</Navbar.Brand>
            <Nav className="mr-auto">
                <Nav.Link as={Link} to="http://localhost:3000/">영화</Nav.Link>
                <Nav.Link as={Link} to="/movieReservation">예약목록</Nav.Link>
                {/* Add other menu items */}
            </Nav>
        </Navbar>
    )
}

export default Header;