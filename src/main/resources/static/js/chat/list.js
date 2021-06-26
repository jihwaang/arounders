import {chatModule} from '/js/chat/chatModule.js';

const chatRooms = document.querySelector('.chat-rooms');
const modal = document.querySelector('.modal');

/*
* 채팅방 목록 클릭 ->
* 내가 참여중인 채팅방인가? enter : deny and try to accept for entering room
*/
chatRooms.addEventListener('click', async (e) => {

    e.preventDefault();
    if(e.target.tagName !== 'A') return;

    const href = e.target.href;
    const chatRoomId = e.target.dataset.id;

    const result = await chatModule.isJoin(chatRoomId);

    /* 채팅방 참여 O */
    if(result){
        window.location = href;
    }
    /* 채팅방 참여 X */
    else{
        modal.style.display = 'block';
    }
});