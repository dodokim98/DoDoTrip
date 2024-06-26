<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board";
import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자이메일", value: "member_user_email" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  // API 호출
  listArticle(
    param.value,
    ({ data }) => {
      console.log(data);
      articles.value = data.articles;
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
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 text-center">
            여행정보공유
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button
              type="button"
              class="btn btn-dark btn-sm"
              @click="moveWrite"
            >
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                />
                <button
                  class="btn btn-dark"
                  type="button"
                  @click="getArticleList"
                >
                  검색
                </button>
              </div>
            </form>
          </div>
        </div>
        <table class="table">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="article in articles"
              :key="article.articleNo"
              :article="article"
            ></BoardListItem>
          </tbody>
        </table>
      </div>
      <PageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></PageNavigation>
    </div>
  </div>
</template>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}

.container {
  background-color: #f2f2f2; /* Light gray background */
  max-width: 1440px;
  margin-top: 2%;
  height: 700px;
}

h2 {
  background-color: #343a40; /* Dark gray background for the title */
  color: #f8f9fa; /* Light text color for the title */
  padding: 10px;
  border-radius: 5px;
}

.btn-outline-primary {
  color: #007bff;
  border-color: #007bff;
}

.btn-outline-primary:hover {
  background-color: #007bff;
  color: #ffffff;
  border-color: #007bff;
}
.table {
  background-color: #fff; /* White background for the table */
}

.table th, .table td {
  border: 1px solid #dee2e6; /* Light gray border for table cells */
}

.table th {
  background-color: #343a40; /* Dark gray background for table header */
  color: #f8f9fa; /* Light text color for table header */
}

.table-hover tbody tr:hover {
  background-color: #f8f9fa; /* Light gray background on hover */
}
</style>
