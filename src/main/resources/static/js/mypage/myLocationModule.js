const updateBtn = document.getElementById('update-btn');
const form = document.getElementById('location-form');
const myLocationModule = {
    init: function() {
        const _this = this;
        updateBtn.addEventListener('click', () => {
            _this.updateAddress();
        });
    },
    updateAddress: function() {
        form.submit();
    }
};
myLocationModule.init();