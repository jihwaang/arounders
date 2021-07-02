
/* slider-wrap */
const sliderBox = document.querySelector('.slider-box');
const slider = sliderBox.querySelector('.slider');
/* current slide Index */
let slideIndex = 0;
/* Items */
const file = sliderBox.querySelectorAll('.file');
const files = sliderBox.querySelector('.files');
/* number of slides */
const total = file.length;
/* frame width */
const sliderWidth = slider.clientWidth;
/* pagination */
const pagination = sliderBox.querySelector('.pagination');
/* set Pagination */
(() => {
    let html = '';
    for(let i=0; i<total; i++){
       let active = '';

       if(i === 0)
           active = 'active';

       html += `<li class="item ${active}"></li>`;
   }
   pagination.insertAdjacentHTML('beforeend', html);
})();
/* set Width of Items */
/*
slides.forEach((element) => {
    element.style.width = sliderWidth + 'px';
});
boardFiles.style.width = sliderWidth * total + 'px';
*/

/* Next / Prev */
const btnNext = sliderBox.querySelector('.btn-next');
const btnPrev = sliderBox.querySelector('.btn-prev');

btnNext.addEventListener('click', (e) => {
    plusSlides(1);
    paging();
});
btnPrev.addEventListener('click', () => {
    plusSlides(-1);
    paging();
});

function plusSlides(n) {
    showSlides(slideIndex += n);
}
function currentSlides(n){
    showSlides(slideIndex = n);
}
function showSlides(n) {
    slideIndex = n;

    if(slideIndex == -1) {
        slideIndex = total - 1;
    }
    else if(slideIndex === total){
        slideIndex = 0;
    }

    files.style.left = -(sliderWidth * slideIndex) + 'px';
}

/* Paggination */
function paging(){

    const items = pagination.querySelectorAll('li');

    items.forEach((item, idx) => {
        if(item.classList.contains('active'))
            item.classList.remove('active');
    });

    items[slideIndex].classList.add('active');
}