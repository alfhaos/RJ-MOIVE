import React from 'react';
function Footer(props) {

    return (
        <div className="containerFooter">
            <footer className="bg-light text-center text-white">
                <div className="text-center p-3" style={{ backgroundColor: 'rgba(0, 0, 0, 0.2)' }}>
                    © 2023 Copyright:
                    <a className="text-white" href="https://github.com//"> RJ-JPA.COM</a>
                </div>
            </footer>
        </div>
    );
}

export default Footer;