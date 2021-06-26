import {chatModule} from "/js/chat/chatModule.js";

const rooms = document.querySelector('.chat-rooms');

(async () => {
    const data = await chatModule.getMyChatRooms();

    let html = '';
    data.forEach((room, idx) => {
        html += `<li>
                    <span>${room.region}</span>
                    <a href="/chat/room?id=${room.id}&region=${room.region}&cityId=${room.cityId}">${room.title}</a>
                    <span>${room.createdAt}</span>
                </li>`;
    });

    rooms.insertAdjacentHTML('beforeend', html);
})();