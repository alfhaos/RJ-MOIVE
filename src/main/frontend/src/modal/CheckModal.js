import React from 'react';
import './Modal.css'; // 모달 스타일을 위한 CSS 파일

function CheckModal({ isOpen, content, closeModal}){

    const handleCheck = () => {
        closeModal();
    }

    if (!isOpen) return null; // 모달이 열려있지 않으면 아무것도 렌더링하지 않음

    return (
        <div className="modal" tabIndex="-1" role="dialog" style={{ display: isOpen ? 'block' : 'none' }}>
            <div className="modal-dialog" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">예약 정보</h5>
                        <button type="button" className="close" onClick={handleCheck}>
                            <span>&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        {content}
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-primary" onClick={handleCheck}>확인</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CheckModal;