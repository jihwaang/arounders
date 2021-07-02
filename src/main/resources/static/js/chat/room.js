import {chatModule} from '/js/chat/chatModule.js';

/* 접속 중인 클라이언트의 Nickname 보여주는 곳 */
const clientsBox = document.querySelector('.clients-box');
const clients = clientsBox.querySelector('.clients');
/* 채팅이 append 될 곳 */
const chatBox = document.querySelector('.chat-box');
const blockLoad = chatBox.querySelector('.block-load');
const chats = chatBox.querySelector('.chats');

const btnBox = document.querySelector('.room-button-box');
/* 전송 버튼 */
const btnSend = document.querySelector('.btn-send');
/* 채팅방 참여취소 */
const btnExit = btnBox.querySelector('.btn-exit');
/* 채팅방 서비스 종료 */
const btnClose = btnBox.querySelector('.btn-close');
/* 웹소켓 세션 연결 종료 */
const btnDisconnect = btnBox.querySelector('.btn-disconnect');
/* 참여중인 유저 목록 버튼 */
const btnClients = btnBox.querySelector('.btn-clients');

const inputContent = document.querySelector('textarea[name="content"]');

/* 채팅 내역을 불러오는 과정에 필요한 변수 */
let page = 1;
let maxSize = 0;
let chatArr = [];
let isLoadLastChat = false;

/**
 * Core Codes </>
 * WebSocket + SockJS + STOMP
 * 1) Stomp connection => send to server a message for enter
 * 2) You can send some messages to server
 * 3) When server get your messages, it will broadcast to other clients in same room
 * 4) You can exit this room
*/
const sock = new SockJS('/stomp/chat', null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});
const stomp = Stomp.over(sock);

stomp.connect({}, function (frame){
    console.log('STOMP Connection!');

    stomp.subscribe(`/sub/chat/room/${chatRoomId}`, function (content) {

        const chat = JSON.parse(content.body);

        let className = 'yours';

        if(chat.memberId == memberId)
            className = 'mine';

        let profile = chat.profile == null? '/image/unknown.png' : chat.profile;

        let html = `<div class="chat ${className}">
                    <img class="profile" src="${profile}">
                    
                    <div class="chat-content">
                        <div class="chat-writer">${chat.writer}</div>
                        <div class="chat-message">${chat.message}</div>
                    </div>
                    
                    <div class="chat-time">${displayedAt(new Date(chat.time))}</div>
                </div>`;

        chats.insertAdjacentHTML('beforeend', html);
        /* 채팅창의 스크롤 맨 밑으로 */
        scrollToDown();
    });

    /* 채팅 목록 불러오기 */
    (async () => {
        chatArr = await chatModule.getChats(chatRoomId);
        //console.log(chatArr);
        maxSize = chatArr.length;
        /* 채팅 목록 보여주기 (30EA) */
        showList();
        /* 채팅창의 스크롤 맨 밑으로 */
        scrollToDown();
    })();

    stomp.send('/pub/chat/enter', {}, JSON.stringify({
        chatRoomId: `${chatRoomId}`,
        region: `${region}`,
        writer: `${nickname}`,
        memberId: `${memberId}`,
        profile: `${profileImg}`
    }));
});

/* 채팅 더보기 */
chats.addEventListener('scroll', function (e) {
    if(!isLoadLastChat && this.scrollTop < 30) {
        showBlock();
        showList();
    }
});
/* 채팅 불러오는 것 시각적 표시 */
function showBlock(){

    setTimeout(() => {
        blockLoad.style.top = '0';
    }, 0);

    blockLoad.addEventListener('transitionend', () => {
        blockLoad.style.top = '-30px';
    });
}

/* 채팅 종료 */
btnDisconnect.addEventListener('click', (e) => {

    e.preventDefault();

    stomp.send('/pub/chat/exit', {}, JSON.stringify({
        chatRoomId: `${chatRoomId}`,
        region: `${region}`,
        writer: `${nickname}`,
        memberId: `${memberId}`,
        profile: `${profileImg}`
    }));
    /* 연결 해제 */
    stomp.disconnect();
    /* 뒤로가기 */
    history.back();
});

/* (채팅 종료) 서버에 세션종료를 알림 */
window.addEventListener('beforeunload', function (e) {

    stomp.send('/pub/chat/exit', {}, JSON.stringify({
        chatRoomId: `${chatRoomId}`,
        region: `${region}`,
        writer: `${nickname}`,
        memberId: `${memberId}`,
        profile: `${profileImg}`
    }));

    stomp.disconnect();
});

/* 메세지 전송 */
btnSend.addEventListener('click', function (e){

    e.preventDefault();

    const content = inputContent.value;
    if(content == '') return;

    inputContent.value = '';
    inputContent.style.height = '38px';

    stomp.send('/pub/chat/message', {}, JSON.stringify({
        chatRoomId: `${chatRoomId}`,
        message: `${content}`,
        writer: `${nickname}`,
        memberId: `${memberId}`,
        profile: `${profileImg}`
    }));
});
inputContent.addEventListener('keydown', function (e){

    if(e.keyCode == 13){
        e.preventDefault();
        btnSend.click();
        return;
    }

    this.style.height = '1px';
    this.style.height = (12 + this.scrollHeight) + 'px';
});

/* 채팅방 서비스 종료 */
if(btnClose != null)
btnClose.addEventListener('click', () => {

    if(!confirm('현재 채팅방을 삭제하시겠습니까?')) return;

    const form = document.createElement('FORM');
    const html = `<input type="hidden" name="id" value="${chatRoomId}">`;

    alert(`${roomTitle} 채팅방이 삭제되었습니다. 감사합니다.`);

    form.method = 'post';
    form.action = '/chat/room';
    form.insertAdjacentHTML('beforeend', html);

    document.querySelector('body').insertAdjacentElement('beforeend', form);
    form.submit();
});

/* 채팅방 참여 종료 */
if(btnExit != null)
btnExit.addEventListener('click', async (e) => {

    if(!confirm('현재 채팅방을 나가시겠습니까? 추후 다시 참여가 가능합니다.')) return;

    const result = await chatModule.dropOut(chatRoomId);

    alert(`${roomTitle} 채팅방을 나가셨습니다. 감사합니다.`);

    /*window.location = '/chat/list';*/
    history.back();
});

/* 채팅 내역을 보여줄 Function */
function showList(){

    /* 마지막 채팅을 불러왔다면 중지 */
    if(isLoadLastChat) return;

    let offset = (page - 1) * 30;
    let html = '';

    //console.log(`page : ${page}, offset : ${offset}, maxSize : ${maxSize}`);

    for(let i = (maxSize) - offset - 30; i < maxSize - offset; i++){

        if(i < 0) {
            /* 마지막 채팅을 불러왔다면 중지를 위해 Flag set */
            isLoadLastChat = true;
            continue;
        }

        const chat = JSON.parse(chatArr[i]);
        //console.log(chat);

        let className = 'yours';

        if(chat.memberId == memberId)
            className = 'mine';

        let profile = chat.profile == null? '/image/unknown.png' : chat.profile;

        html += `<div class="chat ${className}">
                    <img class="profile" src="${profile}">
                    
                    <div class="chat-content">
                        <div class="chat-writer">${chat.writer}</div>
                        <div class="chat-message">${chat.message}</div>
                    </div>
                    
                    <div class="chat-time">${displayedAt(new Date(chat.time))}</div>
                </div>`;
    }

    page++;
    chats.insertAdjacentHTML('afterbegin', html);
}

/* 채팅창의 스크롤 맨 밑으로 */
function scrollToDown() {chats.scrollTop = chats.scrollHeight;}

/* btnClients Click -> toggle 참여중인 사람 목록 */
btnClients.addEventListener('click', () => {
    clientsBox.classList.toggle('hide');
});

/* 채팅방에 가입한 사람, 탈퇴한 사람에 따른 참여 중인 클라이언트 목록 변화 */
async function getClients() {

    const memberList = await chatModule.getClientsFromChatRoom(chatRoomId);

    clients.innerHTML = '';

    let html = '';

    memberList.forEach((member, idx) => {

        let highlight = member.nickname === creator? '<span class="highlight">*</span>' : '';
        let profile = member.uuid != null? `/upload/${member.path}/${member.uuid}_${member.name}` : `/image/unknown.png`;

        html += `<div class="client-box">
                    <img class="client-icon" src="${profile}" alt="profile-icon">
                    <div class="client">${highlight}${member.nickname}</div>
                </div>`;
    });

    clients.insertAdjacentHTML('beforeend', html);
}
getClients();
(() => {
    /* 10분에 1번씩 동기화 */
    setInterval(getClients, 600000);
})();

/* N초전 N분전 N시간전 N일전 ... */
function displayedAt(createdAt) {
    const milliSeconds = new Date() - createdAt;
    const seconds = milliSeconds / 1000;

    if (seconds < 60) return `방금 전`;

    const minutes = seconds / 60;

    if (minutes < 60) return `${Math.floor(minutes)}분 전`;

    const hours = minutes / 60;

    if (hours < 24) return `${Math.floor(hours)}시간 전`;

    const days = hours / 24;

    if (days < 7) return `${Math.floor(days)}일 전`;

    const weeks = days / 7;

    if (weeks < 5) return `${Math.floor(weeks)}주 전`;

    const months = days / 30;

    if (months < 12) return `${Math.floor(months)}개월 전`;

    const years = days / 365;

    return `${Math.floor(years)}년 전`;
}