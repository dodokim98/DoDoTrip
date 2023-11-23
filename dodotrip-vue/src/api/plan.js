import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/api";

function insertPlan(plan, success, fail) {
  console.log("plan : {}", plan);
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.post(`${url}/plan`, JSON.stringify(plan)).then(success).catch(fail);
}

//localhost:8080/api/{planno}/attraction
function insertAttractionPlan(planno, attraction, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  console.log("planAttraction : {}", attraction);
  local
    .post(`${url}/${planno}/attraction`, JSON.stringify(attraction))
    .then(success)
    .catch(fail);
}

//api/plans
function getPlansByUser(success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.get(`${url}/plans`).then(success).catch(fail);
}
//api/plan/{planno}
function viewPlan(planno, success, fail) {
  local.defaults.headers.Authorization = sessionStorage.getItem("accessToken");
  local.get(`${url}/plan/${planno}`).then(success).catch(fail);
}

export { insertPlan, insertAttractionPlan, getPlansByUser, viewPlan };
