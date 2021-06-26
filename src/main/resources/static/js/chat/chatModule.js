export const chatModule = {

    /* 특정 채팅방의 채팅 내역 조회 */
    getChats(chatRoomId){
        return new Promise((resolve, reject) => {
            ajax({
                url:`/chats/api/v1/${chatRoomId}`,
                method: 'GET',
                loadend: (data) => {
                    const json = JSON.parse(data);

                    resolve(json);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* Mypage에서 내가 참여한 채팅방 목록 조회 */
    getMyChatRooms() {
        return new Promise((resolve, reject) => {
            ajax({
                url:`/chatMember/api/v1`,
                method: 'GET',
                loadend: (data) => {
                    const json = JSON.parse(data);

                    resolve(json);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 특정 채팅방 참여여부 */
    isJoin(chatRoomId){
        return new Promise((resolve, reject) => {

            ajax({
                url: `/chatMember/api/v1/${chatRoomId}`,
                method: 'GET',
                loadend: (result) => {
                    const data = JSON.parse(result);

                    resolve(data);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 특정 채팅방 참여 */
    join(chatRoomId){
        return new Promise((resolve, reject) => {

            ajax({
                url: `/chatMember/api/v1/${chatRoomId}`,
                method: 'POST',
                loadend: (result) => {
                    const data = JSON.parse(result);

                    resolve(data);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 특정 채팅방 나가기 */
    dropOut(chatRoomId){
        return new Promise((resolve, reject) => {

            ajax({
                url: `/chatMember/api/v1/${chatRoomId}`,
                method: 'DELETE',
                loadend: (result) => {
                    const data = JSON.parse(result);

                    resolve(data);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 특정 채팅방에 참여 중인 클라이언트 목록 조회 (ID와 Nickname만) */
    getClientsFromChatRoom (chatRoomId){
        return new Promise((resolve, reject) => {

            ajax({
                url: `/chatMember/api/v1/clients/${chatRoomId}`,
                method: 'GET',
                loadend: (list) => {
                    resolve(JSON.parse(list));
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    }
};