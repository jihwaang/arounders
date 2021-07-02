import {likeModule} from '/js/likes/likeModule.js';
import {interestModule} from "/js/interests/interestModule.js";
import {reportModule} from "/js/reports/reportModule.js";

const filterBox = document.querySelector('.filter-box');
const main = document.querySelector('main');

/* Modal */
const modal = document.querySelector('.modal');
const modalTitle = modal.querySelector('.modal-title');
const modalBodyContent = modal.querySelector('.modal-body-content');
const btnClose = modal.querySelector('.btn-close');
const btnCancel = modal.querySelector('.btn-cancel');
const btnDoReport = modal.querySelector('.btn-do-report');
const btnDoHide = modal.querySelector('.btn-do-hide');

const utilBox = document.querySelector('.util-box');
const boardStatusBox = document.querySelector('.board-status-box');
const boardOrderBox = document.querySelector('.board-order-box');

/* category */
const selectCategory = filterBox.querySelector('select[name="category"]');
/* Search -> keyword */
const inputKeyword = filterBox.querySelector('input[name="keyword"]');
/* Search -> field */
const selectField = filterBox.querySelector('select[name="field"]');
/* 검색 버튼 */
const btnSearch = filterBox.querySelector('.btn-search');
/* 리셋 버튼 */
const btnReset = filterBox.querySelector('.btn-reset');
/* 필터 버튼 */
const btnFilter = utilBox.querySelector('.btn-filter');
/* 게시글 작성 버튼 */
const btnRegister = utilBox.querySelector('.btn-register');

const boardBox = document.querySelector('.boards');

const cri = BoardCriteria;

// 세션에서 값 가져와 넣기
let isLastPage = false;
cri.region = region;
cri.cityId = cityId;
showList(cri);

if(window.innerHeight >= main.clientHeight){
    cri.page++;
    showList(cri);
}

/* Scroll Down -> Board List Request */
/* 브라우저의 높이와, 브라우저의 scroll값, main의 높이 */
window.addEventListener('scroll', () => {
    let val = window.innerHeight + window.scrollY;

    // console.log(`window.innerHeight : ${window.innerHeight}`);
    // console.log(`window.scrollY : ${window.scrollY}`);
    // console.log(`document.body.offsetHeight : ${main.clientHeight}`);
    // console.log('------');

    if(val >= main.clientHeight){
        cri.page++;
        showList(cri);
    }
});

/* 검색버튼 click */
btnSearch.addEventListener('click', (e) => {

    boardBox.innerHTML = '';

    const order = getOrder();
    const status = getStatus();
    const category = selectCategory.value;
    const field = selectField.value;
    const keyword = inputKeyword.value;

    isLastPage = false;
    //console.log(`${order}, ${status}, ${category}, ${field}, ${keyword}`);

    cri.init();
    cri.set(order, status, category, field, keyword);

    showList(cri);
});

function getOrder(){
    const orders = filterBox.querySelectorAll('input[name="order"]');

    for(let order of orders)
        if(order.checked)
            return order.value;
}
function getStatus(){
    const statuses = filterBox.querySelectorAll('input[name="status"]');

    for(let status of statuses)
        if(status.checked)
            return status.value;
}

 /*Modal Close 버튼 Click*/
btnClose.addEventListener('click', () => {
    modal.style.display = 'none';
});
btnCancel.addEventListener('click', () => {
    modal.style.display = 'none';
});

async function showList(cri){

    if(isLastPage) return;

    const result = await boardModule.getList(cri);

    if(result.length == 0) {
        modalTitle.innerText = 'Notification';
        modalBodyContent.innerText = '마지막 페이지 입니다.';
        btnDoHide.style.display = 'none';
        btnDoReport.style.display = 'none';

        modal.style.display = 'block';
        isLastPage = true;
        return;
    }

    let html = '';

    result.forEach((board, idx) => {
        console.log(board);

        let status = board.status == '0'? '<span class="sticker-status">진행중</span>' : '<span class="sticker-status-off">종료</span>';

        let src = board.thumbnailName == null? `/image/board/sample-image.png`:`/upload/${board.thumbnailPath}/${board.thumbnailName}`;

        let likeClass = board.isLike === null? 'btn-like' : 'btn-like-on';

        let interestClass = board.isInterest === null? 'btn-interest' : 'btn-interest-on';


        html += `<div class="board" data-bid="${board.id}">

                    <div class="board-top">
                        <div class="board-sticker">
                            <span class="sticker-cg">${board.categoryTitle}</span>
                            <span class="sticker-city">${board.city}</span>
                            <span class="sticker-region">${board.region}</span>
                            ${status}
                        </div>
        
                        <button class="btn-dot">dot</button>
        
                        <div class="board-toolbox hide">
                            <button class="btn-hide">숨기기</button>
                            
                            ${board.memberId == memberId? `<button class="btn-report">신고하기</button>`:'' }
                        </div>
                    </div>
        
                    <div class="board-mid">
                        <div class="board-thumbnail-box">
                            <img src="${src}" alt="썸네일 이미지" class="board-thumbnail">
                            <!--<div class="board-thumbnail" style="background: #fff url(${src});"></div>-->
                        </div>
        
                        <div class="board-content-box">
                            <div class="board-title">
                                <a href="read?id=${board.id}">${board.title}</a>
                            </div>
                            <div class="board-info">
                                <span class="board-nickname">${board.writer}</span>
                                <span class="board-date">${displayedAt(new Date(board.createdAt))}</span>
                            </div>
                        </div>
                    </div>
        
                    <div class="board-bottom">
                        <div>
                            <button class="like ${likeClass}">Like</button><span class="val like-val">${numToK(board.likeCount)}</span>
                        </div>
                        <div>
                            <button class="btn-comment">Comment</button><span class="val comment-val">${numToK(board.commentCount)}</span>
                        </div>
                        <div>
                            <button class="interest ${interestClass}">Interest</button><span class="val interest-val">${numToK(board.interestCount)}</span>
                        </div>
                    </div>
        
                </div>`;
    });
    boardBox.insertAdjacentHTML('beforeend', html);
}

/* 리셋 버튼 클릭 */
btnReset.addEventListener('click', (e) => {
    /* order -> 최신순 */
    boardOrderBox.querySelector('input[value="desc"]').checked = true;
    /* status -> 전체 */
    boardStatusBox.querySelector('input[value="all"]').checked = true;
    /* category -> 전체 */
    selectCategory.querySelector('option[value="1"]').selected = true;
    /* field -> 전체 & keyword -> '' */
    inputKeyword.value = '';
    selectField.querySelector('option[value="all"]').selected = true;

    /* when btnReset is clicked, btnSearch will be clicked too */
    const event = new MouseEvent('click', {
        view: window,
        bubbles: true,
        cancelable: true
    });

    btnSearch.dispatchEvent(event);
});

/* 필터 버튼 클릭 */
btnFilter.addEventListener('click', (e) => {
    filterBox.classList.toggle('hide');
});

/* Board의 ...버튼 클릭 */
boardBox.addEventListener('click', function (e) {

    const dot = e.target;

    if(!dot.classList.contains('btn-dot')) return;

    const toolBox = dot.nextElementSibling;
    toolBox.classList.toggle('hide');

    //bid = toolBox.parentElement.parentElement.dataset.bid;

    const btnReport = toolBox.querySelector('.btn-report');
    const btnHide = toolBox.querySelector('.btn-hide');

    /* 신고하기 */
    btnReport.addEventListener('click', doReport);
    /* 숨기기 */
    btnHide.addEventListener('click', doHide)
});

function doReport(){

    const bid = this.parentElement.parentElement.parentElement.dataset.bid;

    modalTitle.innerText = 'Notification';
    modalBodyContent.innerText = `${bid}번 글을 신고하시겠습니까?`;
    btnDoHide.style.display = 'none';
    btnDoReport.style.display = 'block';

    modal.style.display = 'block';

    btnDoReport.onclick = async () => {

        const result = await reportModule.report(bid);

        /* 신고 완료 */
        if(result != null && result != ''){
            modalBodyContent.innerText = `${bid}번 글을 신고했습니다.`;
            btnDoReport.style.display = 'none';
        }
        /* 이미 내가 신고한 글일 때 */
        else{
            modalBodyContent.innerText = `${bid}번 글을 이미 신고하셨습니다.`;
            btnDoHide.style.display = 'none';
            btnDoReport.style.display = 'none';
        }
    }
}

function doHide(){

    const targetBoard = this.parentElement.parentElement.parentElement;
    const bid = targetBoard.dataset.bid;

    modalTitle.innerText = 'Notification';
    modalBodyContent.innerText = `${bid}번 글을 숨기시겠습니까?`;
    btnDoHide.style.display = 'block';
    btnDoReport.style.display = 'none';

    modal.style.display = 'block';

    btnDoHide.onclick = async () => {

        const result = await boardModule.doHide(bid);

        /* 숨김 완료 */
        if(result != null && result != ''){
            modalBodyContent.innerText = `${bid}번 글을 숨김 처리했습니다.`;
            btnDoHide.style.display = 'none';

            targetBoard.remove();
        }

    }
}

/* 게시글당 좋아요 버튼 */
boardBox.addEventListener('click', async function (e) {

    const btnLike = e.target;

    if(!btnLike.classList.contains('like')) return;

    /* target Board 얻어서 board_id 추출 */
    const targetBoard = btnLike.parentElement.parentElement.parentElement;
    const bid = targetBoard.dataset['bid'];

    /* board_id set */
    likeModule.boardId = bid;

    /* current not like -> like*/
    if(btnLike.classList.contains('btn-like'))
        await likeModule.like();
    /* current like -> not like */
    else
        await likeModule.dislike();

    /* Update value of Likes */
    const likeVal = targetBoard.querySelector('.like-val');
    likeVal.innerText = await likeModule.getCounts();

    /* Toggle Icon */
    btnLike.classList.toggle('btn-like');
    btnLike.classList.toggle('btn-like-on');
});
/* 게시글당 관심 버튼 */
boardBox.addEventListener('click', async function (e) {

    const btnInterest = e.target;

    if(!btnInterest.classList.contains('interest')) return;

    /* target Board 얻어서 board_id 추출 */
    const targetBoard = btnInterest.parentElement.parentElement.parentElement;
    const bid = targetBoard.dataset['bid'];

    /* board_id set */
    interestModule.boardId = bid;

    /* Toggle Interest */
    await interestModule.doInterest();

    /* Update value of Interests */
    const interestVal = targetBoard.querySelector('.interest-val');
    interestVal.innerText = await interestModule.getCounts();

    /* Toggle Icon */
    btnInterest.classList.toggle('btn-interest');
    btnInterest.classList.toggle('btn-interest-on');
});

/* 게시글 등록 버튼 */
btnRegister.addEventListener('click', (e) => {
    window.location = '/board/register';
});

/* Date를 N분전/N일전/N달전... */
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

function numToK(val){

    const n = parseInt(val);

    if(n < 1000)
        return n;

    let result = Math.floor(n / 1000.0 * 10) / 10;

    return `${result}k`;
}