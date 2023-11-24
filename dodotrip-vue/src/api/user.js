import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function signup(param, success, fail) {
  console.log("param", param);
  await local.post(`/api/signup`, param).then(success).catch(fail);
  console.log("회원가입 성공");
}

async function userConfirm(param, success, fail) {
  console.log("param", param);
  await local.post(`/api/login`, param).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function findByEmail(userEmail, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  await local.get(`/api/member/${userEmail}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] =
    sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/api/member/refresh`, user).then(success).catch(fail);
}

async function logout(userEmail, success, fail) {
  await local.get(`/api/logout/${userEmail}`).then(success).catch(fail);
}

function modifyUser(user, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.delete(`/api/member`, JSON.stringify(user)).then(success).catch(fail);
}

function deleteMember(userEmail, success, fail){
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.delete(`api/member/${userEmail}`).then(success).catch(fail);

}

export {
  signup,
  userConfirm,
  findByEmail,
  tokenRegeneration,
  logout,
  modifyUser,
  deleteMember
};
