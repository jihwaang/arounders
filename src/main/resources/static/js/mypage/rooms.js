import {chatModule} from "/js/chat/chatModule.js";

const rooms = document.querySelector('.chat-rooms');

(async () => {
    const data = await chatModule.getMyChatRooms();

    let html = '';
    data.forEach((room, idx) => {
        html += `<div class="chat-room">
                    <div class="upper">
                        <a class="title" href="/chat/room?id=${room.id}&region=${room.region}&cityId=${room.cityId}">${room.title}</a>
                        <span class="created-at">${getDate(new Date(room.createdAt))}</span>
                    </div>
                    <div class="lower">
                        <span class="clients-count">${room.count}</span>
                        <span class="count-text">명 참여중</span>
                    </div>
                </div>`;
    });

    rooms.insertAdjacentHTML('beforeend', html);
})();

function getDate(date){

    const year = date.getFullYear();
    const month = '';
    const day = date.getDay() + 1;

    return `${year}/${month}/${day}`;
}