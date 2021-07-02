const regForm = document.querySelector('#regForm');
const inputFile = regForm.querySelector('input[type="file"]');
const thumbIdx = regForm.querySelector('input[name="thumbIdx"]');

const frame = regForm.querySelector('.thumbnail-frame');
const hint = regForm.querySelector('.hint');

const btnBack = document.querySelector('.button-back');
const btnSubmit = regForm.querySelector('.btn-submit');
const btnUpload = regForm.querySelector('.btn-upload');

/* Modal */
const modal = document.querySelector('.modal');
const modalBodyContent = modal.querySelector('.modal-body-content');
const btnClose = modal.querySelector('.btn-close');
const btnCancel = modal.querySelector('.btn-cancel');

let selected = null;

/* upload button click -> file upload */
inputFile.addEventListener('input', function (e) {

    frame.innerHTML = '';
    hint.classList.remove('hide');

    const files = inputFile.files;

    if(files.length > 5){

        modalBodyContent.innerText = '이미지는 5개 이하로만 가능합니다.';
        modal.style.display = 'block';

        return;
    }

    for(let i=0; i<files.length; i++) {
        if (!isImage(files[i])) {

            modalBodyContent.innerText = '이미지 파일만 업로드 해주세요.';
            modal.style.display = 'block';

            inputFile.value = '';
            return;
        }
    }

    for(let i=0; i<files.length; i++) {
        const reader = new FileReader();

        reader.addEventListener('load', () => {

            const img = `<img src="${reader.result}" class="thumbnail">`;

            frame.insertAdjacentHTML('beforeend', img);
        });

        reader.readAsDataURL(files[i]);
    }
});

btnBack.addEventListener('click', () => {
    history.back();
});

/* Select Thumbnail Index */
frame.addEventListener('click', function (e) {

    if(e.target.tagName !== 'IMG') return;

    const img = e.target;

    if(selected != null){
        selected.classList.toggle('selected');
    }

    selected = img;
    selected.classList.toggle('selected');

    thumbIdx.value = getThumbIdx(img.parentElement);
});

function getThumbIdx(parent){

    const children = parent.children;

    for(let i=0; i < children.length; i++)
        if(children[i].classList.contains('selected'))
            return i;
}

function isImage(file) {
    return file.type.indexOf('image') >= 0;
}

/*Modal Close 버튼 Click*/
btnClose.addEventListener('click', () => {
    modal.style.display = 'none';
});
btnCancel.addEventListener('click', () => {
    modal.style.display = 'none';
});