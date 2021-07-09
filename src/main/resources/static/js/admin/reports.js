const main = document.querySelector('main');

const reportsDashboard = document.querySelector('.reports-dashboard');
const today = reportsDashboard.querySelector('.today');
const process = reportsDashboard.querySelector('.process');
const finished = reportsDashboard.querySelector('.finished');

const reportBox = document.querySelector('.reports');

const searchBox = document.querySelector('.reports-search-form');
const field = searchBox.querySelector('select[name="field"]');
const keyword = searchBox.querySelector('input[name="keyword"]');
const btnReportSearch = searchBox.querySelector('.btn-report-search');
const btnReportReset = searchBox.querySelector('.btn-report-reset');

let page = 1;
let f = null;
let k = null;

/* 브라우저의 높이와, 브라우저의 scroll값, main의 높이 */
window.addEventListener('scroll', () => {

    let val = window.innerHeight + (window.scrollY - 70);

    if(val >= main.clientHeight){
        page++;
        getReports();
    }
});

/* 신고 목록 조회 */
async function getReports(){

    let query = `?page=${page}&`;

    if(f == 'b')
        query += `boardId=${k}&`;
    if(f == 's')
        query += `status=${k}`;

    const reports = await axios({url: `/reports/api/v1${query}`});

    let html = '';

    reports.data.forEach((report, idx) => {

        let finish = report.isFinished == '1'? 'selected':'';
        let process = report.isFinished == '1'? 'disabled':'selected';

        html += `
            <div class="report">

            <div class="top">
                <span class="region">${report.region}</span>
                <span class="board-id" title="게시글 숨기기">${report.boardId}</span>
            </div>

            <div class="mid-top">
                <span class="title">${report.title}</span>
            </div>

            <div class="mid-bottom">
                <span class="writer">작성자: ${report.writer}</span>
                <div class="date-box">
                    <span class="writed-at">작성일: ${getDate(new Date(report.boardCreatedAt))}</span>
                    <span class="created-at">신고일: ${getDate(new Date(report.createdAt))}</span>
                </div>
            </div>

            <div class="bottom">
                <span class="reporter">신고자: ${report.reporter}</span>
                <select class="status" data-id="${report.id}">
                    <option ${process} value="0">접수</option>
                    <option ${finish} value="1">처리</option>
                </select>
            </div>

        </div>`;
    });

    reportBox.insertAdjacentHTML('beforeend', html);
}
getReports();

function getDate(date){

    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();

    if(month.toString().length < 2) month = '0' + month;
    if(day.toString().length < 2) day = '0' + day;

    return `${year}/${month}/${day}`;
}

/* 신고 처리 이미 처리된 것은 상태 변경 불가 */
reportBox.addEventListener('change', async (e) => {

    if(!e.target.classList.contains('status')) return;

    const reportId = e.target.dataset.id;

    if(!confirm(`${reportId}번 신고를 처리하시겠습니까?`)) return;

    const result = await axios({
        url: `/reports/api/v1/${reportId}`,
        method: 'PUT'
    });

    alert(`${result.data}번 신고의 처리가 완료되었습니다.`);

    e.target.querySelector('option[value="1"]').selected = true;
    e.target.querySelector('option[value="0"]').disabled = true;
});

/* boardId Click -> 게시글 숨기기 */
reportBox.addEventListener('click', async (e) => {

    if(!e.target.classList.contains('board-id')) return;

    const boardId = e.target.innerText;

    if(!confirm(`${boardId}번 게시글을 숨기기 하시겠습니까?`)) return

    const result = await axios({
        url: `/boards/api/v1/${boardId}`,
        method: 'POST'
    });

    alert(`${result.data}번 게시글이 숨기기 되었습니다.`);
});

/* 신고 검색 */
btnReportSearch.addEventListener('click', (e) => {

    reportBox.innerText = '';

    const fieldParam = field.value;
    const keywordParam = keyword.value;

    f = fieldParam;
    k = keywordParam;
    page = 1;

    getReports();
});
/* 검색 조건 초기화 */
btnReportReset.addEventListener('click', (e) => {

    reportBox.innerText = '';

    f = '';
    k = '';
    page = 1;

    getReports();
});

/* 신고 수 */
(async () => {
    const processCount = await axios({url: '/reports/api/v1/process'});
    const finishCount = await axios({url: '/reports/api/v1/finish'});
    const todayCount = await axios({url: '/reports/api/v1/today'});

    process.insertAdjacentHTML('beforeend',
        `<div class="value">${processCount.data}</div>`);
    finished.insertAdjacentHTML('beforeend',
        `<div class="value">${finishCount.data}</div>`);
    today.insertAdjacentHTML('beforeend',
        `<div class="value">${todayCount.data}</div>`);
})();