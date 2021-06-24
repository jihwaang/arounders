const form = document.getElementById('user-info');
const addressFinder = document.getElementById('addr-finder');

const myInfoModule = {
    init: function() {
        const _this = this;
        /* event on submit */
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            let result = await this.submit();
            if(result < 1) return alert('변경정보를 확인해주세요.');
            alert('성공적으로 변경되었습니다.');
            location.href = `/board/list`; // need to be renamed to /mypage
        });
        /* event on address finder */
        addressFinder.addEventListener('click', () => {
            _this.findAddress();
        });
    },
    submit: function() {
        const url = `/mypage/update/info`;
        const options = {
            method: 'POST',
            body: new FormData(form)
        };
        return fetch(url, options).then(res => res.json()).then(data => data);
    },
    findAddress: function() {
        const platformType = navigator.userAgent.indexOf('Mob') === -1 ? 'P' : 'M';
        window.open(`/member/getAddrInfo?platformType=${platformType}`,'pop','width=570,height=420, scrollbars=yes, resizable=yes');
    }
};
myInfoModule.init();

function putAddr(roadAddrPart1, addrDetail){
    document.getElementById('addr').value = roadAddrPart1;
    document.getElementById('addrDtl').value = addrDetail;
};
