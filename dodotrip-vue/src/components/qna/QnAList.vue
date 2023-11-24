<script setup>
import { ref, onMounted } from "vue";
import { listQna } from "@/api/qna";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

import QnAListItem from "./item/QnAListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const qnas = ref([]);
const currentPage = ref(1); //현재 페이지
const totalPage = ref(0); //전체 페이지
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const memberStore = useMemberStore();
const { getUserInfo } = memberStore;
const userRole = ref("");

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
});

onMounted(() => {
  getQnaList();
});

onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token);  //유저의 role 권한 얻기 위해 메소드 호출
  console.log(userInfo.role)
  userRole.value = userInfo.role;
  // userRole.value = 'ADMIN';
});

const getQnaList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  listQna(
    param.value,
    ({ data }) => {
      console.log(data);
      qnas.value = data.qnas;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getQnaList();
};

const moveWrite = () => {
  router.push({ name: "qna-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col">
        <h2 class="my-3 py-3 text-center">
          QnA 게시판
        </h2>
      </div>
      <div class="col">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-dark btn-sm" @click="moveWrite" style="margin-left: 36px;">
              질문하기
            </button>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">질문</th>
              <th scope="col">작성자</th>
              <th scope="col">답변</th>
            </tr>
          </thead>
          <tbody>
            <QnAListItem v-for="qna in qnas" :key="qna.qnaNo" :qna="qna"></QnAListItem>
          </tbody>
        </table>
      </div>
      <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
    </div>
  </div>
</template>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}

.container {
  background-color: #f2f2f2;
  /* Light gray background */
  width: 1300px;
  align-items: center;
  margin-top: 2%;
}

h2 {
  background-color: #343a40;
  /* Dark gray background for the title */
  color: #f8f9fa;
  /* Light text color for the title */
  padding: 10px;
  width: 1200px;
  margin-left: 36px;
  border-radius: 5px;
}


.table {
  background-color: #fff;
  width: 1200px;
  height: 530px;
  margin-left: 36px;

}

.table th,
.table td {
  border: 1px solid #dee2e6;
  /* Light gray border for table cells */
}

.table th {
  background-color: #343a40;
  /* Dark gray background for table header */
  color: #f8f9fa;
  /* Light text color for table header */
}

.table-hover tbody tr:hover {
  background-color: #f8f9fa;
  /* Light gray background on hover */
}
</style>

