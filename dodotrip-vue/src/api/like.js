import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/api/attractions";

function getAttractionLikeList(userEmail,success, fail) {
    local.get(`${url}/like/${userEmail}`).then(success).catch(fail);
}

function getAttractionLikeDetail(likeNo, success, fail) {
    local.get(`${url}/${likeNo}`).then(success).catch(fail);
}

function createAttractionLike(attractionLikeDto, success, fail) {
    console.log("attractionLikeDto : ", attractionLikeDto);
    local.post(`${url}/create`, JSON.stringify(attractionLikeDto)).then(success).catch(fail);
}

function deleteAttractionLike(likeNo, success, fail) {
    console.log("likeNo : "+likeNo);
    local.delete(`${url}/${likeNo}`).then(success).catch(fail);
}

function deleteAttractionLike2(contentId, success, fail) {
    console.log("contentId : "+contentId);
    local.delete(`${url}/like/${contentId}`).then(success).catch(fail);
}

function getAttractionDescription(contentId, success, fail) {
    console.log("2313123123123123123");
    console.log("contentId : ", contentId);
    local.get(`${url}/detail/${contentId}`).then(success).catch(fail);
}

export{
    getAttractionLikeList,
    getAttractionLikeDetail,
    createAttractionLike,
    deleteAttractionLike,
    deleteAttractionLike2,
    getAttractionDescription,
}