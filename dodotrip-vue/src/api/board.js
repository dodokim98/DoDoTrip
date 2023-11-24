import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/api/boards";

function listArticle(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.get(`${url}/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.post(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.get(`${url}/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  console.log("board modifyArticle", article);
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.put(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.delete(`${url}/${articleno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};
