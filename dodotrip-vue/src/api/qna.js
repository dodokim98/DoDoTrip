import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/api/qnas";

function listQna(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailQna(qnano, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.get(`${url}/${qnano}`).then(success).catch(fail);
}

function registQna(qna, success, fail) {
  console.log(qna);
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.post(`${url}/question`, JSON.stringify(qna)).then(success).catch(fail);
}

function registAnswer(qna, success, fail) {
  console.log(qna);
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.put(`${url}/answer`, JSON.stringify(qna)).then(success).catch(fail);
}

function deleteQna(qnano, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.delete(`${url}/${qnano}`).then(success).catch(fail);
}

export {
  listQna,
  detailQna,
  registQna,
  registAnswer,
  deleteQna,
}
;
