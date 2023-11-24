import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/api/comments";

function getCommentList(boardArticleNo,success, fail) {
    local.get(`/api/${boardArticleNo}/comments`).then(success).catch(fail);
}
  
function registComment(commentDto, success, fail) {
    local.post(`${url}`, JSON.stringify(commentDto)).then(success).catch(fail);
}

function modifyComment(commentDto, success, fail) {
    local.put(`${url}`, JSON.stringify(commentDto)).then(success).catch(fail);
}

function deleteComment(commentNo, success, fail) {
    local.delete(`${url}/${commentNo}`).then(success).catch(fail);
}
function getComment(commentNo, success, fail) {
    local.get(`${url}/${commentNo}`).then(success).catch(fail);
}

export {
    getCommentList,
    registComment,
    modifyComment,
    deleteComment,
    getComment,
}