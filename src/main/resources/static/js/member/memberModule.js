const signupForm = document.querySelector('#form-signup');

const validations = {
    email: false,
    password: false,
    nickname: false,
    phone: false
};

const hasAddress = {
    flag: false
};
let agreedWithLocation = false;
let emailAuth = null;

const memberModule = {
    init: function() {
        //declare this object
        const _this = this;

        //select elements of the form for event
        const email = document.getElementById('signup-email');
        const password = document.getElementById('signup-password');
        const passwordConfirmed = document.getElementById('passwordConfirmed');
        const nickname = document.getElementById('nickname');
        const phone = document.getElementById('phone');
        //const addressFinder = document.getElementById('addr');
        const submitButton = document.getElementById('submitBtn');

        //password check
        passwordConfirmed.addEventListener('blur', () => {
            validations.password = passwordConfirmed.value === password.value ? true : false;
        });

        //nickname check
        nickname.addEventListener('blur', (e) => {
            console.log(e.target.value);
            _this.isOverlapped(e.target.value);
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
            phone.value = autoHyphenPhone(phone.value);
        });


        let htmlFlag = false;
        // validation on submit
        submitButton.addEventListener('click', async (e) => {

            e.preventDefault();
            if(!emailAuth) return alert('이메일 인증이 필요합니다.');

            if(!emailAuth.confirmed) {
                console.log('test');
                const csrfToken = document.querySelector('input[name=_csrf]').value;
                const url = '/emailAuth/confirmCheck';
                const options = {
                    method: 'POST',
                    body: JSON.stringify(emailAuth),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8',
                        'X-CSRF-TOKEN': csrfToken
                    }
                }

                let result = await _this.checkConfirm();
                console.log(result);
                if (result) {
                    emailAuth = result;
                    if (!emailAuth.confirmed) return alert('발송된 인증메일을 확인해주세요.');
                    validations.email = true;
                }
            }
                    const isValid = Object.values(validations).every(val => {
                        return val === true;
                    });

                    if(!isValid) return alert('입력하신 정보를 확인해주세요');

                    if(!htmlFlag) {
                        const html = `<label for="addr">주소<span class="required">*</span></label>
                                    <input class="form-input-text" type="text" id="addr" name="addr" readonly/>
                                    <label for="addrDtl">상세주소(직접입력)</label>
                                    <input class="form-input-text" type="text" id="addrDtl" name="addrDtl"/>
                                    <a class="btn-location-agreement" href="#" id="location-agreement">위치정보수집 동의하기</a>`;
                        const inputContainer = document.querySelector('#input-container');
                        inputContainer.insertAdjacentHTML('beforeend', html);
                        htmlFlag = true;
                    }

                //address api
                const addressFinder = document.querySelector('#addr');
                addressFinder.addEventListener('click', () => {
                    const platformType = navigator.userAgent.indexOf('Mob') === -1 ? 'P' : 'M';
                    window.open(`/member/getAddrInfo?platformType=${platformType}`,'pop','width=570,height=420, scrollbars=yes, resizable=yes');
                });

                // location agreement
                document.getElementById('location-agreement').addEventListener('click', (e) => {
                    //if (!e.target.classList.contains('btn-location-agreement')) return;
                    e.preventDefault();

                    if (!agreedWithLocation) getAgreement();

                    function success() {
                        document.getElementById('location-agreement').innerText = '동의완료';
                        agreedWithLocation = true;
                        alert('위치정보수집 동의가 완료되었습니다.');
                        return false;
                    }
                    function error() {
                        alert('위치정보수집 동의를 완료해주세요.');
                        return false;
                    }

                    function getAgreement() {
                        if(navigator.geolocation) {
                            alert('위치정보수집 동의창이 뜨면 \'허용\'버튼을 눌러주세요.');
                            navigator.geolocation.getCurrentPosition(success, error);
                        } else {
                            alert('지도 API를 지원하지 않는 브라우저 입니다.');
                            return false;
                        }
                    }
                });
                if(addressFinder.value.length > 0) hasAddress.flag = true;
                if(!hasAddress.flag) return alert('소중한 정보입력 감사합니다.\n주소정보를 추가로 입력해주세요 :)');
                if(!agreedWithLocation) return alert('위치정보수집 동의가 필요합니다.');
                alert('회원가입에 성공하였습니다.');
                signupForm.submit();
        }); // end validation
    }, // end init

    isOverlapped : function(value) {
        const url = `/member/isOverlapped/nicknames/${value}`;
        console.log(url);
        const options = {
            method: 'GET',
        };
        fetch(url, options)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            if(data > 0) {
                validations.nickname = false;
                return alert('중복된 닉네임입니다.');
            } else {
                validations.nickname = true;
            }
        })
        .catch(err => console.error(err));
    }, // end isOverlapped

    checkConfirm: async function() {
        const csrfToken = document.querySelector('input[name=_csrf]').value;
        const url = '/emailAuth/confirmCheck';
        const options = {
            method: 'POST',
            body: JSON.stringify(emailAuth),
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
                'X-CSRF-TOKEN': csrfToken,
            },
        }
        return await fetch(url, options).then(response => response.json()).catch(err => err);
    } // end checkConfirm

}; // end module object

memberModule.init();
//address callback function
// function putAddr(roadAddrPart1, addrDetail){
//     document.getElementById('addr').value = roadAddrPart1;
//     document.getElementById('addrDtl').value = addrDetail;
//     validations.address = true;
// };

