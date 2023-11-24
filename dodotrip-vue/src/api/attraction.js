import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/api/map";

function listSido(success, fail) {
    local.get(`${url}/sido`).then(success).catch(fail);
  }
  
  function listGugun(param, success, fail) {
    local.get(`${url}/gugun`, { params: param }).then(success).catch(fail);
  }

  function search(param, success, fail) {
    local.get(`${url}/search`, { params: param }).then(success).catch(fail);
  }
  
  export { listSido, listGugun, search };