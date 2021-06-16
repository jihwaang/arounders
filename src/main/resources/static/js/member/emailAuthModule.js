const emailAuthModule = {

    formSelector: document.querySelector('form'),

    emailAuth: null,

    init() {

        const _this = this;

        const form = _this.formSelector;

        form.addEventListener('click', function (e) {

            let formObj = {};
            new FormData(form).forEach((value, key) => formObj[key] = value);

            if (e.target.id === 'requestAuth') _this.requestAuth(formObj);

            if (e.target.id === 'submitBtn') _this.submit(formObj);

        }); // end event listener

    }, // end init

    requestAuth(formObj) {
        if(!formObj.email) return alert('이메일 아이디를 입력해주세요.');
        if(this.emailAuth) return alert('인증메일이 이미 발송되었습니다.');

        const url = '/emailAuth/auth';
        const options = {
            method: 'POST',
            body: JSON.stringify(formObj),
            headers: {
                'Content-type': 'application/json; charset=UTF-8'
            }
        };

        fetch(url, options)
        .then(res => res.json())
        .then(data => {
            this.emailAuth = data;
            alert('인증메일이 발송되었습니다.\n입력하신 메일을 통해 인증을 완료해주세요.');
            console.log(this.emailAuth);
         })
        .catch(err => console.log(err));
    }, // end requestAuth

    submit(formObj) {
        if(!this.emailAuth) return alert('이메일 인증이 필요합니다.');

        const url = '/emailAuth/confirmCheck';
        const options = {
            method: 'POST',
            body: JSON.stringify(this.emailAuth),
            headers: {
                'Content-type': 'application/json; charset=UTF-8'
            }
        };

        //email confirm check
        fetch(url, options)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            this.emailAuth = data;
            console.log(this.emailAuth);
            if (!this.emailAuth.confirmed) return alert('발송된 인증메일을 확인해주세요.');
            this.formSelector.submit();
        })
        .catch(err => console.log(err));


    }, // end submit

    async ajaxRequest(url, method, obj) {
        console.log(url, method, obj);
        const options = {
            method: method,
            body: JSON.stringify(obj),
            headers: {
                'Content-type': 'application/json; charset=UTF-8'
            }
        };

        try {
            let response = await fetch(url, options);
            let data = await response.json();
            return data;
        } catch(err) {
            console.log(err);
        }

    } // end ajax

}; // end object

emailAuthModule.init();
