import {commentModule} from "/js/comments/commentModule.js";
import {CommentCriteria} from "../comments/commentModule.js";
//
import {likeModule} from "/js/likes/likeModule.js";
import {interestModule} from "/js/interests/interestModule.js";
//

const profileBtn = document.querySelector('.profile-btn');
const locationBtn = document.querySelector('.location-btn');
const locationContainer = document.querySelector('.button-container');
const passwordForm = document.querySelector('#password-confirm-form');
const locationForm = document.querySelector('#location-form');
const boardCategory = document.querySelector('.board-button-list');
const listSection = document.querySelector('.list-section');
const boards = document.querySelector('.list-section > .list-container > .boards');
let cri = BoardCriteria;
const main = document.querySelector('main');
const activity = document.querySelector('.activity-section');
const activityType = activity.querySelector('.activity-list');
const comments = document.querySelector('#comment-list');
const commentSection = document.querySelector('.comment-section');

const interests = document.querySelector('#interest-list');
const interestSection = document.querySelector('.interest-section');

//게시글 0일 시 플래그 처리를 위한 변수 선언
let flag = false;



// validation
const validations = {
    nickname: true,
    phone: true,
    address: true,
};
const dashboard = {
    init: function (cri) {
        const _this = this;

        _this.getList(cri);

        activity.addEventListener('click', (e) => {
            console.log('activity section invoked');
            // if (e.target.classList.contains('board')) {
            //     cri = BoardCriteria;
            //     _this.getList(cri);
            // }
            if (e.target.classList.contains('comment')) {
                cri = CommentCriteria;
                flag = false;
                let page = 1;
                cri.page = page;
                _this.getCommentList(cri);
            } else if (e.target.classList.contains('interest')) {
                cri = BoardCriteria;
                cri.init();
                flag = false;
                _this.getInterestList(cri);
            } else {
                return;
            }
        })
    },
    getList: async function(cri) {
        comments.innerHTML = '';
        commentSection.classList.add('dp-none');

        interests.innerHTML = '';
        interestSection.classList.add('dp-none');

        if (listSection.classList.contains('dp-none')) {
            listSection.classList.remove('dp-none');
        }
        let requestURL = `/mypages/api/v1/boards`;

        let query = '?';
        /* mypage -> 내가 쓴 게시글 조회시, category별 조회만 가능(필터기능 X) */
        query += `page=${cri.page}&`;
        query += `category=${cri.category}`;

        requestURL = requestURL + query;

        let result = await fetch(requestURL).then(response => response.json());
        // flag 처리
        if (result.length === 0) flag = true;


        for (let board of result) {
            let status = board.status == '0'? '<span class="sticker-status">진행중</span>' : '<span class="sticker-status-off">종료</span>';

            let src = board.thumbnailName == null? `/image/board/sample-image.png`:`/upload/${board.thumbnailPath}/${board.thumbnailName}`;

            let likeClass = board.isLike === null? 'btn-like' : 'btn-like-on';

            let interestClass = board.isInterest === null? 'btn-interest' : 'btn-interest-on';


            let html = `<div class="board" data-bid="${board.id}">

                    <div class="board-top">
                        <div class="board-sticker">
                            <span class="sticker-cg">${board.categoryTitle}</span>
                            <span class="sticker-city">${board.city}</span>
                            <span class="sticker-region">${board.region}</span>
                            ${status}
                        </div>
        
<!--                        <button class="btn-dot">dot</button>-->
<!--        -->
<!--                        <div class="board-toolbox hide">-->
<!--                            <button class="btn-hide">숨기기</button>-->
<!--                            -->
<!--                            -->
<!--                        </div>-->
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
            boards.insertAdjacentHTML('beforeend', html);
        }

    },
    getCommentList: async function(cri) {

        boards.innerHTML = '';
        listSection.classList.add('dp-none');



        interests.innerHTML = '';
        interestSection.classList.add('dp-none');


        if (commentSection.classList.contains('dp-none')) {
            commentSection.classList.remove('dp-none');
        }
        let requestURL = `/mypages/api/v1/comments`;
        let query = '?';
        query += `page=${cri.page}`;
        let result = await fetch(requestURL+query).then(response => response.json());
        console.log(result);
        for (let comment of result) {
            let html = `<div class="board" data-bid="${comment.boardId}">
                    <div class="board-mid">
                        <div class="board-content-box">
                            <div class="board-title">
                                <a href="read?id=${comment.boardId}">${comment.title}</a>
                            </div>
                        </div>
                    </div>
                    <div class="board-bottom comment-list">
                        <div class="comment">
                            <p class="comment-content">${comment.content}</p>
                            <span class="comment-date">${displayedAt(new Date(comment.createdAt))}</span>
                        </div>
                    </div>
                </div>`;
            comments.insertAdjacentHTML('beforeend', html);
        }
    },
    getInterestList: async function(cri) {

        boards.innerHTML = '';
        listSection.classList.add('dp-none');



        comments.innerHTML = '';
        commentSection.classList.add('dp-none');


        if (interestSection.classList.contains('dp-none')) {
            interestSection.classList.remove('dp-none');
        }

        let requestURL = `/mypages/api/v1/interests`;

        let query = '?';
        /* mypage -> 내가 쓴 게시글 조회시, category별 조회만 가능(필터기능 X) */
        query += `page=${cri.page}&`;
        query += `category=${cri.category}`;

        requestURL = requestURL + query;

        let result = await fetch(requestURL).then(response => response.json());
        console.log(result);
        // flag 처리
        if (result.length === 0) flag = true;


        for (let board of result) {
            let status = board.status == '0'? '<span class="sticker-status">진행중</span>' : '<span class="sticker-status-off">종료</span>';

            let src = board.thumbnailName == null? `/image/board/sample-image.png`:`/upload/${board.thumbnailPath}/${board.thumbnailName}`;

            let likeClass = board.isLike === null? 'btn-like' : 'btn-like-on';

            let interestClass = board.isInterest === null? 'btn-interest' : 'btn-interest-on';


            let html = `<div class="board" data-bid="${board.id}">

                    <div class="board-top">
                        <div class="board-sticker">
                            <span class="sticker-cg">${board.categoryTitle}</span>
                            <span class="sticker-city">${board.city}</span>
                            <span class="sticker-region">${board.region}</span>
                            ${status}
                        </div>
        
<!--                        <button class="btn-dot">dot</button>-->
<!--        -->
<!--                        <div class="board-toolbox hide">-->
<!--                            <button class="btn-hide">숨기기</button>-->
<!--                            -->
<!--                            -->
<!--                        </div>-->
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
            interests.insertAdjacentHTML('beforeend', html);
        }
    }
};

dashboard.init(cri);

boardCategory.addEventListener('click', (e) => {
    if (e.target.tagName.toLowerCase() !== 'a') return;
    flag = false;
    boards.innerHTML = '';
    let categoryId = e.target.dataset.id;
    cri.init();
    cri.category = categoryId;
    dashboard.getList(cri);
});

activityType.addEventListener('click', e => {
    if (e.target.tagName.toLowerCase() !== 'a') return;
    flag = false;
    boards.innerHTML = '';
    comments.innerHTML = '';
    interests.innerHTML = '';
    let page = 1;
    cri.page = page;
    //dashboard.getCommentList(cri);
})

/*  */
// if(window.innerHeight >= document.body.offsetHeight){
//     cri.page++;
//     dashboard.getList(cri);
// }

/* Scroll Down -> Board List Request */
window.addEventListener('scroll', () => {
    if (flag === true) return;

    let val = window.innerHeight + window.scrollY;

    if(val >= main.clientHeight){
        cri.page++;
        if(boards.innerHTML.length > 0) {
            dashboard.getList(cri)
        } else if (comments.innerHTML.length > 0) {
            dashboard.getCommentList(cri)
        } else if (interests.innerHTML.length > 0) {
            dashboard.getInterestList(cri)
        }
    }
});
/*  */
const modal = {
    init: function() {
        const _this = this;
        const modal = document.querySelector('.modal-container');
        const overlay = modal.querySelector('.overlay');
        const passwordConfirm = modal.querySelector('.password-confirm');
        const updateInfo = modal.querySelector('.update-info');
        let addressInfo = null;
        const currentLocation = modal.querySelector('.current-location');

        //show modal for profile info
        profileBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            overlay.classList.remove('dp-none');
            passwordConfirm.classList.remove('dp-none');
        });

        //show modal for location info
        locationContainer.addEventListener('click', (e) => {
            e.preventDefault();
            initCurrentLocation();
            overlay.classList.remove('dp-none');
            currentLocation.classList.remove('dp-none');
        });


        //show off modal popup
        overlay.addEventListener('click', (e) => {
            const modals = modal.querySelectorAll('.modal-item');
            modals.forEach((ele) => {
                if(!ele.classList.contains('dp-none')) ele.classList.add('dp-none');
            });
            // reset password input value in password confirm modal
            if(document.querySelector('#verifyingPassword').value.length > 0) {
                document.querySelector('#verifyingPassword').value = '';
            }
            // remove existing info modal
            updateInfo.innerHTML = '';
        });

        //form handlers
        // enter key event for password form to submit
        passwordForm.addEventListener('keydown', function(e) {
            if (e.key !== 'Enter') return;
            if (e.target.tagName.toLowerCase() !== 'input') return;
            e.preventDefault();
            passwordForm.querySelector('.btn-submit').click();
        });
        // # password confirm form handling event
        passwordConfirm.addEventListener('click', async (e) => {
            //submit button event
            if (e.target.classList.contains('btn-submit')) {
                const requestURL = `/member/checkPassword`;
                const options = {
                    method: 'POST',
                    body: new FormData(passwordForm)
                };

                let result = await fetch(requestURL, options).then(response => response.json());
                if (result < 1) {
                    passwordConfirm.querySelector('.result-message').classList.remove('dp-none');
                    return;
                }
                // show update info modal here after input password is valid
                passwordConfirm.classList.add('dp-none');

                const infoHTML = `<form action="/mypage/update/info" method="POST" id="update-form" class="update-form" enctype="multipart/form-data">
                <div class="form-raw inner-profile">
                    <input type="file"
                            class="inner-profile-input"
                            name="profileImg"
                            id="inner-profileImg"/>
                    <div class="inner-profile-image">
                    <label for="inner-profileImg">
                        <i class="fas fa-camera"></i>
                    </label>
                </div>
                </div>
                <div class="form-raw">
                    <label for="email">이메일</label>
                    <input type="email" id="email" disabled/>
                </div>
                <div class="form-raw">
                    <label for="password">비밀번호</label>
                    <input type="password" name="password" id="password"/>
                </div>
                <div class="form-raw">
                    <label for="passwordConfirm">비밀번호 확인</label>
                    <input
                            type="password"
                            id="passwordConfirm"
                    />
                </div>
                <div class="form-raw">
                    <label for="nickname">닉네임</label>
                    <input type="text" name="nickname" id="nickname"/>
                </div>
                <div class="form-raw">
                    <label for="phone">휴대폰번호</label>
                    <input type="tel"
                           class="form-control m-input"
                           name="phone"
                           id="phone"
                           required
                           pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
                           maxlength="13"
                           placeholder="예) 010-1234-5678"/>
                </div>
                <div class="form-raw">
                    <label for="addr">주소</label>
                    <input type="text" name="addr" id="addr"/>
                </div>
                <div class="form-raw">
                    <label for="addrDtl">상세주소</label>
                    <input type="text" name="addrDtl" id="addrDtl"/>
                </div>
            </form>
            <div class="button-container">
                <a href="#" class="btn btn-submit">변경</a>
            </div>`;
                updateInfo.insertAdjacentHTML('beforeend', infoHTML);
                updateInfo.classList.remove('dp-none');

                //get user Info here
                let member = await fetch(`/mypage/info`).then(response => response.json());
                let attachment = await fetch(`/mypage/profileImg`).then(response => response.json());

                let profileImg = updateInfo.querySelector('.inner-profile-image');
                profileImg.style.background = `url(${attachment.path}) no-repeat center center/contain`;
                let email = updateInfo.querySelector('#email');
                email.value = member.email;
                let nickname = updateInfo.querySelector('#nickname');
                nickname.value = member.nickname;
                let phone = updateInfo.querySelector('#phone');
                phone.value = member.phone;
                let addr = updateInfo.querySelector('#addr');
                addr.value = member.addr;
                let addrDtl = updateInfo.querySelector('#addrDtl');
                addrDtl.value = member.addrDtl;

                addressInfo = updateInfo.querySelector('#addr');
                // address api
                addressInfo.addEventListener('click', () => {
                    const platformType = navigator.userAgent.indexOf('Mob') === -1 ? 'P' : 'M';
                    window.open(`/member/getAddrInfo?platformType=${platformType}`, 'pop', 'width=570,height=420, scrollbars=yes, resizable=yes');
                });
                // init validation after html loaded
                _this.validation();
            }
            //return false;

        });


        // # update info form handling event
        const previewImage = (e) => {
            const inputFile = document.querySelector('#inner-profileImg');
            const profileImage = document.querySelector('.inner-profile-image');

            if (e.target.tagName.toLowerCase() !== 'i') inputFile.click();

            inputFile.addEventListener('change', (e) => {
                if(!e.target.files[0].size) return;
                let url = URL.createObjectURL(e.target.files[0])
                profileImage.style.background = `url('${url}') no-repeat center center/contain`;
                inputFile.addEventListener('load', (e) => {
                    URL.revokeObjectURL(url);
                })
            });
        }



        updateInfo.addEventListener('click', async (e) => {
            //e.preventDefault();
            if (e.target.id === 'inner-profileImg') return;
            //image preview
            if (e.target.classList.contains('inner-profile-image') || e.target.classList.contains('fa-camera')) {
                previewImage(e);
                return;
            }

            // submit
            if(e.target.classList.contains('btn-submit')) {
                e.preventDefault();

                if (!document.getElementById('addr')) validations.address = false;

                const isValid = Object.values(validations).every(val => {
                    return val === true;
                });

                if(!isValid) return alert('입력하신 정보를 확인해주세요');

                // update here
                const requestURL = `/mypage/update/info`;
                const options = {
                    method: 'POST',
                    body: new FormData(document.getElementById('update-form'))
                };
                // document.getElementById('update-form').submit();
                let result = await fetch(requestURL, options).then(response => response.json());
                if (result !== 1) return alert('오류가 발생했습니다.\m관리자에게 문의해주세요.');
                alert('성공적으로 변경되었습니다.');
                location.href = `/mypage/dashboard`;
            }
        });
        // # update location info handler
        currentLocation.addEventListener('click', async (e) => {
            if(e.target.classList.contains('btn-submit')){
                e.preventDefault();
                const requestURL = `/mypage/update/address`;
                const options = {
                    method: 'POST',
                    body: new FormData(document.getElementById('location-form'))
                };

                let result = await fetch(requestURL, options).then(response => response.json());
                if (result !== 1) return alert('오류가 발생했습니다.\m관리자에게 문의해주세요.');
                alert('성공적으로 변경되었습니다.');
                location.href = `/mypage/dashboard`;
            }
        });
    },
    validation: function() {
        const nickname = document.getElementById('nickname');
        const originalNickname = nickname.value;
        const phone = document.getElementById('phone');
        //nickname check
        const isOverlappedNickname = (value) => {
            console.log('isOverlappedNickname invoked');
            const nicknameCheckingURL = `/member/isOverlapped/nicknames/${value}`;

            fetch(nicknameCheckingURL)
                .then(res => res.json())
                .then(data => {
                    if(data > 0) {
                        validations.nickname = false;
                        return alert('중복된 닉네임입니다.');
                    } else {
                        validations.nickname = true;
                    }
                })
                .catch(err => console.error(err));
        }

        nickname.addEventListener('blur', (e) => {
            validations.nickname = originalNickname === e.target.value;
            if (!validations.nickname) isOverlappedNickname(e.target.value);
        });

        // auto-hyphen in between phone numbers
        const autoHyphenPhone = (str) => {
            str = str.replace(/[^0-9]/g, '');
            let tmp = '';
            if (str.length < 4) {
                return str;
            } else if (str.length < 7) {
                tmp += str.substr(0, 3);
                tmp += '-';
                tmp += str.substr(3);
                return tmp;
            } else if (str.length < 11) {
                tmp += str.substr(0, 3);
                tmp += '-';
                tmp += str.substr(3, 3);
                tmp += '-';
                tmp += str.substr(6);
                return tmp;
            } else {
                tmp += str.substr(0, 3);
                tmp += '-';
                tmp += str.substr(3, 4);
                tmp += '-';
                tmp += str.substr(7);
                validations.phone = true;
                return tmp;
            }
            return str;
        }
        //phone number keyup event for auth-hyphen function
        phone.addEventListener('keyup', () => {
            validations.phone = false;
            phone.value = autoHyphenPhone(phone.value);
        });
    }
}

modal.init();

// callback from address
function putAddr(roadAddrPart1, addrDetail) {
    document.getElementById('addr').value = roadAddrPart1;
    document.getElementById('addrDtl').value = addrDetail;
}


/* Board의 ...버튼 클릭 */
boards.addEventListener('click', function (e) {

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


/* 게시글당 관심 버튼 */
boards.addEventListener('click', async function (e) {

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

/* 게시글당 좋아요 버튼 */
boards.addEventListener('click', async function (e) {

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
interests.addEventListener('click', async function (e) {

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

/* 게시글당 좋아요 버튼 */
interests.addEventListener('click', async function (e) {

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
//