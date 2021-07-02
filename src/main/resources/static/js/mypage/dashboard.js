const profileBtn = document.querySelector('.profile-btn');
const locationBtn = document.querySelector('.location-btn');
const locationContainer = document.querySelector('.button-container');
const passwordForm = document.querySelector('#password-confirm-form');
const locationForm = document.querySelector('#location-form');



// validation
const validations = {
    nickname: true,
    phone: true,
    address: true,
};
const dashboard = {
    init: function () {
        const _this = this;
        _this.getList();

    },
    getList: async function() {
        const requestURL = `/boards/api/v1?`;

        let board = await fetch().then(response => response.json());
        console.log(board);
        let listHTML = `<div class="board">
                        <span>${board.category}</span>
                        <span>${board.region}</span>
                        <span>${board.status == '0'? '미완료':'완료'}</span>
                        <div><a href="read?id=${board.id}">${board.title}</a></div>
                        <div>${board.writer}</div>
                        <div>${board.regDate}</div>
                        <div><img src="/upload/${board.thumbnailPath}/${board.thumbnailName}"></div>
                        <div>댓글수: ${board.commentCount}</div>
                        <div>좋아요수 :${board.likeCount}</div>
                        <div>관심수 : ${board.interestCount}</div>
                        <hr>
                    </div>`;
    }
};

dashboard.init();

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
            console.log('previewImage function invoked');
            const inputFile = document.querySelector('#inner-profileImg');
            const profileImage = document.querySelector('.inner-profile-image');

            inputFile.click();
            inputFile.addEventListener('change', (e) => {
                console.log('image change');
                console.log(e.target);
                if(!e.target.files[0].size) return;
                let url = URL.createObjectURL(e.target.files[0])
                profileImage.style.background = `url('${url}') no-repeat center center/contain`;
                inputFile.addEventListener('load', (e) => {
                    URL.revokeObjectURL(url);
                })
            });
        }

        updateInfo.addEventListener('click', async (e) => {
            if (e.target.tagName.toLowerCase() === 'i' || e.target.id === 'inner-profileImg') return;
            //image preview
            if (e.target.classList.contains('inner-profile-image')) {
                console.log('image preview');
                previewImage(e);
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

// const boardBox = document.querySelector('.boards');
// const categoryList = document.querySelector('.category-list');
// const categoryKey = categoryList.querySelector('.category-key');
//
// const cri = BoardCriteria;
//
// showList(cri);
// if(window.innerHeight >= document.body.offsetHeight){
//     cri.page++;
//     showList(cri);
// }
//
// /* Scroll Down -> Board List Request */
// window.addEventListener('scroll', () => {
//     let val = window.innerHeight + window.scrollY;
//
//     if(val >= document.body.offsetHeight){
//         cri.page++;
//         showList(cri);
//     }
// });
//
// categoryList.addEventListener('click', (e) => {
//
//     if(!e.target.classList.contains('category-key')) return;
//
//     boardBox.innerHTML = '';
//     const category = e.target.dataset['id'];
//
//     cri.init();
//     cri.set(null, null, category, null, null);
//     showList(cri);
// });
//
// async function showList(cri){
//
//     const result = await boardModule.getMyList(cri);
//
//     /*if(result.length == 0) {
//         alert('마지막 페이지입니다.');
//         return;
//     }*/
//
//     result.forEach((board, idx) => {
//         console.log(board);
//
//         let html = '';
//         html = `<div class="board">
//                     <span>${board.category}</span>
//                     <span>${board.region}</span>
//                     <span>${board.status == '0'? '미완료':'완료'}</span>
//                     <div><a href="/board/read?id=${board.id}">${board.title}</a></div>
//                     <div>${board.writer}</div>
//                     <div>${board.createdAt}</div>
//                     <div><img src="/upload/${board.thumbnailPath}/${board.thumbnailName}"></div>
//                     <div>댓글수: ${board.commentCount}</div>
//                     <div>좋아요수 :${board.likeCount}</div>
//                     <div>관심수 : ${board.interestCount}</div>
//                     <hr>
//                 </div>`
//
//         boardBox.insertAdjacentHTML('beforeend', html);
//     });
// }