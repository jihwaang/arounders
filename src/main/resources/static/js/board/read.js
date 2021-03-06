import {likeModule} from '/js/likes/likeModule.js';
import {interestModule} from "/js/interests/interestModule.js";
import {commentModule} from "/js/comments/commentModule.js";
import {chatModule} from '/js/chat/chatModule.js';

/* Modal */
const modal = document.querySelector('.modal');
const modalTitle = modal.querySelector('.modal-title');
const modalBodyContent = modal.querySelector('.modal-body-content');
const modalDate = modal.querySelector('.modal-date');
const modalWriter = modal.querySelector('.modal-writer');
const modalCid = modal.querySelector('input[name="cid"]');

const btnRecomment = modal.querySelector('.btn-recomment');
const btnClose = modal.querySelector('.btn-close');
const btnCancel = modal.querySelector('.btn-cancel');
const btnSave = modal.querySelector('.btn-save');
const btnModify = modal.querySelector('.btn-modify');
const btnDelete = modal.querySelector('.btn-delete');
const btnChatJoin = modal.querySelector('.btn-chat-join');

/* Board */
const board = document.querySelector('.board');

const boardFiles = board.querySelector('.board-files');
const btnLike = board.querySelector('.like');
const btnInterest = board.querySelector('.interest');
const commentVal = board.querySelector('.comment-val');
//const btnBack = document.querySelector('.button-back');

/* image viewer */
const boardMid = document.querySelector('.board-mid');
const imgViewer = document.querySelector('.img-viewer');
const viewerClose = imgViewer.querySelector('.viewer-close');
const viewerImg = imgViewer.querySelector('.viewer-img');

likeModule.boardId = boardId;
interestModule.boardId = boardId;

/* Comment */
const commentBox = document.querySelector('.comment-box');
const comments = commentBox.querySelector('.comments');
const commentSubmit = commentBox.querySelector('.comment-submit');
const commentTextarea = commentBox.querySelector('.comment-textarea');
const btnCommentMore = commentBox.querySelector('.btn-comment-more');

/* Option */
const btnToChat = document.querySelector('.btn-to-chat');

/* set boardId at CommentModule */
commentModule.boardId = boardId;

let currentSelect = '';
let page = 1;

function pageInit(){
    page = 1;
}

/* About Board */
/* ???????????? ????????? ?????? */
btnLike.addEventListener('click', async function (e) {

    /* current not like -> like*/
    if(btnLike.classList.contains('btn-like'))
        await likeModule.like();
    /* current like -> not like */
    else
        await likeModule.dislike();

    /* Update value of Likes */
    const likeVal = this.nextElementSibling;
    likeVal.innerText = await likeModule.getCounts();

    /* Toggle Icon */
    btnLike.classList.toggle('btn-like');
    btnLike.classList.toggle('btn-like-on');
});
/* ???????????? ?????? ?????? */
btnInterest.addEventListener('click', async function (e) {

    /* Toggle Interest */
    await interestModule.doInterest();

    /* Update value of Interests */
    const interestVal = this.nextElementSibling;
    interestVal.innerText = await interestModule.getCounts();

    /* Toggle Icon */
    btnInterest.classList.toggle('btn-interest');
    btnInterest.classList.toggle('btn-interest-on');
});

// btnBack.addEventListener('click', () => {
//     history.back();
// });
/* -------------------- About Board ---------------------- */

/* -------------------- About Comment -------------------- */
/* ??????????????? ???????????? */
async function getComments(page, target){

    const data = await commentModule.getComments(page, target);

    /* ????????? ????????? ????????? ?????? ??? */
    if(data.length === 0) {
        //lastCommentModal();
        btnCommentMore.classList.add('hide');
        return;
    }

    let html = '';

    data.forEach((comment, idx) => {

        let hasChild = '';

        if(comment.hasChild > 0)
            hasChild = `<button class="btn-child">?????????</button>`;

        html += `
            <div class="comment" data-cid="${comment.id}" data-wid="${comment.memberId}">
                <div class="comment-top">
                    <span class="comment-writer">${comment.nickname}</span>
                    <span class="comment-date">${displayedAt(new Date(comment.createdAt))}</span>
                </div>

                <div class="comment-mid">
                    <p class="comment-content">${comment.content}</p>
                </div>

                <div class="comment-bot">
                    <button class="comment-reply">??????</button>
                </div>

                <div class="comment-children">
                    ${hasChild}
                    <div class="children">

                    </div>
                </div>
            </div>`;
    });

    comments.insertAdjacentHTML('beforeend', html);
}
getComments(page++, commentBox);


btnCommentMore.addEventListener('click', () => {
    getComments(page++, commentBox);
});
/* ?????? ?????? -> ?????? ?????? */
comments.addEventListener('click', function (e) {

    if(e.target.tagName == 'BUTTON') return;

    e.preventDefault();

    /* ?????? ????????? ????????????(?????????) ?????? */
    const target = e.target.closest('.child') || e.target.closest('.comment');
    const cid = target.dataset.cid;
    const writerId = target.dataset.wid;

    currentSelect = target;

    let writer;
    let date;
    let content;

    if(target.classList.contains('child')){
        writer = target.querySelector('.child-writer').innerText;
        date = target.querySelector('.child-date').innerText;
        content = target.querySelector('.child-content').innerText;
    }
    else{
        writer = target.querySelector('.comment-writer').innerText;
        date = target.querySelector('.comment-date').innerText;
        content = target.querySelector('.comment-content').innerText;
    }

    modalTitle.innerText = '??????';
    modalCid.value = cid;
    modalBodyContent.readOnly = true;
    modalBodyContent.value = content;
    modalDate.innerText = date;
    modalWriter.innerText = writer;

    if(modalValidate(memberId, writerId)){
        btnModify.style.display = 'block';
        btnDelete.style.display = 'block';
    }
    else{
        btnModify.style.display = 'none';
        btnDelete.style.display = 'none';
    }
    modalDate.style.display = 'block';
    modalWriter.style.display = 'block';

    btnChatJoin.style.display = 'none';
    btnRecomment.style.display = 'none';
    btnSave.style.display = 'none';


    modal.style.display = 'block';
});

/* ?????? ?????? (???) */
commentSubmit.addEventListener('click', async () => {

    const content = commentTextarea.value;

    commentTextarea.value = '';

    const result = await commentModule.register(content);
    /* ?????? ?????? ?????? */
    commentVal.innerText = await commentModule.getCount(boardId);

    const html = `
            <div class="comment" data-cid="${result}" data-wid="${memberId}">
                <div class="comment-top">
                    <span class="comment-writer">${nickname}</span>
                    <span class="comment-date">${displayedAt(new Date())}</span>
                </div>

                <div class="comment-mid">
                    <p class="comment-content">${content}</p>
                </div>

                <div class="comment-bot">
                    <button class="comment-reply">??????</button>
                </div>
                <div class="comment-children">
                    <div class="children"></div>
                </div>
            </div>`;

    comments.insertAdjacentHTML('afterbegin', html);
});

/* ????????? ?????? ?????? (???) */
comments.addEventListener('click', async function (e) {

    if(!e.target.classList.contains('btn-child')) return;

    const target = e.target.closest('.comment');
    const upperId = target.dataset.cid;

    /* cid??? upperId??? ?????? ????????? ???????????? ????????? */

    const reComments = await commentModule.getReComments(upperId);

    const children = target.querySelector('.children');
    children.innerText = '';

    let html = '';

    reComments.forEach((re, idx) => {
        html += `<div class="child" data-cid="${re.id}" data-wid="${re.memberId}">
                    <div class="child-top">
                        <span class="child-writer">${re.nickname}</span>
                        <span class="child-date">${displayedAt(new Date(re.createdAt))}</span>
                    </div>

                    <div class="child-mid">
                        <p class="child-content">${re.content}</p>
                    </div>
                </div>`
    });

    children.insertAdjacentHTML('beforeend', html);
    e.target.remove();
});

/* (?????????)?????? ?????? ?????? -> ?????? (???) */
comments.addEventListener('click', (e) => {

    if(!e.target.classList.contains('comment-reply')) return;

    const target = e.target.closest('.comment');
    const cid = target.dataset.cid;

    currentSelect = target;

    /* ?????? ????????? cid??? ????????? ?????? */
    btnRecomment.style.display = 'block';
    btnDelete.style.display = 'none';
    btnModify.style.display = 'none';
    btnSave.style.display = 'none';
    btnChatJoin.style.display = 'none';

    modalTitle.innerText = '?????? ??????';
    modalCid.value = cid;
    modalBodyContent.readOnly = false;
    modalBodyContent.value = '';
    modalDate.innerText = '';
    modalWriter.innerText = '';

    modal.style.display = 'block';
});

/* ?????? ?????? ?????? ?????? (???) */
btnRecomment.addEventListener('click', async (e) => {

    const upperId = modalCid.value;
    const content = modalBodyContent.value;

    modalBodyContent.readOnly = true;

    const result = await commentModule.registerRe(content, upperId);
    /* ?????? ?????? ?????? */
    commentVal.innerText = await commentModule.getCount(boardId);

    btnChatJoin.style.display = 'none';
    btnRecomment.style.display = 'none';
    modal.style.display = 'none';

    /* ????????? ???????????? (???) */
    let html = `<div class="child" data-cid="${result}" data-wid="${memberId}">
                    <div class="child-top">
                        <span class="child-writer">${nickname}</span>
                        <span class="child-date">${displayedAt(new Date())}</span>
                    </div>

                    <div class="child-mid">
                        <p class="child-content">${content}</p>
                    </div>
                </div>`;

    currentSelect.querySelector('.children').insertAdjacentHTML('afterbegin', html);
});

/* ------- About Comment --------- */

/* Modal */
/* Modal Modify btn (???) */
btnModify.addEventListener('click', (e) => {
    
    modalTitle.innerText = '?????? ??????';
    modalBodyContent.readOnly = false;

    btnSave.style.display = 'block';
    btnChatJoin.style.display = 'none';
    btnDelete.style.display = 'none';
    btnModify.style.display = 'none';
});

/* Modal ?????? -> ?????? (???) */
btnSave.addEventListener('click', async (e) => {

    const content = modal.querySelector('.modal-body-content').value;
    const cid = modal.querySelector('input[name="cid"]').value;
    
    const result = await commentModule.modify(content, cid);

    /* ?????? ??? ?????? ????????? ????????? (x) -> ?????? ?????? ?????? ?????? */
    modal.style.display = 'none';

    /* ????????? ??? */
    if(currentSelect.classList.contains('comment'))
        currentSelect.querySelector('.comment-content').innerText = content;
    /* ????????? ??? */
    else
        currentSelect.querySelector('.child-content').innerText = content;
});

/* Modal ?????? ?????? (???) */
btnDelete.addEventListener('click', async (e) => {
    const cid = modal.querySelector('input[name="cid"]').value;

    btnChatJoin.style.display = 'none';
    btnModify.style.display = 'none';
    btnDelete.style.display = 'none';

    modalBodyContent.readOnly = true;

    const result = await commentModule.remove(cid);
    /* ?????? ?????? ?????? */
    commentVal.innerText = await commentModule.getCount(boardId);

    modalBodyContent.value = `${result}??? ????????? ?????????????????????.`;

    currentSelect.remove();
});

/*Modal Close ?????? Click*/
btnClose.addEventListener('click', () => {
    modal.style.display = 'none';
});

btnCancel.addEventListener('click', () => {
    modal.style.display = 'none';
});

function modalValidate(currentId, writerId){

    return currentId == writerId;
}

function lastCommentModal(){

    modalBodyContent.readOnly = true;

    modalBodyContent.value = '????????? ???????????????.';
    modalTitle.innerText = 'Notification';
    modalDate.innerText = '';
    modalWriter.innerText = '';
    btnSave.style.display = 'none';
    btnDelete.style.display = 'none';
    btnRecomment.style.display = 'none';
    btnModify.style.display = 'none';
    btnChatJoin.style.display = 'none';

    modal.style.display = 'block';
}

/* ????????? ?????? -> validation */
if(btnToChat != null)
btnToChat.addEventListener('click', async function (e) {

    e.preventDefault();

    const href = e.target.href;
    const chatRoomId = e.target.dataset.id;

    const isClose = await fetch(`/chatMember/api/v1/${chatRoomId}/room`)
                            .then(res => res.json());
    console.log(isClose);
    if(isClose == '0') {
        modalDate.style.display = 'none';
        modalWriter.style.display = 'none';

        btnRecomment.style.display = 'none';
        btnModify.style.display = 'none';
        btnSave.style.display = 'none';
        btnDelete.style.display = 'none';

        btnChatJoin.style.display = 'none';

        modalTitle.innerText = 'Notification';
        modalBodyContent.value = '???????????? ?????? ???????????? ???????????????.';
        modal.style.display = 'block';
        return;
    }

    const result = await chatModule.isJoin(chatRoomId);

    /* ????????? ?????? O */
    if(result){
        window.location = href;
    }
    /* ????????? ?????? X */
    else{

        modalDate.style.display = 'none';
        modalWriter.style.display = 'none';

        btnRecomment.style.display = 'none';
        btnModify.style.display = 'none';
        btnSave.style.display = 'none';
        btnDelete.style.display = 'none';

        btnChatJoin.style.display = 'block';

        modalTitle.innerText = 'Notification';
        modalBodyContent.value = '???????????? ?????????????????????????';
        modal.style.display = 'block';

        btnChatJoin.onclick = (e) => {

            chatModule.join(chatRoomId);
            window.location = href;
        }
    }
});

/* Image slider Click -> ?????? */
boardMid.addEventListener('click', (e) => {

    if(!e.target.classList.contains('thumbnail')) return;

    imgViewer.style.zIndex = '900';
    viewerImg.src = e.target.src;
    imgViewer.style.opacity = '1';

});
viewerClose.addEventListener('click', (e) => {

    imgViewer.style.opacity = '0';
    imgViewer.style.zIndex = '-1';
});

function displayedAt(createdAt) {
    const milliSeconds = new Date() - createdAt;
    const seconds = milliSeconds / 1000;

    if (seconds < 60) return `?????? ???`;

    const minutes = seconds / 60;

    if (minutes < 60) return `${Math.floor(minutes)}??? ???`;

    const hours = minutes / 60;

    if (hours < 24) return `${Math.floor(hours)}?????? ???`;

    const days = hours / 24;

    if (days < 7) return `${Math.floor(days)}??? ???`;

    const weeks = days / 7;

    if (weeks < 5) return `${Math.floor(weeks)}??? ???`;

    const months = days / 30;

    if (months < 12) return `${Math.floor(months)}?????? ???`;

    const years = days / 365;

    return `${Math.floor(years)}??? ???`;
}