const form = document.querySelector('form');
const pwdBtn = document.querySelector('#password-btn');
form.addEventListener('submit', ()=> {
    return false;
});
const accountFinderModule = {
    init: function() {
        const _this = this;
        form.addEventListener('click', function(e) {
            let target = e.target;
            // password changer
            if (target.id === 'password-btn') _this.changePassword();
            return;
        }); // end event listener
    }, // end init function
    changePassword: function() {
        console.log('changePassword invoked');
        //form.submit();
        let payload = {};
        new FormData(form).forEach((value, key) => payload[key] = value);
        console.log(payload);
        const url = `/member/checkExist`;
        const options = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            }
        };

        fetch(url, options)
            .then(res => res.json())
            .then(result => {
                if (result < 1) return alert('가입하신 회원정보가 올바르지 않습니다.');
                let url = `/member/update/password`;
                fetch(url, options)
                    .then(res => res.json())
                    .then(result => {
                        if(result == -1) return alert('요청이 실패되었습니다.\n다시 시도해주세요.');
                        alert('이메일 확인 후 변경된 비밀번호로 접속해주세요.');
                        location.href = '/';
                    })
                    .catch(err => console.log(err));
            })
            .catch(err => console.log(err));
    } // end changePassword function
}; // end module object
accountFinderModule.init();