<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailQna, deleteQna } from "@/api/qna";
import { useMemberStore } from "@/stores/member";

const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();

// const articleno = ref(route.params.articleno);
const qnaNo = ref(0);
const qna = ref({});

const { getUserInfo } = memberStore;
const userRole = ref(""); //로그인한 유저의 권한

onMounted(() => {
  getQna();
});

onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token); //유저의 role 권한 얻기 위해 메소드 호출
  console.log(userInfo.role);
  userRole.value = userInfo.role;
  // userRole.value = 'ADMIN';
});

const getQna = () => {
  qnaNo.value = route.params.qnano;
  console.log(qnaNo.value + "번 글 얻으러 가자!!!");

  detailQna(
    qnaNo.value,
    ({ data }) => {
      console.log(data);
      qna.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "qna-list" });
}

function onDeleteQna() {
  console.log(qnaNo.value + "번글 삭제하러 가자!!!");
  console.log(userRole);
  // API 호출
  deleteQna(qnaNo.value, ({ data }) => {
    console.log(data);
    moveList();
  });
}
</script>

<template>
  <header>
    <h1 style="margin-top: 200px;">QnA 게시판</h1>
  </header>

  <main>
    <div class="qna-item">
      <div class="question">질문: <span id="question1">{{ qna.question }}</span></div>
      <div class="answer">답변: <span id="answer1">{{ qna.answer }}</span></div>
      <div class="user">작성자: <span id="user1">{{ qna.memberUserEmail }}</span></div>
    </div>
    <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteQna">
      글삭제
    </button>
  </main>
</template>

<style scoped>
main {
  max-width: 800px;
  margin: 20px auto;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.qna-item {
  cursor: pointer;
  margin-bottom: 20px;
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
}

.question {
  font-weight: bold;
  margin-bottom: 8px;
}

.answer {
  margin-left: 20px;
}

.user {
  color: #888;
}
</style>