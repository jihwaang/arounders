import {chatModule} from '/js/chat/chatModule.js';

const roomForm = document.querySelector('#chat-room-form');
const chatRooms = document.querySelector('.chat-rooms');
const titleInput = document.querySelector('.title-input');
const btnCreate = document.querySelector('.btn-create');

const modal = document.querySelector('.modal');
const modalTitle = modal.querySelector('.modal-title');
const modalBodyContent = modal.querySelector('.modal-body-content');
const btnModalCreate = modal.querySelector('.btn-create');
const btnJoin = modal.querySelector('.btn-join');
const btnClose = modal.querySelector('.btn-close');
const btnCancel = modal.querySelector('.btn-cancel');

/*
* 채팅방 목록 클릭 ->
* 내가 참여중인 채팅방인가? enter : deny and try to accept for entering room
*/
chatRooms.addEventListener('click', async (e) => {

    e.preventDefault();
    if(e.target.tagName !== 'A') return;

    const href = e.target.href;
    const title = e.target.innerText;
    const chatRoomId = e.target.dataset.id;

    const result = await chatModule.isJoin(chatRoomId);

    /* 채팅방 참여 O */
    if(result){
        window.location = href;
    }
    /* 채팅방 참여 X */
    else{
        btnModalCreate.style.display = 'none';
        btnJoin.style.display = 'block';

        modalTitle.innerText = '채팅방 참여';
        modalBodyContent.innerText = title;
        modal.style.display = 'block';

        btnJoin.addEventListener('click', (e1) => {
            console.log('aa');
            chatModule.join(chatRoomId);
            window.location = href;
        });
    }
});

/* 채팅방 개설 전 확인 작업 */
btnCreate.addEventListener('click', (e) => {

    e.preventDefault();

    btnJoin.style.display = 'none';
    btnModalCreate.style.display = 'block';

    modalTitle.innerText = '채팅방 개설';
    modalBodyContent.innerText = titleInput.value;
    modal.style.display = 'block';

    btnModalCreate.addEventListener('click', (e1) => {
        roomForm.submit();
    });
});

/* Modal Close / Cance 버튼 Click */
btnCancel.addEventListener('click', () => {
    modal.style.display = 'none';
});
btnClose.addEventListener('click', () => {
    modal.style.display = 'none';
});