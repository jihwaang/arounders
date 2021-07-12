//declare
const reviewList = document.querySelector('.review-list');
const bottom = document.querySelector('.bottom-container');
const modal = document.querySelector('.modal-container');
const modalItems = modal.querySelectorAll('.modal-item');
const overlay = modal.querySelector('.overlay');
const form = modal.querySelector('.input-form');
const stars = form.querySelectorAll('.fa-star');
const content = form.querySelector('#content');
const confirm = modal.querySelector('.confirm-cancel');
const exit = modal.querySelector('.btn-exit');

//event
reviewList.addEventListener('click', (e) => {
   if (e.target.tagName.toLowerCase() !== 'a') return;
    e.preventDefault();
    modalObj.openModal(e);
});

// 1. overlay display off
overlay.addEventListener('click', () => {
   overlay.classList.add('dp-none');
    modalItems.forEach(ele => {
        if (!ele.classList.contains('dp-none')) ele.classList.add('dp-none');
    })
    content.value = '';
    document.getElementById('byte-length').textContent = '0';
    document.getElementById('reviewId').value = '';
    document.getElementById('rate').value = '';
});
// 2. bottom click event
bottom.addEventListener('click', (e) => {
   if (!e.target.classList.contains('btn-write')) return;
    e.preventDefault();
   modalObj.openModal(e);
});

// 3. exit button click event
exit.addEventListener('click', (e) => {
   e.preventDefault();
   overlay.click();
});

// 4. form submit event
form.addEventListener('click', (e) => {
    e.preventDefault();
    let className = e.target.classList;
    //send data
    if (className.contains('btn-write')) {
        dataRequest.insertData(form);
        //formHandler.sendData(form);
    } else if (className.contains('btn-update')) {
        dataRequest.updateData(form);
    }

    //check rate[s]
    if (className.contains('fa-star')) {
        //handling remove existing rates
        stars.forEach(star => {
            if(star.classList.contains('check')) star.classList.remove('check');
            if(star.classList.contains('fas')) star.classList.replace('fas', 'far');
        });

        //check
        className.add('check');

        //change color of stars
        for (let i = 0; i < stars.length; i++) {
            if (stars[i].classList.contains('check')) {
                stars[i].classList.replace('far', 'fas');
                break;
            }
            stars[i].classList.replace('far', 'fas');
        }

        //get the value of checked star
        let rate = e.target.getAttribute('data-index');
        document.getElementById('rate').value = rate;
        //check rate[e]
    }
});
// byte check event
form.addEventListener('keydown', (e) => {
    if (!e.target.classList.contains('content')) return;
    // check byte
    let length = e.target.value.length;
    let maxLength = parseInt(e.target.getAttribute('data-max-length'));

    const byteLength = form.querySelector('#byte-length');
    byteLength.textContent = length;
});

// confirm click event
confirm.addEventListener('click', (e) => {
    if (!e.target.classList.contains('btn')) return;
    e.preventDefault();
    // delete handle
    if (e.target.classList.contains('btn-delete')) {
        dataRequest.deleteData(form.querySelector('#reviewId').value);
        form.querySelector('#reviewId').value = '';
    } else if (e.target.classList.contains('btn-cancel')) {
        overlay.click();
    }
})

//modal object
const modalObj = {
    openModal: async function(e) {
        // remove existing rate
        stars.forEach(star => {
            if(star.classList.contains('check')) star.classList.remove('check');
            if(star.classList.contains('fas')) star.classList.replace('fas', 'far');
        });

        // overlay turn on
        overlay.classList.remove('dp-none');

        // open modal based on the event
        // 1. create
        if (e.target.classList.contains('btn-write')) {
            form.classList.remove('dp-none');
            //button enable and disable
            form.querySelector('.btn-write').classList.remove('dp-none');
            form.querySelector('.btn-update').classList.add('dp-none');
        // 2. update
        } else if (e.target.classList.contains('edit')) {
            //button enable and disable
            form.querySelector('.btn-update').classList.remove('dp-none');
            form.querySelector('.btn-write').classList.add('dp-none');
            // fetch review data here
            const reviewId = e.target.parentNode.parentNode.querySelector('[name=reviewId').value;

            const requestURL = `/reviews/api/v1/review/${reviewId}`;
            const review = await fetch(requestURL).then(response => response.json());

            document.getElementById('reviewId').value = reviewId;
            document.getElementById('rate').value = review.rate;
            document.getElementById('content').value = review.content;
            document.getElementById('byte-length').textContent = content.value.length.toString();

            const stars = modal.querySelectorAll('.fa-star');
            for (let i = 0; i < 5; i++) {
                if (parseInt(stars[i].getAttribute('data-index')) <= parseInt(review.rate)) {
                    stars[i].classList.replace('far', 'fas');
                }
            }
            form.classList.remove('dp-none');
        // 3. delete
        } else if (e.target.classList.contains('delete')) {
            form.querySelector('#reviewId').value = e.target.parentNode.parentNode.querySelector('[name=reviewId]').value;
            confirm.classList.remove('dp-none');
        }
    }
}

const dataRequest = {
    pagination: {
      offset: 0,
      isMore: true
    },
    rateFactory: function(rate) {
        let html = '';
        for (let i = 1; i <= 5; i++) {
            html += i <= parseInt(rate) ? '<i class="fas fa-star"></i>' : '<i class="far fa-star"></i>';
        }
        return html;
    },
    getReviews: async function() {
        const requestURL = `/reviews/api/v1/boards/${boardId}?offset=${this.pagination.offset}`;
        const reviews = await fetch(requestURL).then(response => response.json());
        const csrfToken = '<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>';
        if (reviews.length < 10) this.pagination.isMore = false;

        for (let review of reviews) {
            const className = memberId == review.memberId ? '' : 'dp-none';
            let html = `<div class="review-item">
                <div class="item-title">
                    ${csrfToken}
                    <input type="hidden" name="reviewId" value="${review.id}"/>
                    <span class="nickname">${review.writer}</span>
                    <span class="creation">${review.createdAt.toString().substr(0, 10)}</span>
                </div>
                <div class="item-rate">
                        ${this.rateFactory(review.rate)}
                </div>
                <div class="item-content">
                    <p class="content">${review.content}</p>
                </div>
                <div class="item-control ${className}">
                    <a class="edit">수정</a>
                    <a class="delete">삭제</a>
                </div>
            </div>`;
            reviewList.insertAdjacentHTML('beforeend', html);
        }
    },
    deleteData: async function(reviewId) {
        const requestURL = `/reviews/api/v1/${reviewId}`;
        const csrfToken = document.querySelector('input[name=_csrf]').value;
        const options = {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        };
        let result = await fetch(requestURL, options).then(response => response.json());
        if (result > 0) {
            alert('리뷰가 삭제되었습니다.');
            reviewList.innerHTML = '';
            overlay.click();
            this.getReviews();
        } else {
            alert('오류가 발생했습니다.\n다시 시도해주세요.');
            return;
        }
    },
    updateData: async function(form) {
        let payload = {};
        const csrfToken = document.querySelector('input[name=_csrf]').value;
        new FormData(form).forEach((val, key) => payload[key] = val);
        if (!payload.rate) return alert('별점을 선택해주세요');
        if (!payload.content) return alert('내용을 입력해주세요');
        const requestURL = `/reviews/api/v1/${payload.id}`;
        const options = {
            method: 'PUT',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
                'X-CSRF-TOKEN': csrfToken
            }
        }
        let result = await fetch(requestURL, options).then(response => response.json());
        if (result < 1) return alert('오류가 발생했습니다.\n다시 시도해주세요.');
        alert('수정이 완료되었습니다.');
        document.getElementById('rate').value = '';
        reviewList.innerHTML = '';
        overlay.click();
        this.getReviews();
    },
    insertData: async function(form) {
        if (document.querySelector('#content').value.length > 200) return alert('200자를 초과입력 할 수 없습니다.');
        const csrfToken = document.querySelector('input[name=_csrf]').value;
        let formData = new FormData(form);
        formData.append('boardId', boardId);
        let payload = {};
        formData.forEach((val, key) => payload[key] = val);
        if (!payload.rate) return alert('별점을 선택해주세요');
        if (!payload.content) return alert('내용을 입력해주세요');
        // check duplicates
        let requestURL = '/reviews/api/v1/duplicates';
        let options = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'X-CSRF-TOKEN': csrfToken
            }
        };
        let result = await fetch(requestURL, options).then(response => response.json());

        if (result > 0) return alert('이미 리뷰를 등록하셨습니다.');

        // insert data
        requestURL = '/reviews/api/v1/create';
        options = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'X-CSRF-TOKEN': csrfToken
            }
        }

        result = await fetch(requestURL, options).then(response => response.json());
        if (result < 1) return alert('오류가 발생했습니다.\n관리자에게 문의해주세요.');
        alert('성공적으로 등록되었습니다.');
        reviewList.innerHTML = '';
        this.pagination.isMore = true;
        this.pagination.offset = 0;
        dataRequest.getReviews();
        overlay.click();
    },
    getMyReviews: async function() {
        const requestURL = `/reviews/api/v1/reviews?offset=${this.pagination.offset}`;
        const reviews = await fetch(requestURL).then(response => response.json());
        console.log(reviews);
        if (reviews.length < 10) this.pagination.isMore = false;

        for (let review of reviews) {
            let html = `<div class="review-item">
                <div class="item-title">
                    <input type="hidden" name="reviewId" value="${review.id}"/>
                    <span class="nickname">${review.writer}</span>
                    <span class="creation">${review.createdAt.toString().substr(0, 10)}</span>
                </div>
                <div class="item-rate">
                        ${this.rateFactory(review.rate)}
                </div>
                <div class="item-content">
                    <p class="content">${review.content}</p>
                </div>
                <div class="item-control">
                    <a class="edit">수정</a>
                    <a class="delete">삭제</a>
                </div>
            </div>`;
            reviewList.insertAdjacentHTML('beforeend', html);
        }
    }
};

window.addEventListener('load', () => {
    let currentURL = location.pathname.split('/')[1];
    if (currentURL == 'board') {
        dataRequest.getReviews();
    } else if (currentURL == 'mypage') {
        return;
    }

});

window.addEventListener('scroll', () => {
   const { scrollTop, scrollHeight, clientHeight } = document.documentElement;
   if (clientHeight + scrollTop >= scrollHeight - 5) {
       // check if more to show
       if (!dataRequest.pagination.isMore) return;
       // if so, get reviews again
       dataRequest.pagination.offset += 10;
       dataRequest.getReviews();
   }
});
