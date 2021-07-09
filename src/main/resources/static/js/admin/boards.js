window.addEventListener('load', () => {

    const main = document.getElementsByTagName('main')[0];
    const boardBox = document.querySelector('.boards');

    const boardDashboard = document.querySelector('.boards-dashboard');
    const today = boardDashboard.querySelector('.today');
    const process = boardDashboard.querySelector('.process');
    const finished = boardDashboard.querySelector('.finished');

    let page = 1;

    window.addEventListener('scroll', () => {

        let val = window.innerHeight + (window.scrollY - 70);

        if(val >= main.clientHeight){
            page++;
            getBoards();
        }
    });

    getBoards();
    async function getBoards(){

        const result = await axios({
            url: `/admin/api/v1/boards?page=${page}`
        });

        let html = '';

        result.data.forEach((board, idx) => {
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
        
                        <!--<div class="board-toolbox hide">
                        
                            <button class="btn-hide">숨기기</button>
                            <button class="btn-report">신고하기</button>   
                        </div>-->
                    </div>
        
                    <div class="board-mid">
                        <div class="board-thumbnail-box">
                            <img src="${src}" alt="썸네일 이미지" class="board-thumbnail">
                        </div>
        
                        <div class="board-content-box">
                            <div class="board-title">
                                <a class="btn-to-read" id="${board.id}" data-id="${board.id}" href="#">${board.title}</a>
                            </div>
                            <div class="board-info">
                                <span class="board-nickname">${board.writer}</span>
                                <span class="board-date">${board.createdAt}</span>
                            </div>
                        </div>
                    </div>
        
                </div>`;
        });
        boardBox.insertAdjacentHTML('beforeend', html);
    }

    /* 게시글 수 */
    (async () => {
        const processCount = await axios({url: '/admin/api/v1/boards/process'});
        const finishCount = await axios({url: '/admin/api/v1/boards/finish'});
        const todayCount = await axios({url: '/admin/api/v1/boards/today'});

        process.insertAdjacentHTML('beforeend',
            `<div class="value">${processCount.data}</div>`);
        finished.insertAdjacentHTML('beforeend',
            `<div class="value">${finishCount.data}</div>`);
        today.insertAdjacentHTML('beforeend',
            `<div class="value">${todayCount.data}</div>`);
    })();
});

