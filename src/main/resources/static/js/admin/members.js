const main = document.querySelector('main');

const memberDashboard = document.querySelector('.member-dashboard');
const today = memberDashboard.querySelector('.today');
const valid = memberDashboard.querySelector('.valid');
const invalid = memberDashboard.querySelector('.invalid');

const memberBox = document.querySelector('.member-box')

const searchBox = document.querySelector('.search-form-box');
const field = searchBox.querySelector('select[name="field"]');
const keyword = searchBox.querySelector('input[name="keyword"]');
const btnMemberSearch = searchBox.querySelector('.btn-member-search');
const btnMemberReset = searchBox.querySelector('.btn-member-reset');

let page = 1;
let k = '';
let f = '';

/* 브라우저의 높이와, 브라우저의 scroll값, main의 높이 */
window.addEventListener('scroll', () => {

    let val = window.innerHeight + (window.scrollY - 70);

    if(val >= main.clientHeight){
        page++;
        getMembers(page)
    }
});

/* Member목록 조회 */
async function getMembers(page){

    let query = '?';

    if(f != '')
        query += `field=${f}&`;
    if(k != '')
        query += `keyword=${k}&`;

     const members = await axios({
        url: `/admin/api/v1/members/${page}${query}`,
        method: 'GET'
    });

     let html = '';

     members.data.forEach((member, idx) => {

        const id = member.id;
        const email = member.email;
        let nickname = member.nickname;
        const phone = member.phone;
        const roleId = member.roleId;
        const addr = member.addr;
        const addrDtl = member.addrDtl;
        const createdAt = member.createdAt;
        const droppedAt = member.droppedAt;
        const lastLoginAt = member.lastLoginAt;

        if(droppedAt)
            nickname = '탈퇴한 사용자';

        const user = roleId == '1'? 'selected':'';
        const admin = roleId == '0'? 'selected':'';

        html += `<div class="member">
            <div class="top">
                <span class="member-email">${email}</span>
                <span class="member-id">${id}</span>
            </div>
            <div class="mid">
                <div class="mid-top">
                    <div>
                        <span class="member-nickname">${nickname}</span> /
                        <span class="member-phone">${phone}</span>
                    </div>
                    <select class="member-role" name="role" data-id="${id}">
                        <option ${user} value="1">USER</option>
                        <option ${admin} value="0">ADMIN</option>
                    </select>
                </div>

                <div class="mid-bottom">
                    <div class="member-addr">${addr}</div>
                    <div class="member-addr-dtl">${addrDtl}</div>
                </div>
            </div>
            <div class="bottom">
                <span class="created">가입일 : ${getDate(new Date(createdAt))}</span>
                <span class="latest-login">최근 로그인: ${getDate(new Date(lastLoginAt))}</span>
            </div>
        </div>`;
     });

     memberBox.insertAdjacentHTML('beforeend', html);
}
getMembers(page);

/* User Role Change */
memberBox.addEventListener('change', async (e) => {

    if(e.target.tagName != 'SELECT') return;

    const memberId = e.target.dataset.id;
    const roleId = e.target.value;

    if(!confirm(`${memberId}번 회원의 권한을 변경하시겠습니까?`)) return;

    const result = await axios({
        url: `/admin/api/v1/members`,
        method: 'POST',
        data: {
            id: memberId,
            roleId: roleId
        }
    });

    alert(`${result.data}번 회원의 권한을 변경했습니다.`);
});

/* Member 검색 */
btnMemberSearch.addEventListener('click', (e) => {

    memberBox.innerText = '';

    const fieldParam = field.value;
    const keywordParam = keyword.value;

    f = fieldParam;
    k = keywordParam;
    page = 1;

    getMembers(page);
});
/* 검색 조건 초기화 */
btnMemberReset.addEventListener('click', (e) => {

    memberBox.innerText = '';

    f = '';
    k = '';
    page = 1;

    getMembers(page);
});

/* 가입/탈퇴한 사용자 수 */
(async () => {
    const usersCount = await axios({url: '/admin/api/v1/members/valid'});
    const droppedCount = await axios({url: '/admin/api/v1/members/invalid'});
    const todayCount = await axios({url: '/admin/api/v1/members/today'});

    valid.insertAdjacentHTML('beforeend',
        `<div class="value">${usersCount.data}</div>`);
    invalid.insertAdjacentHTML('beforeend',
        `<div class="value">${droppedCount.data}</div>`);
    today.insertAdjacentHTML('beforeend',
        `<div class="value">${todayCount.data}</div>`);
})();

function getDate(date){

    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();

    if(month.toString().length < 2) month = '0' + month;
    if(day.toString().length < 2) day = '0' + day;

    return `${year}/${month}/${day}`;
}