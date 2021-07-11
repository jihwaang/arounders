const emailAuthModule = {

    init() {

        const _this = this;
        const form = document.querySelector('.form-signup');

        form.addEventListener('click', function (e) {

            let formObj = {};
            new FormData(form).forEach((value, key) => formObj[key] = value);

            if (e.target.id === 'requestAuth') _this.requestAuth(formObj);

            //if (e.target.id === 'submitBtn') _this.submit(formObj);

        }); // end event listener

    }, // end init

    async requestAuth(formObj) {
        if (!formObj.email) return alert('이메일 아이디를 입력해주세요.');
        const csrfToken = document.querySelector('input[name=_csrf]').value;
        const emailCheckUrl = `/member/checkEmail`;
        const emailCheckOptions = {
            method: 'POST',
            body: formObj.email,
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'X-CSRF-TOKEN': csrfToken
            }
        };

        let emailCount = await fetch(emailCheckUrl, emailCheckOptions).then(res => res.json()).then(data => data);

        if (emailCount > 0) return alert('이미 가입되어있는 이메일 입니다.');

        if (emailAuth) return alert('인증메일이 이미 발송되었습니다.');


        alert('인증메일이 발송되었습니다.\n입력하신 메일을 통해 인증을 완료해주세요.');

        const url = '/emailAuth/auth';
        const options = {
            method: 'POST',
            body: JSON.stringify(formObj),
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'X-CSRF-TOKEN': csrfToken
            }
        };

        fetch(url, options)
        .then(res => res.json())
        .then(data => {
            emailAuth = data;
            document.querySelector('#requestAuth').style.backgroundColor = '#999 !important';
            document.getElementById('requestAuth').value = '인증요청완료';


         })
        .catch(err => console.log(err));
    }, // end requestAuth
}; // end object

emailAuthModule.init();
